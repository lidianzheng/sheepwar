package sheepwar;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.ui.DrawUtil;
import cn.ohyeah.stb.ui.TextView;
import cn.ohyeah.stb.util.RandomValue;

/**
 * 显示图像
 * 
 * @author Administrator
 * 
 */
public class ShowGame implements Common {
	private SheepWarGameEngine engine;
	public ShowGame(SheepWarGameEngine e){
		this.engine=e;
	}
	public Image playing_cloudsmall,playing_cloudbig;
	private int tempx=ScrW, tempy=20, tempx2=ScrW, tempy2=30;//ScrW屏幕宽度，tempx初始=ScrW，可以用表达式tempx-=1来使其移动
	
	/* 登陆界面坐标 */
	public int menuAxis[][] = { { 523, 243 }, { 466, 288 }, { 523, 333 },
			{ 466, 378 }, { 523, 423 }, { 466, 468 }, };
	/* 游戏界面坐标 */
	public int playingAxis[][] = { { 491, 0 }, { 0, 529 }, { 0, 72 },
			{ 377, 153 }, { 377, 240 }, { 377, 324 }, { 377, 409 }, };
	
     /*成就左侧文字图片坐标*/
	public int archLeftAxis[][] = { { 68, 132 },{ 59, 186 },{ 68, 240 },{ 68, 294 },{ 68, 348 },{ 68, 402 } };
	
	
	/*释放MainMenu图片*/
	public void clearMainMenu() {
		Resource.freeImage(Resource.id_main_bg);
		Resource.freeImage(Resource.id_main_menu);
	}
	
	/*释放游戏中的图片*/
	public void clearGamePlaying(){
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
		Resource.freeImage(Resource.id_playing_stop);   //游戏暂停按钮
		Resource.freeImage(Resource.id_playing_sheep);   
		Resource.freeImage(Resource.id_sheep_eye);   
		Resource.freeImage(Resource.id_sheep_hand);   
		Resource.freeImage(Resource.id_bomb);   
	}
	
	/*清商城界面*/
	public void clearShop() {
		Resource.freeImage(Resource.id_shop);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_big);
		Resource.freeImage(Resource.id_shop_figure);
		Resource.freeImage(Resource.id_shop_balance);
		Resource.freeImage(Resource.id_shop_go_pay);
		Resource.freeImage(Resource.id_shop_midding);
		Resource.freeImage(Resource.id_shop_out);
		Resource.freeImage(Resource.id_price_quantity);
		Resource.freeImage(Resource.id_playing_prop);        //商品图片
	}
	
	/*清 成就系统界面*/
    public void clearGameArchi(){
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_shop_midding);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_shop_go_pay);
    	Resource.freeImage(Resource.id_achievement);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
    	Resource.freeImage(Resource.id_achievement_long);
    	Resource.freeImage(Resource.id_achievement_long1);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_achievement_points);
    	Resource.freeImage(Resource.id_archivement_hoof);
    	Resource.freeImage(Resource.id_archivement_hoof1);
    	Resource.freeImage(Resource.id_achievement_left);
    	Resource.freeImage(Resource.id_achievement_word);
    }
    
    /*清除排行系统界面*/
    public void clearRanking() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_current_ranking);
    	Resource.freeImage(Resource.id_ranking_option);
    	Resource.freeImage(Resource.id_ranking_option1);
    	Resource.freeImage(Resource.id_ranking_stripe);
    	Resource.freeImage(Resource.id_ranking);
    	Resource.freeImage(Resource.id_ranking_show);
	}
    
    /*清除帮助界面*/
    public void clearHelp() {
    	Resource.freeImage(Resource.id_game_bg);
    	Resource.freeImage(Resource.id_achievement_out1);
    	Resource.freeImage(Resource.id_shop_big);
    	Resource.freeImage(Resource.id_game_help);
    	Resource.freeImage(Resource.id_achievement_left_right);
    	Resource.freeImage(Resource.id_achievement_left_right1);
	}
    
    /*画出主菜单mainMenu*/
	public void drawMainMenu(Graphics g, int index) {
		Image main_bg = Resource.loadImage(Resource.id_main_bg);
		Image main_menu = Resource.loadImage(Resource.id_main_menu);
		g.drawImage(main_bg, 0, 0, 0);
		for (int i = 0; i < menuAxis.length; ++i) {
			g.drawRegion(main_menu,
					(index != i) ? main_menu.getWidth() / 2 : 0,
					i * main_menu.getHeight() / 6, main_menu.getWidth() / 2,
					main_menu.getHeight() / 6, 0, menuAxis[i][0],
					menuAxis[i][1], 0);
		}
	}
    
    /*画出游戏界面*/
	public void drawGamePlaying(Graphics g, int index,Role own) {
		
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image playing_menu = Resource.loadImage(Resource.id_playing_menu);// {491,0}
		Image playing_cloudbig = Resource.loadImage(Resource.id_playing_cloudbig);
		Image playing_cloudsmall = Resource.loadImage(Resource.id_playing_cloudsmall);// {404,164}
		Image playing_lawn = Resource.loadImage(Resource.id_playing_lawn);// {0,499}
		Image playing_step = Resource.loadImage(Resource.id_playing_step);// {377,153},{377,240}{377,324}{377,409} Y 相差89
		Image playing_tree = Resource.loadImage(Resource.id_playing_tree);// {0,72}
		Image playing_lunzi = Resource.loadImage(Resource.id_playing_lunzi);//{374,132}
		Image playing_shenzi = Resource.loadImage(Resource.id_playing_shenzi); //{379,154}
		Image playing_lift = Resource.loadImage(Resource.id_playing_lift); //{342,303}
		Image playing_shenzi1 = Resource.loadImage(Resource.id_playing_shenzi1); //{399, 135}//横放的绳子
		Image playing_prop_memu = Resource.loadImage(Resource.id_playing_prop_memu); //{497,192}{564,192}//上下相差70
		Image playing_stop = Resource.loadImage(Resource.id_playing_stop); //{501,466}
		Image bomb = Resource.loadImage(Resource.id_bomb); //{501,466}
		g.drawImage(game_bg, 0, 0, TopLeft);
		
		if(tempx+playing_cloudbig.getWidth()>0){
			tempx -= 1;
		}else{
			tempy = RandomValue.getRandInt(0, 114);
			tempx = ScrW;
		}
		g.drawRegion(playing_cloudbig, 0, 0, playing_cloudbig.getWidth(), playing_cloudbig.getHeight(), 
				0, tempx, tempy, TopLeft);
		
		if(tempx2+playing_cloudsmall.getWidth()>0){
			tempx2 -= 2;
		}else{
			tempy2 = RandomValue.getRandInt(0, 114);
			tempx2 = ScrW;
		}
		g.drawRegion(playing_cloudsmall, 0, 0, playing_cloudsmall.getWidth(), playing_cloudsmall.getHeight(), 
				0, tempx2, tempy2, TopLeft);
		g.drawImage(playing_lawn, 0, 499, TopLeft);
		g.drawImage(playing_tree, 0, 72, TopLeft);
		g.drawImage(playing_shenzi1, 399, 135, TopLeft);
		for(int i=0;i<4;i++){   //阶梯
			g.drawImage(playing_step, 377, 153+i*89, TopLeft);
		}
		g.drawRegion(playing_shenzi, 0, 0, playing_shenzi.getWidth(), (own.mapy-154),        //上下移动的绳子
				0, 379, 154, TopLeft);                                                        //竖直绳子 的纵坐标 154

		g.drawRegion(playing_lift, 0, 0, playing_lift.getWidth(), playing_lift.getHeight(),     //羊的吊篮
				0, 342, 154+(own.mapy-154), TopLeft);
		g.drawRegion(bomb, 0, 0, bomb.getWidth()/3, bomb.getHeight(), 0, 345-18, 40+own.mapy, TopLeft); //吊篮上的飞镖
		
		g.drawImage(playing_lunzi, 374,132, TopLeft);
		g.drawImage(playing_menu, 491, 0, TopLeft);
		for(int i=0;i<4;i++){                                                                //游戏中的左侧框内容---道具内容
			g.drawImage(playing_prop_memu, 497,185+i*68, TopLeft);
			drawProp(g, i, 497+5,185+i*(68+3));                                              //第一列对应原图片中的前四个
			drawNum(g, i+1, 540+7, 223-17+i*73);//提示技能按键：1-4{540,223}
			
			g.drawImage(playing_prop_memu, 564,185+i*68, TopLeft);
			drawProp(g, i+4, 564+5,185+i*(68+2));  //第二列对应原图片中的后四个
			drawNum(g, i+4+1, 612, 223-17+i*73);//提示技能键5-8{}
		}
		g.drawImage(playing_stop, 500,459, TopLeft);//暂停游戏按钮
		
	}
	
	/*画商店界面*/
	public void drawGameShop(Graphics g,int shopX,int shopY) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_balance = Resource.loadImage(Resource.id_shop_balance);//{46,454}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{29,103}
		//Image shop_figure = Resource.loadImage(Resource.id_shop_figure);//{103,452}  数字
		Image shop_go_pay = Resource.loadImage(Resource.id_shop_go_pay);//{457,381}
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{434,103}
		Image shop_out_base = Resource.loadImage(Resource.id_shop_out_base);
		Image shop_out = Resource.loadImage(Resource.id_shop_out);//{457,429}
		Image shop_small_base = Resource.loadImage(Resource.id_shop_small_base);
		Image shop_small = Resource.loadImage(Resource.id_shop_small);
		Image price_quantity = Resource.loadImage(Resource.id_price_quantity);
		Image shop = Resource.loadImage(Resource.id_shop);//{217,18}
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		//{42,115},{233,115},{42,195},{233,195},{42,279},{233,279},{42,361},{233,361}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop, 217, 18, TopLeft);
		g.drawImage(shop_big, 29, 103, TopLeft);
		g.drawImage(shop_balance, 46, 454, TopLeft);
	
		int x =42, y = 120, spaceX = 15, spaceY = 8;//
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				g.drawRegion(shop_small_base, 0, 0, shop_small_base.getWidth(), shop_small_base.getHeight(),
						0, x+(spaceX+shop_small_base.getWidth())*j, y+(spaceY+shop_small_base.getHeight())*i, TopLeft);
			}
		}
		g.drawImage(shop_midding, 434, 103, TopLeft);
		for(int i=0;i<2;i++){             //midding下的按钮阴影
			g.drawRegion(shop_out_base, 0, 0, shop_out_base.getWidth(), shop_out_base.getHeight(), 
					0, 457, 381+(spaceY+shop_out_base.getHeight())*i, TopLeft);
			}
		int mapx=37,mapy=112;       //被选中后出现阴影效果的坐标
		 for(int i=0;i<4;i++){
		     for(int j=0;j<2;j++){
				if(shopX==j && shopY==i){
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(),
							0, mapx+(spaceX+shop_small.getWidth())*j, mapy+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, mapx+(spaceX+shop_small.getWidth())*j+65, 
							mapy+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							mapx+(spaceX+shop_small.getWidth())*j+8, mapy+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, mapx+(spaceX+shop_small.getWidth())*j+119, mapy+(spaceY+shop_small.getHeight())*i+36);
				}else{
					g.drawRegion(shop_small, 0, 0, shop_small.getWidth(), shop_small.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j, y+(spaceY+shop_small.getHeight())*i, TopLeft);
					g.drawImage(price_quantity, x+(spaceX+shop_small.getWidth())*j+65, 
							y+(spaceY+shop_small.getHeight())*i+12, TopLeft);
					g.drawRegion(playing_prop, getIndex(j, i)*playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8, playing_prop.getHeight(), 0,
							x+(spaceX+shop_small.getWidth())*j+8, y+(spaceY+shop_small.getHeight())*i+9, TopLeft);
					drawNum(g, 111, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+11);
					drawNum(g, 333, x+(spaceX+shop_small.getWidth())*j+119, y+(spaceY+shop_small.getHeight())*i+36);
				}
			}
		}
		 if(shopX==2){          //充值和返回被选择的阴影效果
			 if(shopY==0){    //控制方向由左到右的入口方向
				 g.drawImage(shop_go_pay, 457-8, 381-5, TopLeft);
			   	 g.drawImage(shop_out, 457, 429, TopLeft);
			  }else{
				 g.drawImage(shop_go_pay, 457, 381, TopLeft);
			   	 g.drawImage(shop_out, 457-8, 429-5, TopLeft);
			 }
		    }else{
		    	g.drawImage(shop_go_pay, 457, 381, TopLeft);
		   		g.drawImage(shop_out, 457, 429, TopLeft);
		    }
		drawNum(g, 10, 103,452);                                          //TODO 添加数字
	}
	
	private int getIndex(int x, int y){    //取出对应商店商场左侧的位置
		if(y==0 && x==0)return 0;
		if(y==1 && x==0)return 1;
		if(y==2 && x==0)return 2;
		if(y==3 && x==0)return 3;
		if(y==0 && x==1)return 4;
		if(y==1 && x==1)return 5;
		if(y==2 && x==1)return 6;
		if(y==3 && x==1)return 7;
		if(x==2)        return 8;
		return -1;
	}
	
	/*画出成就系统*/
	public void drawGameArchi(Graphics g,int archX,int archY) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_midding = Resource.loadImage(Resource.id_shop_midding);//{28,102}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{235,102}
		Image achievement_left = Resource.loadImage(Resource.id_achievement_left);//{457,381}//{51,123},{51,178},{51,232},{51,286},{51,342},{51,396}
		Image achievement = Resource.loadImage(Resource.id_achievement);//{270,19}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{458,441}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);
		Image achievement_long = Resource.loadImage(Resource.id_achievement_long);//{247,114},{247,198},{247,277},{247,361},{}
		Image achievement_long1 = Resource.loadImage(Resource.id_achievement_long1);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{55,451}
		Image achievement_points = Resource.loadImage(Resource.id_achievement_points);//{250,448}
		Image archivement_hoof = Resource.loadImage(Resource.id_archivement_hoof);//{539,130},{539,211},{539,293},{539,378}
		Image archivement_hoof1 = Resource.loadImage(Resource.id_archivement_hoof1);
		Image achievement_word = Resource.loadImage(Resource.id_achievement_word);
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement, 270, 19, TopLeft);
		g.drawImage(shop_midding, 28, 102, TopLeft);
		g.drawImage(achievement_out1, 55, 451, TopLeft);
		g.drawImage(shop_big, 235, 102, TopLeft);
		g.drawImage(achievement_points, 250, 448, TopLeft);
		g.drawImage(achievement_left_right,458,441, TopLeft);
		
		int x=247,y=116,spaceY=4;
		for(int i=0;i<4;i++){//判断光标指向右侧哪个模块
			if(archX==1 && archY==i){   
				g.drawRegion(achievement_long, 0, 0, achievement_long.getWidth(), achievement_long.getHeight(), 0,
						x, y+(spaceY+achievement_long.getHeight())*i, TopLeft);
				g.drawRegion(archivement_hoof, 0, 0, archivement_hoof.getWidth(), archivement_hoof.getHeight(), 0,
						x+289, y+12+(spaceY+achievement_long.getHeight())*i, TopLeft);//hoof相对于底层的坐标是289  
				drawNum(g, 10, 546, y+(achievement_long.getHeight()+spaceY)*i+26);
			}else{
				g.drawRegion(achievement_long1, 0, 0, achievement_long1.getWidth(), achievement_long1.getHeight(), 0,
						x, y+(spaceY+achievement_long1.getHeight())*i, TopLeft);
				g.drawRegion(archivement_hoof1, 0, 0, archivement_hoof1.getWidth(), archivement_hoof1.getHeight(), 0,
						x+289, y+12+(spaceY+31+archivement_hoof1.getHeight())*i, TopLeft);//hoof相对于底层的坐标是289
				drawNum(g, 30, 546, y+(achievement_long.getHeight()+spaceY)*i+26);
			}
		}
		int leftX = 52,leftY = 122,leftSpace = 15;     //成就左侧leftSpace:上下间隔
		for(int i=0;i<6;i++){       //成就左侧条目
			if(archX==0 && archY==i){
				g.drawRegion(achievement_left, 0, 0, achievement_left.getWidth(), achievement_left.getHeight(), 0,
						leftX, leftY+(achievement_left.getHeight()+leftSpace)*i, TopLeft);
				DrawUtil.drawRect(g, leftX, leftY+archY*(achievement_left.getHeight()+leftSpace), 
						achievement_left.getWidth(), achievement_left.getHeight(), 2, 0XFFFF00);  // 画出矩形方框  ---2 是线条宽度
			}else{
				g.drawRegion(achievement_left, 0, 0, achievement_left.getWidth(), achievement_left.getHeight(), 0,
						leftX, leftY+(achievement_left.getHeight()+leftSpace)*i, TopLeft);
			}
		}
		for(int i = 0;i<archLeftAxis.length;++ i){     //成就左侧条目文字图片
//			if(i!=1){
				g.drawRegion(achievement_word,0,
						i*achievement_word.getHeight() / 6, achievement_word.getWidth(),
						achievement_word.getHeight() / 6, 0, archLeftAxis[i][0],
						archLeftAxis[i][1], TopLeft);
//			}else{
//				g.drawImage(achievement_word, archLeftAxis[i][0], archLeftAxis[i][1], TopLeft);
//			}
		}
	}
	
	/*画出排行榜*/
	public void showRanking(Graphics g, int rankingIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);//{61,462}
		Image shop_big = Resource.loadImage(Resource.id_shop_big);//{233,101}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);//{457,440}
		Image current_ranking=Resource.loadImage(Resource.id_current_ranking);//{253,448}
		Image ranking_option=Resource.loadImage(Resource.id_ranking_option);//{39,112} Y相差54 
		Image ranking_option1=Resource.loadImage(Resource.id_ranking_option1);
		Image ranking_stripe=Resource.loadImage(Resource.id_ranking_stripe);//{241,151}  条高度57
		Image ranking=Resource.loadImage(Resource.id_ranking);//{232,18}
		Image ranking_show=Resource.loadImage(Resource.id_ranking_show);//{241,108}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(achievement_out1, 61,462, TopLeft);
		for(int i=0;i<3;i++){//排行左侧条目
			g.drawImage(ranking_option, 39, 112+i*54, TopLeft);
		}
		g.drawImage(shop_big, 233,101, TopLeft);
		g.drawImage(ranking_show,241,108, TopLeft);
		for(int i=0;i<5;i++){
			g.drawImage(ranking_stripe,241,151+i*57, TopLeft);
		}
		g.drawImage(current_ranking, 253,448, TopLeft);
		g.drawImage(ranking, 232,18, TopLeft);
		g.drawImage(achievement_left_right, 457,440, TopLeft);
	}
	
	/*画出帮助界面*/
	public void showHelp(Graphics g,int helpIndex,int pageIndex) {
		Image game_bg = Resource.loadImage(Resource.id_game_bg);
		Image shop_big = Resource.loadImage(Resource.id_shop_big);       //{137,108}
		Image game_help = Resource.loadImage(Resource.id_game_help);     //{214,18}
		Image achievement_out1 = Resource.loadImage(Resource.id_achievement_out1);   //{17,498}
		Image achievement_left_right = Resource.loadImage(Resource.id_achievement_left_right);   //{}
		Image achievement_left_right1 = Resource.loadImage(Resource.id_achievement_left_right1);   //{380,452}
		g.drawImage(game_bg, 0, 0, TopLeft);
		g.drawImage(shop_big, 137, 108, TopLeft);
//		g.drawImage(achievement_left_right1, 380, 490-achievement_left_right.getHeight(), TopLeft);
//		g.drawRegion(achievement_left_right1, 0, 0, achievement_left_right1.getWidth(), achievement_left_right1.getHeight(), 0
//				, 380, 452, TopLeft);
//		g.drawRegion(achievement_left_right, pageIndex==0?0:achievement_left_right1.getWidth()/2,
//				0, achievement_left_right1.getWidth()/2, achievement_left_right1.getHeight(), 0, 380, 452, TopLeft);
//		g.drawRegion(achievement_left_right1, pageIndex==1?0:achievement_left_right1.getWidth()/2,
//				0, achievement_left_right1.getWidth()/2, achievement_left_right1.getHeight(), 0, 
//				380+achievement_left_right.getWidth()/2, 452, TopLeft);
		if(pageIndex==0){
			g.drawRegion(achievement_left_right, 0, 0, achievement_left_right.getWidth()/2, achievement_left_right.getHeight(), 0,
					380, 452, TopLeft);
			g.drawRegion(achievement_left_right1, achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2, achievement_left_right.getHeight(),
					0, 380+achievement_left_right.getWidth()/2, 452, TopLeft);
		}else{
			g.drawRegion(achievement_left_right1, 0, 0, achievement_left_right.getWidth()/2, achievement_left_right.getHeight(), 0,
					380, 452, TopLeft);
			g.drawRegion(achievement_left_right, achievement_left_right.getWidth()/2, 0, achievement_left_right.getWidth()/2, achievement_left_right.getHeight(),
					0, 380+achievement_left_right.getWidth()/2, 452, TopLeft);
		}
		g.drawImage(game_help, 214,18, TopLeft);
		g.drawImage(achievement_out1, 17,498, TopLeft);
		g.setColor(0xffffff);
//        engine.setFont(5);
		g.setFont(Font.getDefaultFont());
//		String info="";
//		for(int i=0;i<gameIntro.length;i++){
//			info += gameIntro[i];
//		}
//		TextView.showMultiLineText(g, info, 10, 150, 130, 350, 350);
		TextView.showMultiLineText(g, Resource.gameInfo[helpIndex], 10,150, 130, 360, 334);
	}
	
	/*游戏中的数字*/
	private void drawNum(Graphics g, int num, int x, int y) {
		Image imgNumeber = Resource.loadImage(Resource.id_shop_figure);
		String number = String.valueOf(num);
		for (byte i = 0; i < number.length(); i++) {
			g.drawRegion(imgNumeber, (number.charAt(i) - '0') * imgNumeber.getWidth()/10, 0, 
					imgNumeber.getWidth()/10, imgNumeber.getHeight(), 0, x + i * (imgNumeber.getWidth()/10 + 1), y, 0);
		}
	}
	
	/*道具的图片加载----数字转化为图片*/
	private void drawProp(Graphics g,int num,int x,int y){
		Image playing_prop=Resource.loadImage(Resource.id_playing_prop);
		String number=String.valueOf(num);
		for(byte i=0;i<number.length();i++){
			g.drawRegion(playing_prop, (number.charAt(i) - '0')* playing_prop.getWidth()/8, 0, playing_prop.getWidth()/8,
					playing_prop.getHeight(), 0, x+i * (playing_prop.getWidth()/8 + 1), y, 0);
		}
	}
}
