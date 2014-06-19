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
 * ÿ����Ϸ�ؿ�����֮�����Ϸͳ�ƽ���
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
	 * ���ƾ��εĻ���
	 */
	private Paint rectPaint;

	/**
	 * �������Ƶ����Ļ��ʼ��Ϸ�ı�
	 */
	private Paint bottomPaint;

	/**
	 * �����Ļ��ʼ��Ϸ�ı���alphaֵ
	 */
	private int bottomAlpha;

	/**
	 * �����Ļ��ʼ��Ϸ�ı���alpha�Ƿ�����
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
		// �����ı��Ļ���
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		// ���Ʊ������εĻ���
		rectPaint = new Paint();
		rectPaint.setAlpha(100);
		rectPaint.setColor(Color.WHITE);
		// ���Ƶײ������Ļ��ʼ��Ϸ����
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
		//���ƾ���
		canvas.drawRect(rect, rectPaint);
		//�����ı�����
		textPaint.setTextSize(16);
		textPaint.setColor(Color.GREEN);
		textPaint.setTextAlign(Align.RIGHT);
		canvas.drawText("���سɹ���", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.40), textPaint);
		canvas.drawText("��ʱ��", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.47), textPaint);
		canvas.drawText("���������", (int)(Util.WIDTH_DEVICE * 0.4), (int)(Util.HEIGTH_DEVICE * 0.54), textPaint);
		textPaint.setTextSize(28);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.RED);
		canvas.drawText("��" + Util.GAME_LEVEL + "�����", (int)(Util.WIDTH_DEVICE  * 0.5), (int)(Util.HEIGTH_DEVICE * 0.26), textPaint);
		if (mainActivity.gameCount != null) {
			textPaint.setTextAlign(Align.LEFT);
			textPaint.setTextSize(16);
			textPaint.setColor(Color.GREEN);
			canvas.drawText(String.valueOf(mainActivity.gameCount.getuseTime()), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.47), textPaint);
			canvas.drawText(mainActivity.gameCount.getString(), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.40), textPaint);
			canvas.drawText(String.valueOf(mainActivity.gameCount.getWrongCount()), (int)(Util.WIDTH_DEVICE  * 0.42), (int)(Util.HEIGTH_DEVICE * 0.54), textPaint);
		}
		//���Ƶ����Ļ��ʼ��Ϸ����
		bottomPaint.setAlpha(bottomAlpha);
		canvas.drawText("�����Ļ��ʼ��Ϸ", (int)(Util.WIDTH_DEVICE*0.5), (int)(Util.HEIGTH_DEVICE * 0.9), bottomPaint);
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
		// �����Ļ������λ�þ��ɿ�ʼ��һ����Ϸ
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
				gameCountViewDrawThread.join();// �ȴ�ˢ֡�߳̽���
				retry = false;
			} catch (InterruptedException e) {// ���ϵ�ѭ����ֱ���ȴ����߳̽���
			}
		}
	}

}
