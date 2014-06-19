package gameview;

import util.MagicCloud;
import util.MessageEnum;
import util.Util;

import com.example.linklinklook.MainActivity;
import com.example.linklinklook.R;

import DrawThread.GameCountViewDrawThread;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 每个游戏关卡结束之后的游戏统计界面
 * 
 * @author Administrator
 * 
 */
public class GameCountView extends SurfaceView implements Callback {

	private MainActivity mainActivity;

	private Bitmap background;

	private Rect backgroundRect;

	private Paint textPaint;

	/**
	 * 绘制矩形的画笔
	 */
	private Paint rectPaint;

	/**
	 * 用来绘制点击屏幕开始游戏文本
	 */
	private Paint bottomPaint;

	/**
	 * 点击屏幕开始游戏文本的alpha值
	 */
	private int bottomAlpha;

	/**
	 * 点击屏幕开始游戏文本的alpha是否增加
	 */
	private boolean bottomAlphaGrow = false;

	private MagicCloud magicCloud = new MagicCloud(this);

	private GameCountViewDrawThread gameCountViewDrawThread;

	public GameCountView(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity;
		getHolder().addCallback(this);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);
		backgroundRect = new Rect(0, 0, Util.WIDTH_DEVICE, Util.HEIGTH_DEVICE);
		magicCloud.initMagicCloud();
		// 绘制文本的画笔
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		// 绘制背景矩形的画笔
		rectPaint = new Paint();
		rectPaint.setAlpha(100);
		rectPaint.setColor(Color.WHITE);
		// 绘制底部点击屏幕开始游戏画笔
		bottomPaint = new Paint();
		bottomPaint.setColor(Color.BLUE);
		bottomPaint.setTextSize(30);
		bottomPaint.setStyle(Style.STROKE);
		bottomPaint.setTextAlign(Align.CENTER);
		bottomAlpha = 255;
		bottomPaint.setAntiAlias(true);
		gameCountViewDrawThread = new GameCountViewDrawThread(this, getHolder());
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(background, null, backgroundRect, textPaint);
		magicCloud.doDraw(canvas);
		Rect rect = new Rect((int)(Util.WIDTH_DEVICE * 0.1), (int)(Util.HEIGTH_DEVICE * 0.2), 
				(int)(Util.WIDTH_DEVICE * 0.9), (int)(Util.HEIGTH_DEVICE * 0.8));
		//绘制矩形
		canvas.drawRect(rect, rectPaint);
		//绘制文本内容
		textPaint.setTextSize(16);
		textPaint.setColor(Color.GREEN);
		textPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("闯关成功否：", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.40), textPaint);
		canvas.drawText("用时：", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.47), textPaint);
		canvas.drawText("错误次数：", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.54), textPaint);
		textPaint.setTextSize(28);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.RED);
		canvas.drawText("第" + Util.GAME_LEVEL + "关完成", (int)(Util.WIDTH_DEVICE  * 0.5), (int)(Util.HEIGTH_DEVICE * 0.26), textPaint);
		if (mainActivity.gameCount != null) {
			textPaint.setTextAlign(Align.LEFT);
			textPaint.setTextSize(16);
			textPaint.setColor(Color.GREEN);
			canvas.drawText(String.valueOf(mainActivity.gameCount.getuseTime()), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.47), textPaint);
			canvas.drawText(mainActivity.gameCount.getString(), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.40), textPaint);
			canvas.drawText(String.valueOf(mainActivity.gameCount.getWrongCount()), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.54), textPaint);
		}
		//绘制点击屏幕开始游戏文字
		bottomPaint.setAlpha(bottomAlpha);
		canvas.drawText("点击屏幕开始游戏", (int)(Util.WIDTH_DEVICE*0.5), (int)(Util.HEIGTH_DEVICE * 0.9), bottomPaint);
		if (bottomAlphaGrow) {
			bottomAlpha += 20;
			if (bottomAlpha >= 255) {
				bottomAlphaGrow = false; 
			}
		} else {
			bottomAlpha -= 20;
			if (bottomAlpha <= 0) {
				bottomAlphaGrow = true;
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 点击屏幕上任意位置均可开始下一关游戏
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mainActivity.myHandler.sendEmptyMessage(MessageEnum.NEXTLEVEL
					.ordinal());
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameCountViewDrawThread.setFlag(true);
		gameCountViewDrawThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		gameCountViewDrawThread.setFlag(false);
		while (retry) {
			try {
				gameCountViewDrawThread.join();// 等待刷帧线程结束
				retry = false;
			} catch (InterruptedException e) {// 不断地循环，直到等待的线程结束
			}
		}
	}

}
