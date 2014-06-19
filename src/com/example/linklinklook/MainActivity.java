package com.example.linklinklook;


import util.GameCount;
import util.MessageEnum;
import util.Util;
import gameview.GameCountView;
import gameview.Gameview;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends ActionBarActivity {

	public Gameview gameview;
	public GameCountView gameCountView;
	public GameCount gameCount;
	private int y;
	private int x;
	
	
	public Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.obj instanceof GameCount){
				gameCount=(GameCount)msg.obj;
				initGameCount();
			} else if (msg.what == MessageEnum.BEGIN_GAME.ordinal()) {
				initGameView();
			} else if (msg.what == MessageEnum.NEXTLEVEL.ordinal()) {
				nextLevel();
			}
			
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
				              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//强制设置屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //全屏
		
		Util.WIDTH_DEVICE = getWindowManager().getDefaultDisplay().getWidth();
		Util.HEIGTH_DEVICE = getWindowManager().getDefaultDisplay().getHeight();
//		this.setContentView(new Gameview(getApplicationContext()));
		x=6;
		y=5;
		initGameView();
//		initGameCount();

	}
	protected void initGameView() {
		gameview = new Gameview(this,y,x);
//		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		gameview.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
		this.setContentView(gameview);
	}
	/**
	 * 初始化游戏的统计界面
	 */
	private void initGameCount() {
		gameCountView = new GameCountView(this);
		this.setContentView(gameCountView);
	}
	
	/**
	 * 开始下一关游戏
	 */
	private void nextLevel() {
		Util.GAME_TIME_NUM-=10;
		Util.GAME_LEVEL++;
		y++;
		gameview = new Gameview(this,y,x);
		this.setContentView(gameview);
	}
}
