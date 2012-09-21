package sheepwar;

import javax.microedition.lcdui.Image;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.util.Collision;
import cn.ohyeah.stb.util.RandomValue;

public class StateGame implements Common{
	
	/*从下往上判断四个梯子上是否有狼，从右到左*/
	public static boolean HASWOLF_ONE;
	public static boolean HASWOLF_TWO;
	public static boolean HASWOLF_THREE;
	public static boolean HASWOLF_FOUR;
	
	/*判断梯子上是否都有狼*/
	public static boolean IS_FOUR_WOLF;
	
	private SheepWarGameEngine engine;
	
	public StateGame(SheepWarGameEngine engine){
		this.engine = engine;
	}
	
	public CreateRole createRole;
	public Batches batches;
	public Weapon weapon;
	public static Role own; 		

	/*游戏关卡*/
	public short level = 1; 
	/*奖励关卡*/
	public short rewardLevel = 1;
	
	public boolean isRewardLevel;
	
	/*当前关卡狼出现的批次*/
	public short batch;
	
	/*关卡信息*/
	public static int[][] LEVEL_INFO = {
		/*0-关卡，1-该关卡击中狼的数量， 2-每批狼出现的间隔时间（秒），3-该关卡狼的位置（0-上面, -1-下面）*/
		{1, 2, 3, 0},  	//第一关
		{2, 2, 3, -1},  //第二关
		{3, 2, 3, 0},  	//第三关
		{4, 2, 3, -1},  //第四关
		{5, 2, 3, 0},  	//第五关
		{6, 2, 3, -1},  
		{7, 2, 2, 0},  
		{8, 2, 2, -1},  
		{9, 2, 1, 0},  
		{10, 2, 1, -1},  
		{11, 2, 3,0},  
		{12, 2, 3, -1},  
		{13, 2, 3, 0},  
		{14, 2, 3, -1},  
		{15, 2, 3, 0},  
	};
	
	/*奖励关卡信息*/
	public static int [][] REWARD_LEVEL_INFO = {
		/*0-关卡，1-该关卡击中狼的数量， 2-每批狼出现的间隔时间（秒），3-该关卡狼的位置（0-上面, -1-下面）*/
		{1,16,3,-1},			//奖励关卡一
		{2,16,3,-1},			//奖励关卡二
		{3,16,3,-1},			//奖励关卡三
		{4,16,3,-1},			
		{5,16,3,-1},			
		{6,16,3,-1},			
		{7,16,3,-1},			
	};
	
	/*游戏过度时间*/
	private long gameBufferTimeS, gameBufferTimeE;
	private boolean goNext;
	
	/*控制子弹发射的变量*/
	private long startTime, endTime;
	private boolean isAttack = true;
	private int bulletInterval = 2;   
	
	/*时光闹钟*/
	public static boolean pasueState;
	private long pasueTimeS,  pasueTimeE;
	private int pasueInterval = 10;
	
	/*加速*/
	public static boolean speedFlag;
	private long addSpeedTime,addSpeedTime2;
	private int speedLiquidInterval = 30;
	
	/*无敌*/
	private long proEndTime;
	private long proStartTime;
	private long protectInterval = 5;
	public static boolean protectState;
	
	/*强力磁石*/
	private long magnetStartTime,magnetEndTime;
	private long magnetInterval = 3;
	public static boolean magnetState;
	
	/*玩家复活数据*/
	public static int lifeNum;
	public static int scores;
	public static int eatNum;
	
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30, sWidth = 155, sTempy = 309;
	
	public void handleKey(KeyState keyState){
		if (keyState.containsMoveEventAndRemove(KeyCode.UP)) {
			moveRole(0);
			
		} else if (keyState.containsMoveEventAndRemove(KeyCode.DOWN)) {
			moveRole(1);
			
		} else if (keyState.containsAndRemove(KeyCode.OK)&& own.status ==ROLE_ALIVE) {	
			if(isAttack){ //普通攻击
				weapon.createBomb(own, Weapon.WEAPON_MOVE_LEFT);
				own.bombNum ++;
				if(own.bombNum%2==0){
					isAttack = false;
					startTime = System.currentTimeMillis()/1000;
				}
			}
			
		}else if(keyState.containsAndRemove(KeyCode.NUM1)&& own.status ==ROLE_ALIVE){    	//时光闹钟
			pasueState = true;
			pasueTimeS = System.currentTimeMillis()/1000;
			
		}else if(keyState.containsAndRemove(KeyCode.NUM2)&& own.status ==ROLE_ALIVE){ 		//捕狼网
			weapon.createNet(own, Weapon.WEAPON_MOVE_LEFT);
			
		}else if(keyState.containsAndRemove(KeyCode.NUM3)&& own.status ==ROLE_ALIVE){		//盾牌
			protectState = true;
			weapon.createProtect(own);
			proStartTime = System.currentTimeMillis()/1000;
			
		}else if(keyState.containsAndRemove(KeyCode.NUM4)&& own.status ==ROLE_ALIVE){		//激光枪
			weapon.createGlare(own,Weapon.WEAPON_MOVE_LEFT);

		}else if(keyState.containsAndRemove(KeyCode.NUM5)&& own.status ==ROLE_ALIVE){		//驱散竖琴
			weapon.createHarp(own);
			
		}else if(keyState.containsAndRemove(KeyCode.NUM6)&& own.status ==ROLE_ALIVE){		//速度提升液
			if(!speedFlag){
				own.speed = own.speed + 5;
				speedFlag = true;
				addSpeedTime = System.currentTimeMillis()/1000;
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM7)&& own.status ==ROLE_ALIVE){		//强力磁石
			magnetStartTime = System.currentTimeMillis()/1000;
			magnetState = true;
		}else if(keyState.containsAndRemove(KeyCode.NUM8) && own.status ==ROLE_ALIVE){		//木偶->可以增加一条生命
			own.lifeNum ++;
			
		}else if(keyState.containsAndRemove(KeyCode.NUM9)){		//暂停							
			
		}else if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){ 	//返回
			engine.status = STATUS_MAIN_MENU;
			clear();
		}
	}
	
	public void show(SGraphics g){
		drawGamePlaying(g);
		createRole.showSheep(g,own);
		batches.showWolf(g, weapon);
		weapon.showFruit(g);
		weapon.showBomb(g);
		weapon.showBoom(g,own);			
		weapon.showNet(g);
		weapon.showProtect(g, own);
		weapon.showGlare(g, own);
		weapon.showHarp(g, batches);
		weapon.showMagnetEffect(g, batches);
		if(batches.redWolf!=null){
			batches.showRedWolf(g,weapon);				
		}
	}
	
	public void execute(){
		/*控制子弹发射间隔*/
		endTime = System.currentTimeMillis()/1000; 
		if(isAttack==false && endTime-startTime>=bulletInterval){
			isAttack = true;
		}
		/*时光闹钟效果时间*/
		pasueTimeE = System.currentTimeMillis()/1000;
		if(pasueState && (pasueTimeE-pasueTimeS)>=pasueInterval){
			pasueState = false;
		}
		/*加速效果时间*/
		addSpeedTime2 = System.currentTimeMillis()/1000;
		if(addSpeedTime2 - addSpeedTime >speedLiquidInterval){			
			speedFlag = false;
			own.speed = 5;
		}
		/*防狼套装的时间控制*/
		proEndTime = System.currentTimeMillis()/1000;
		if(proEndTime - proStartTime > protectInterval){
			protectState = false;
		}
		
		/*强力磁石控制时间*/
		magnetEndTime = System.currentTimeMillis()/1000;
		if(magnetEndTime - magnetStartTime > magnetInterval){
			magnetState = false;
		}
		System.out.println("own.life:"+own.lifeNum);
		/*游戏过度时间*/
		gameBufferTimeE = System.currentTimeMillis()/1000;
		
		/*关卡过关判断*/
		isNextLevel();  			
		
		/*创建狼*/
		createNpc();
		
		/*创建一批狼*/
		createRedNpc();
		
		/*普通攻击碰撞检测*/
		bombAttackNpcs();
		
		/*捕狼道具碰撞检测*/
		netAttackNpcs();
		
		/*狼的普通攻击碰撞检测*/
		boomAttackPlayer();
		
		/*激光枪碰撞检测*/
		glareAttackNpcs();
		
		/*移除死亡对象*/
		removeDeath();

		/*判断4个位置上是否都有狼*/
		checkFourPosition();
		
		/*游戏成功或失败*/
		gameSuccessOrFail();
	}

	private void checkFourPosition() {
		if(HASWOLF_ONE && HASWOLF_TWO && HASWOLF_THREE && HASWOLF_FOUR){
			IS_FOUR_WOLF = true;
			own.status = ROLE_DEATH;
			own.lifeNum --;
			for(int j = batches.npcs.size() - 1;j>=0;j--){
				Role npc = (Role)batches.npcs.elementAt(j);
				if(npc.position == ON_ONE_LADDER || npc.position ==ON_TWO_LADDER 
						|| npc.position == ON_THREE_LADDER || npc.position == ON_FOUR_LADDER){
					npc.status = ROLE_DEATH;
					StateGame.HASWOLF_ONE = false;
					StateGame.HASWOLF_TWO = false;
					StateGame.HASWOLF_THREE = false;
					StateGame.HASWOLF_FOUR = false;
					batches.npcs.removeElement(npc);
				}
			}
		}
	}

	private void isNextLevel(){
		if(!isRewardLevel){  //普通关卡过关判断
			for (int i = 1; i < 16; i++) {
				if (level == i && own.eatNum >= LEVEL_INFO[level - 1][1]) {
					System.out.println("过关");
					gameBufferTimeS = System.currentTimeMillis()/1000;
					if(level%2==0){
						isRewardLevel = true;
						System.out.println("下一关为奖励关卡");
					}
					goNext = true;
					own.eatNum = 0;
					batch = 0;
					level++;
				}
			}
		}else{	//奖励关卡过关判断
			if((rewardLevel%2==1 && batch >= (RewardLevelBatchesInfo[rewardLevel-1].length - 1))
					||(rewardLevel%2==0 && batches.redWolf.bombNum>=16)){
				System.out.println("奖励关卡结束");
				gameBufferTimeS = System.currentTimeMillis()/1000;
				goNext = true;
				isRewardLevel = false;
				rewardLevel++;
				own.eatNum = 0;
				if(batches.redWolf!=null){
					batches.redWolf.bombNum = 0;
				}
				batch = 0;
			}
		}
		
		/*上述判断为true则进入过关界面*/
		if(goNext==true && gameBufferTimeE-gameBufferTimeS>1){
			goNext = false;
			HASWOLF_ONE = false;
			HASWOLF_TWO = false;
			HASWOLF_THREE = false;
			HASWOLF_FOUR = false;
			weapon.clearObjects(); // 清空对象
			batches.clearObject(); // 清空对象
			StateNextLevel stateLevel = new StateNextLevel();
			stateLevel.processNextLevel(own);
		}
	}
	
	private void gameSuccessOrFail() {
		if(own.lifeNum<=0){		/*游戏失败*/
			System.out.println("isSuccess:"+false);
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(false, own);
			engine.status = STATUS_MAIN_MENU;
			
		}else if(level > 15){	/*游戏通关*/
			System.out.println("isSuccess:"+true);
			StateGameSuccessOrFail sgs = new StateGameSuccessOrFail();
			sgs.processGameSuccessOrFail(true, own);
			engine.status = STATUS_MAIN_MENU;
		}
	}

	/*判断激光枪是否击中狼*/
	private void glareAttackNpcs() {
		for(int i=weapon.glares.size()-1;i>=0;i--){
			Weapon glare = (Weapon) weapon.glares.elementAt(i);
			if(!glare.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					if(npc.status == ROLE_ALIVE){
						if(Collision.checkCollision(npc.mapx, npc.mapy, npc.width, npc.height, glare.mapx, glare.mapy, glare.width, glare.height)){
							npc.status = ROLE_DEATH;		//TODO confirm the real effect of Glare
							npc.speed += 10;
						}
					}
				}
			}
			/*激光出界时移除*/
			if(glare.mapx+glare.width <= 0){
				weapon.glares.removeElement(glare);
			}
		}
	}

	/*判断狼是否击中玩家*/
	private void boomAttackPlayer(){
		for(int i = weapon.booms.size() - 1;i >=0;i--){
			Weapon boom = (Weapon)weapon.booms.elementAt(i);
			if(own.status == ROLE_ALIVE){
				if(Collision.checkCollision(boom.mapx, boom.mapy, boom.width, boom.height, own.mapx, own.mapy, own.width, own.height)){
					if(!protectState){			//玩家是否有防狼套装
						own.status = ROLE_DEATH;
						own.lifeNum --;
						lifeNum = own.lifeNum;
					}
					weapon.booms.removeElement(boom);
				}
			}
			/*子弹出界时移除*/
			if(boom.mapx >= gameW){
				weapon.booms.removeElement(boom);
			}
		}
	}

	/*羊的捕狼网技能攻击*/
	private void netAttackNpcs() {
		for(int i=weapon.nets.size()-1;i>=0;i--){
			Weapon net = (Weapon) weapon.nets.elementAt(i);
			if(net.isUse){
				for(int j=batches.npcs.size()-1;j>=0;j--){
					Role npc = (Role) batches.npcs.elementAt(j);
					if(npc.status == ROLE_ALIVE){
						if(Collision.checkCollision(npc.mapx, npc.mapy, npc.width, npc.height, net.mapx, net.mapy, net.width, net.height)){
							npc.status = ROLE_DEATH;
							own.eatNum ++;
							if(npc.role != null){
								own.scores += npc.role.scores;
								scores = own.scores;
							}
							npc.speed += 10;
						}
					}
				}
				
				/*射水果*/
				for(int k=weapon.fruits.size()-1;k>=0;k--){
					Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
					if(Collision.checkCollision(fruit.mapx, fruit.mapy, fruit.width, fruit.height,net.mapx, net.mapy, net.width, net.height)){
						own.scores += fruit.scores;
						scores = own.scores;
						fruit.status = FRUIT_HIT;
					}
				}
				
				Weapon.netTimeE = System.currentTimeMillis()/1000;
				if(Weapon.netTimeE-Weapon.netTimeS>=Weapon.netInterval){
					net.isUse = false;
					weapon.nets.removeElement(net);
				}
			}
		}
	}

	/*创建红太狼npc*/
	private void createRedNpc(){
		if(isRewardLevel){
			System.out.println("当前奖励关卡:"+rewardLevel);
			if(rewardLevel % 2 ==0 && batches.redWolf==null){					//偶数奖励关卡出现红太狼
				System.out.println("奖励关卡创建红太狼");
				batches.redWolf = batches.createRedWolf();
			}
		}else{
			System.out.println("当前关卡："+level);
			if(level % 2!=0 && level != 1 && batches.redWolf==null){			//奇数关卡会有红太狼的出现
				System.out.println("普通关卡创建红太狼");
				batches.redWolf = batches.createRedWolf();
			}
		}
	}
	
	private void createNpc(){
		if(isAllDown()){
			if(!isRewardLevel){
				if(engine.timePass(LEVEL_INFO[level-1][2]*1000)){
					System.out.println("普通关卡创建一批狼");
					batches.createBatches(level, batch, LEVEL_INFO[level-1][3]);
					batch = (short) ((batch+1) % BatchesInfo[level-1].length);
				}
			}else{
				if(engine.timePass(REWARD_LEVEL_INFO[rewardLevel -1][2]*1000)&&rewardLevel%2!=0){	
					System.out.println("奖励关卡创建一批狼");
					batches.createBatchesReward(rewardLevel, batch, REWARD_LEVEL_INFO[rewardLevel-1][3]);
					batch = (short)((batch+1) % RewardLevelBatchesInfo[rewardLevel-1].length);
					
				}
			}
		}
	}
	
	/*判断狼是否都已经下降或者上升*/
	private boolean isAllDown(){
		int len = batches.npcs.size();
		for(int i=len-1;i>=0;i--){
			Role role = (Role) batches.npcs.elementAt(i);
			if(role.status2 == ROLE_ON_GROUND){
				return false;
			}
		}
		return true;
	}
	
	private void removeDeath(){
		for(int j=batches.npcs.size()-1;j>=0;j--){
			Role npc = (Role) batches.npcs.elementAt(j);
			if(npc.status == ROLE_DEATH && npc.mapy >= 446){
				batches.npcs.removeElement(npc);
			}
		}
		/*水果出界移除*/
		for(int k=weapon.fruits.size()-1;k>=0;k--){
			Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
			if(fruit.mapx>=gameW || fruit.mapx+fruit.width <=0 || fruit.mapy>=ScrH){
				weapon.fruits.removeElement(fruit);
			}
		}
	}
	
	private void bombAttackNpcs(){
		for(int i=weapon.bombs.size()-1;i>=0;i--){
			Weapon bomb = (Weapon) weapon.bombs.elementAt(i);
			for(int j=batches.npcs.size()-1;j>=0;j--){
				Role npc = (Role) batches.npcs.elementAt(j);
				Role ballon = npc.role;
				if(ballon != null && npc.status == ROLE_ALIVE){
					if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, ballon.mapx, ballon.mapy, ballon.width, 30/*ballon.height*/)){
						npc.status = ROLE_DEATH;
						npc.speed += 10;
						weapon.bombs.removeElement(bomb);
						own.eatNum ++;
						own.scores += ballon.scores;
						scores = own.scores;
						eatNum = own.eatNum;
					}
					else if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, npc.mapx, npc.mapy, npc.width, npc.height)){
						bomb.direction = Weapon.WEAPON_MOVE_DOWN;
						bomb.speedY = bomb.speedX + 10;
					}
				}
			}
			
			/*射水果*/
			for(int k=weapon.fruits.size()-1;k>=0;k--){
				Weapon fruit = (Weapon) weapon.fruits.elementAt(k);
				if(Collision.checkCollision(bomb.mapx, bomb.mapy, bomb.width, bomb.height, fruit.mapx, fruit.mapy, fruit.width, fruit.height)){
					own.scores += fruit.scores;
					fruit.status = FRUIT_HIT;
					weapon.bombs.removeElement(bomb);
				}
			}
			
			/*子弹出界时移除*/
			if((bomb.mapx+bomb.width <=0) || bomb.mapy >= 466){
				weapon.bombs.removeElement(bomb);
			}
		}
	}
	
	private int cloudIndex, cloud2Index;
	private int down_cloudIndex, down_cloud2Index;
	int x1 = 20, x2 = 550, x3 = 424;
	private void drawGamePlaying(SGraphics g){
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);
		Image playing_step = Resource.loadImage(Resource.id_playing_step);
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi);
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift);
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1);
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu);
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop);
		Image ladder = Resource.loadImage(Resource.id_ladder);
		Image playing_level = Resource.loadImage(Resource.id_playing_level);
		Image playing_point = Resource.loadImage(Resource.id_playing_point);
		Image sheep_head = Resource.loadImage(Resource.id_sheep_head);
		Image wolf_head = Resource.loadImage(Resource.id_wolf_head);
		Image multiply = Resource.loadImage(Resource.id_multiply);
		/*奖励关卡图片资源*/
		Image pass_cloud2 = Resource.loadImage(Resource.id_pass_cloud2);
		Image pass_cloud = Resource.loadImage(Resource.id_pass_cloud);
		Image passShadowCloud = Resource.loadImage(Resource.id_cloud1);
		Image pass_cloud1 = Resource.loadImage(Resource.id_pass_cloud1);
		
		g.drawImage(game_bg, 0, 0, 20);
		if(isRewardLevel && !goNext){		//画出奖励关卡界面
			g.drawImage(pass_cloud, 50, 80, 20);
			g.drawImage(pass_cloud, 216, 80, 20);
			g.drawImage(pass_cloud, 404, 140, 20);		//轮子下面的云朵
			for(int i=0;i<4;i++){			//固定的云层 南瓜
				g.drawImage(passShadowCloud, 0+i*60, 80+10, 20);
				g.drawImage(pass_cloud, 0+i*60, 80, 20);
			}
			/*上面第二层云*/
			int cloud2W = pass_cloud2.getWidth(),cloud2H = pass_cloud2.getHeight();
			int len = cloud2W-ScrW;
			int cloud2Y = -6;
			cloud2Index=(cloud2Index+1)%cloud2W;
			if(cloud2Index<=len){
				g.drawRegion(pass_cloud2, len-cloud2Index, 0, ScrW, cloud2H, 0, 0, cloud2Y, 20);
			}else{
				g.drawRegion(pass_cloud2, (cloud2W-cloud2Index), 0, ScrW-(cloud2W-cloud2Index), cloud2H, 0, 0, cloud2Y, 20);
				g.drawRegion(pass_cloud2, 0, 0, (cloud2W-cloud2Index), cloud2H, 0, ScrW-(cloud2W-cloud2Index), cloud2Y, 20);
			}
			
			/*下面第二层云*/
			int down_cloud2Y = 484;
			down_cloud2Index=(down_cloud2Index+1)%cloud2W;
			if(down_cloud2Index<=len){
				g.drawRegion(pass_cloud2, len-down_cloud2Index, 0, ScrW, cloud2H, 0, 0, down_cloud2Y, 20);
			}else{
				g.drawRegion(pass_cloud2, (cloud2W-down_cloud2Index), 0, ScrW-(cloud2W-down_cloud2Index), cloud2H, 0, 0, down_cloud2Y, 20);
				g.drawRegion(pass_cloud2, 0, 0, (cloud2W-down_cloud2Index), cloud2H, 0, ScrW-(cloud2W-down_cloud2Index), down_cloud2Y, 20);
			}

			/*中间的云*/
			int cloudW = pass_cloud.getWidth();
			if(x1+cloudW<=0){
				x1 = ScrW;
			}else{
				x1 -= 1;
			}
			if(x2+cloudW<=0){
				x2 = ScrW;
			}else{
				x2 -= 1;
			}
			if(x3+cloudW<=0){
				x3 = ScrW;
			}else{
				x3 -= 1;
			}
			g.drawImage(pass_cloud, x1, 152, 20);
			g.drawImage(pass_cloud, x2, 180, 20);
			g.drawImage(pass_cloud, x3, 265, 20);
			
			/*上面第一层云*/
			int cloud1W = pass_cloud1.getWidth(),cloud1H = pass_cloud1.getHeight();
			int cloud1Y = -23;
			cloudIndex=(cloudIndex+1)%cloud1W;
			if(cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, cloudIndex, 0, ScrW, cloud1H, 0, 0, cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, cloudIndex, 0, cloud1W-cloudIndex, cloud1H, 0, 0, cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, cloudIndex, cloud1H, 0, cloud1W-cloudIndex, cloud1Y, 20);
			}
			
			/*下面第一层云*/
			int down_cloud1Y = 496;
			down_cloudIndex=(down_cloudIndex+1)%cloud1W;
			if(down_cloudIndex<=cloud1W-ScrW){
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, ScrW, cloud1H, 0, 0, down_cloud1Y, 20);
			}else{
				g.drawRegion(pass_cloud1, down_cloudIndex, 0, cloud1W-down_cloudIndex, cloud1H, 0, 0, down_cloud1Y, 20);
				g.drawRegion(pass_cloud1, 0, 0, down_cloudIndex, cloud1H, 0, cloud1W-down_cloudIndex, down_cloud1Y, 20);
			}
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);								//游戏中 左侧的关卡图片		
			drawNum(g, rewardLevel, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//奖励关卡羊的生命数
			
		}else{
			if(tempx+playing_cloudbig.getWidth()>0){
				tempx -= 1;
			}else{
				tempy = RandomValue.getRandInt(0, 114);
				tempx = ScrW;
			}
			g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
					0, tempx, tempy, 20);
			
			if(tempx2+playing_cloudsmall.getWidth()>0){
				tempx2 -= 2;
			}else{
				tempy2 = RandomValue.getRandInt(0, 114);
				tempx2 = ScrW;
			}
			g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
					0, tempx2, tempy2, 20);
			g.drawImage(playing_lawn, 0, 499, 20);
			g.drawImage(playing_tree, 0, 72, 20);
			for(int i=0;i<4;i++){   //阶梯
				g.drawImage(playing_step, 377, 153+i*89, 20);
				g.drawImage(ladder, 426, 183+i*89, 20);
			}
			
			g.drawImage(playing_menu, 491, 0, 20);
			g.drawImage(playing_level, 491+32, 25, 20);						//游戏中 左侧的关卡图片		
			drawNum(g, level, 491+32+playing_level.getWidth()+10, 25);
			drawNum(g, own.lifeNum, 491+66+multiply.getWidth()+10, 147);			//羊的生命数
		}
		
		if(own.status == ROLE_ALIVE){
			sWidth = own.mapy - 154;
			sTempy = own.mapy;
		}
		g.drawImage(playing_shenzi1, 399, 135, 20);												//横放的绳子
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), sWidth,        			//上下移动的绳子
				0, 379, 154, 20);                                                        		//竖直绳子 的纵坐标 154
		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //羊的吊篮
				0, 342, sTempy, 20);
		
		g.drawImage(playing_lunzi, 374,132, 20);
		g.drawImage(playing_point, 491+11, 66, 20);						//游戏中 左侧 的得分图片	
		drawNum(g, own.scores, playing_point.getWidth()+502, 66);
		g.drawImage(sheep_head, 491+26, 142, 20);						//游戏中 右侧 的羊的头像		
		g.drawImage(wolf_head, 12, 10, 20);								//游戏中 左侧 的狼的头像		
		g.drawImage(multiply, 491+66, 147, 20);	
		g.drawImage(multiply, 45, 12, 20);	
		drawNum(g, own.eatNum, 45+multiply.getWidth()+10, 12);

		int propLeftMenuX = 497+1,propRightMenuX= 564+1,propMenuY = 185-7,distanceMenuY = 4;
		int numLeftX = 547,numRight = 612;
		for(int i=0;i<4;i++){                                                                
			g.drawImage(playing_prop_memu, propLeftMenuX,propMenuY+i*(distanceMenuY+playing_prop_memu.getHeight()), 20);
			drawProp(g, i, propLeftMenuX+5,propMenuY+4+i*(distanceMenuY+playing_prop_memu.getHeight()));                                              
			drawNum(g, i+1, numLeftX, propMenuY+playing_prop_memu.getHeight()-29			//29是显示
					+i*(distanceMenuY+playing_prop_memu.getHeight()));//提示技能按键：1-4{540,223}
			
			g.drawImage(playing_prop_memu, propRightMenuX,propMenuY+i*(distanceMenuY+playing_prop_memu.getHeight()), 20);
			drawProp(g, i+4, propRightMenuX+5,propMenuY+4+i*(distanceMenuY+playing_prop_memu.getHeight()));  //第二列对应原图片中的后四个
			drawNum(g, i+4+1, numRight, propMenuY+playing_prop_memu.getHeight()-29
					+i*(distanceMenuY+playing_prop_memu.getHeight()));//提示技能键5-8{}
		}
		g.drawImage(playing_stop, 500,459, 20);			//暂停游戏按钮
	}
	
	private void moveRole(int towards) {
		switch (towards) {
		case 0: // 往上--主角
			if(own.mapy>=164){
				own.mapy -= own.speed;
			}
			break;
		case 1: // 往下--主角
			own.direction = 1;
			if(own.mapy + own.height<460){
				own.mapy += own.speed;
			}
			break;
		}
	}
	
	private void drawNum(SGraphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	private void drawProp(SGraphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
	
	private void clear() {
		Resource.freeImage(Resource.id_playing_menu);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudbig);
		Resource.freeImage(Resource.id_playing_cloudsmall);
		Resource.freeImage(Resource.id_playing_lawn);
		Resource.freeImage(Resource.id_playing_step);
		Resource.freeImage(Resource.id_playing_tree);
		Resource.freeImage(Resource.id_game_bg);
		Resource.freeImage(Resource.id_playing_lunzi);
		Resource.freeImage(Resource.id_playing_shenzi);
		Resource.freeImage(Resource.id_playing_lift);
		Resource.freeImage(Resource.id_playing_shenzi1);
		Resource.freeImage(Resource.id_playing_prop_memu);
		Resource.freeImage(Resource.id_playing_prop);
		Resource.freeImage(Resource.id_playing_stop);   
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
		Resource.freeImage(Resource.id_bomb);   
		Resource.freeImage(Resource.id_wolf_down);   
		Resource.freeImage(Resource.id_wolf_run);   
		Resource.freeImage(Resource.id_balloon_blue);   
		Resource.freeImage(Resource.id_balloon_green);   
		Resource.freeImage(Resource.id_balloon_multicolour);   
		Resource.freeImage(Resource.id_balloon_yellow);   
		Resource.freeImage(Resource.id_balloon_yellowred);   
		Resource.freeImage(Resource.id_balloon_red);   
		Resource.freeImage(Resource.id_ladder);   
		Resource.freeImage(Resource.id_playing_level);   
		Resource.freeImage(Resource.id_playing_point);   
		Resource.freeImage(Resource.id_sheep_head);   
		Resource.freeImage(Resource.id_wolf_head);   
		Resource.freeImage(Resource.id_multiply);   
		Resource.freeImage(Resource.id_pass_cloud2);   
		Resource.freeImage(Resource.id_pass_cloud1);   
		Resource.freeImage(Resource.id_pass_cloud);   
	}

}
