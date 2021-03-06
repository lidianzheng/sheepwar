package sheepwar;

/*
 * 公共变量类 
 */
public interface Common {
	
	public final static int STATUS_INIT = 0;			//初始
	public final static int STATUS_MAIN_MENU = 1;		//游戏主菜单 
	public final static int STATUS_GAME_PLAYING = 2;	//游戏中

	public final static int ScrW = SheepWarGameEngine.ScrW;
	public final static int ScrH = SheepWarGameEngine.ScrH;
	public final static int gameW = 490;				//游戏区域宽度
	
	public final static short ROLE_ALIVE = 0; 			//角色活着状态 
	public final static short ROLE_DEATH = -1;  		//角色活着状态 
	public final static short ROLE_SUCCESS = 1;  		//狼逃脱的状态 
	
	public final static short ROLE_ON_GROUND = 0;  		//狼在地面
	public final static short ROLE_IN_AIR = 1;  		//狼离开地面
	
	public final static short ON_ONE_LADDER = 1; 			//角色在第一个梯子上
	public final static short ON_TWO_LADDER = 2;  			//角色在第二个梯子上 
	public final static short ON_THREE_LADDER = 3;  		//角色在第三个梯子上
	public final static short ON_FOUR_LADDER = 4;  			//角色在第四个梯子上
	
	/*狼的移动方向*/
	public final static int ROLE_MOVE_UP = 0;  		//上
	public final static int ROLE_MOVE_DOWN = 1;		//下
	public final static int ROLE_MOVE_LEFT = 2;		//左
	public final static int ROLE_MOVE_RIGHT = 3;	//右
	
	/*狼的初始Y轴位置*/
	public final static short WOLF_POSITION_TOP = 0;		//狼出现的初始位置（上面）
	public final static short WOLF_POSITION_BOTTOM = -1;	//狼出现的初始位置（下面）
	
	/*狼在空中分布方式*/
	public final static short NONE = 0;					//无
	public final static short SPREED_BELOW = 1;			//斜下直线
	public final static short SPREED_ABOVE = 2;			//斜上直线
	public final static short SPREED_VERTICAL = 3;		//竖直
	public final static short SPREED_IRREGULAR = 4;		//折线
	
	/*气球ID(对应颜色)*/
	public final static short blue = Resource.id_balloon_blue;					//蓝气球
	public final static short green = Resource.id_balloon_green;				//绿气球
	public final static short multicolour = Resource.id_balloon_multicolour;	//彩色气球
	public final static short red = Resource.id_balloon_red;					//红气球
	public final static short yellow = Resource.id_balloon_yellow;				//黄气球
	public final static short orange = Resource.id_balloon_yellowred;			//橙色气球
	
	/*水果id*/
	public final static short apple = Resource.id_apple;						//苹果
	public final static short lemon = Resource.id_lemon;						//柠檬
	public final static short pear = Resource.id_orange;						//鸭梨
	public final static short watermelon = Resource.id_watermelon;				//西瓜
	
	/*水果状态*/
	public static short FRUIT_NOT_HIT = 0;			//水果被击中
	public static short FRUIT_HIT = 1;				//水果未被击中
	
	public static short BOOM_NOT_HIT = 0;				//水果未被击中
	public static short BOOM_HIT = 1;					//水果未被击中
	
	/*随即选中水果*/
	public final static short selectFruit[] = {apple,lemon,pear,watermelon};
	
	/*折线方式*/
	public final static short[][] regular = {
		{4,2,3,1},
		{1,2,4,3},
		{1,3,4,3},
		{4,3,2,3},
		{3,1,2,4},
	};
	
	/*正常关卡（15关），每批狼出现的方式*/
	public static int[][][] BatchesInfo = {
		{/*第一关*/   
			/*0-数量， 1-气球ID(对应颜色) 2-空中分布方式 */
			{4, orange, SPREED_ABOVE },
			{2, blue, SPREED_VERTICAL },
			{2, orange, SPREED_BELOW },
			{2, blue, SPREED_ABOVE },
			{3, orange, SPREED_BELOW },
			{1, blue, SPREED_IRREGULAR },
			{4, blue, SPREED_ABOVE },
			{2, red, SPREED_VERTICAL },
			{2, orange, SPREED_ABOVE },
		},
		{/*第二关*/   
			/*0-数量， 1-气球ID(对应颜色), 2-空中分布方式*/
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
		},
		{/*第三关*/   
			/*0-数量， 1-气球ID(对应颜色), 2-空中分布方式*/
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
			{4, orange, SPREED_IRREGULAR },
		},
		{/*第四关*/
			{1,green,NONE},
			{2,green,SPREED_VERTICAL},
			{2,green,SPREED_BELOW},
			{2,green,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_ABOVE},
			{4,green,SPREED_ABOVE},
		},
		{/*第五关*/
			{4,green,SPREED_BELOW},
			{4,green,SPREED_VERTICAL},
		},
		{/*第六关*/
			{1,multicolour,NONE},
			{2,green,SPREED_BELOW},				//斜下直线
			{2,green,SPREED_VERTICAL},			//竖直线
			{2,red,SPREED_BELOW},
			{3,green,SPREED_ABOVE},				//斜上直线
			{3,red,SPREED_BELOW},
			{4,red,SPREED_ABOVE},
			{4,red,SPREED_VERTICAL},
			{4,green,SPREED_VERTICAL},
			{4,green,SPREED_ABOVE},
		},
		{/*第七关*/
			{1, green, NONE },
			{1, multicolour, NONE},
			{2, green, SPREED_VERTICAL },
			{2, green, SPREED_ABOVE },
			{4, green, SPREED_ABOVE },
			{4, green, SPREED_BELOW },
			{4, green, SPREED_IRREGULAR },			
		},
		{/*第八关*/
			{1, multicolour, NONE },
			{1, yellow, NONE},
			{2, green, SPREED_VERTICAL },
			{2, green, SPREED_ABOVE },
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_BELOW },	
			{4, green, SPREED_IRREGULAR },			
			{4, green, SPREED_IRREGULAR },			
		},
		{/*第九关*/
			{1, multicolour, NONE },
			{1, yellow, NONE},
			{4, green, SPREED_ABOVE },
			{4, green, SPREED_ABOVE },			
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_BELOW },		
			{4, green, SPREED_IRREGULAR },				
			{4, green, SPREED_IRREGULAR },				
		},
		{/*第十关*/
			{1, yellow, NONE },
			{1, multicolour, NONE},
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_ABOVE },		
			{4, green, SPREED_VERTICAL },	
			{4, green, SPREED_IRREGULAR },		
			{4, green, SPREED_VERTICAL },
			{4, green, SPREED_IRREGULAR },		
			{4, green, SPREED_ABOVE },			
		},
		{/*第十一关*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },	
			{3, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_VERTICAL },	
			{2, yellow, SPREED_ABOVE},	
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*第十二关*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },	
			{4, yellow, SPREED_BELOW},	//折线	
			{2, yellow, SPREED_ABOVE},	
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*第十三关*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{5, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*第十四关*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{4, yellow, SPREED_BELOW },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
		},
		{/*第十五关*/
			{4, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{4, yellow, SPREED_BELOW },
			{2, yellow, SPREED_BELOW},	
			{2, yellow, SPREED_ABOVE},
			{4, yellow, SPREED_VERTICAL },
			{1, multicolour, NONE },
			{1, yellow, NONE },
		},
	};
	
	/*奖励关卡中role出现的方式*/
	public static int [][][] RewardLevelBatchesInfo = {
		/*奖励关卡第一关*/
		{/*0-数量， 1-气球ID(对应颜色) 2-空中分布方式 */
			{2,orange,SPREED_ABOVE},
			{4,orange,SPREED_ABOVE},
			{4,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
			{2,orange,SPREED_ABOVE},
		},
		/*奖励关卡2*/
		{	/*0-数量， 1-水果ID， 2-空中分布方式 */
			{2,orange,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,green,SPREED_BELOW},
		},
		{//奖励关卡3
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,multicolour,SPREED_BELOW},
		},
		{//奖励关卡4
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
		{//奖励关卡5
			{2,orange,SPREED_BELOW},
			{2,orange,SPREED_BELOW},
			{2,multicolour,SPREED_BELOW},
		},
		{//奖励关卡6
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
		{//奖励关卡7
			{2,red,SPREED_BELOW},
			{4,green,SPREED_BELOW},
			{4,orange,SPREED_BELOW},
		},
	};
	
}
