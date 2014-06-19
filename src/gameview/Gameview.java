package gameview;

import java.util.ArrayList;
import java.util.List;

import util.GameCount;
import util.MagicCloud;
import util.MessageEnum;
import util.Util;

import com.example.linklinklook.MainActivity;
import com.example.linklinklook.R;

import DrawThread.GameViewDrawThread;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Gameview extends SurfaceView implements SurfaceHolder.Callback {

	private List<Bitmap> list;
	private Logic logic;
	private boolean lock_the_fisrtPoint = false;
	private Point p_firsttouch;
	private Point p_secondtouch;
	private Point currentpoint;
	private int[][] data;
	private MagicCloud magicCloud = new MagicCloud(this);
	private GameViewDrawThread caDrawThread;
	private MainActivity activity;
	private boolean isstarted = false;

	public int timess;
	private boolean Lock_the_game = true;
	private Bitmap[] timeBitmaps=new Bitmap[10];
	private TimeThread timeThread;
	
	private GameCount gameCount=new GameCount();
	
	
	public Gameview(MainActivity mainActivity,int y,int x) {
		super(mainActivity);
		activity = mainActivity;
		getHolder().addCallback(this);
		logic = new Logic(y,x);
		data = logic.getData();
		timess=Util.GAME_TIME_NUM;
		initalconstant();
		initalBitmapList();
		magicCloud.initMagicCloud();

	}

	private void initalconstant() {

		Util.ROWSIZE = logic.getHeigth() - 2;
		Util.COLUMNSIZE = logic.getWith() - 2;
		
		int tmp = Util.WIDTH_DEVICE / (Util.COLUMNSIZE + 1);
		int tmp2 = Util.HEIGTH_DEVICE / (Util.ROWSIZE + 1);
		if (tmp > tmp2)
			tmp = tmp2;
		if (tmp > 100)
			tmp = 100;
		Util.IMAGE_LENGTH = tmp;

		Util.LEFT_TO_SCREEM = (Util.WIDTH_DEVICE - Util.COLUMNSIZE
				* Util.IMAGE_LENGTH) / 2;
		Util.UP_TO_SCREEN = (Util.HEIGTH_DEVICE - Util.ROWSIZE
				* Util.IMAGE_LENGTH) / 2;

	}

	private void initalBitmapList() {
		list = new ArrayList<Bitmap>();

		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds0),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds1),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds2),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds3),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds4),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds5),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds6),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds7),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds8),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds9),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.fds10),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true));
		list.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.background1), Util.WIDTH_DEVICE,
				Util.HEIGTH_DEVICE, true));
		
		
		timeBitmaps[0]=BitmapFactory.decodeResource(getResources(), R.drawable.time0);
		timeBitmaps[1]=BitmapFactory.decodeResource(getResources(), R.drawable.a1);
		timeBitmaps[2]=BitmapFactory.decodeResource(getResources(), R.drawable.a2);
		timeBitmaps[3]=BitmapFactory.decodeResource(getResources(), R.drawable.a3);
		timeBitmaps[4]=BitmapFactory.decodeResource(getResources(), R.drawable.a4);
		timeBitmaps[5]=BitmapFactory.decodeResource(getResources(), R.drawable.a5);
		timeBitmaps[6]=BitmapFactory.decodeResource(getResources(), R.drawable.a6);
		timeBitmaps[7]=BitmapFactory.decodeResource(getResources(), R.drawable.a7);
		timeBitmaps[8]=BitmapFactory.decodeResource(getResources(), R.drawable.a8);
		timeBitmaps[9]=BitmapFactory.decodeResource(getResources(), R.drawable.a9);


	}

	private void intialOndraw(Canvas canvas) {
		data = logic.getData();
		int width = logic.getWith();
		int heigth = logic.getHeigth();

		int left = 0;
		int top = Util.UP_TO_SCREEN;

		for (int i = 1; i < heigth - 1; i++) {
			left = Util.LEFT_TO_SCREEM;
			for (int j = 1; j < width - 1; j++) {
				canvas.drawBitmap(list.get(data[i][j]), left, top, new Paint());
				left += Util.IMAGE_LENGTH;
			}
			top += Util.IMAGE_LENGTH;
		}
	}

	
	
	private void DrawTime(Canvas canvas)
	{
		if(timess<=0)
		{
			gameCount.setuseTime(Util.GAME_TIME_NUM);
			gameCount.setSuccuse(false);
			Message message=activity.myHandler.obtainMessage(0, 1, 1, gameCount);
			activity.myHandler.sendMessage(message);
		}
		int tenNum=timess/10;
		int Num=timess%10;
		canvas.drawBitmap(timeBitmaps[tenNum], Util.WIDTH_DEVICE/2-30, 20, new Paint());
		canvas.drawBitmap(timeBitmaps[Num], Util.WIDTH_DEVICE/2, 20, new Paint());
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		magicCloud.doDraw(canvas);
		canvas.drawBitmap(list.get(11), 0, 0, new Paint());
		intialOndraw(canvas);
		DrawCurrentPoint(canvas);
		DrawButton(canvas);
		DrawTime(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			double x = event.getX();
			double y = event.getY();

			if (x < 10 + Util.IMAGE_LENGTH && x > 10 && y > 10
					&& y < 10 + Util.IMAGE_LENGTH) // quit the game
			{
				activity.finish();
			}
			if (x < Util.WIDTH_DEVICE - 10
					&& x > Util.WIDTH_DEVICE - Util.IMAGE_LENGTH - 10 && y > 10
					&& y < 10 + Util.IMAGE_LENGTH) {
				if (isstarted) // 正在运行的，点击下去为暂停
				{
					Lock_the_game = true;
					timeThread.flag=false;
					
				} else { // 现在停止的，点击为可以玩游戏了
					
					timeThread=new TimeThread(this);
					timeThread.start();
					Lock_the_game = false;
					
				}
				isstarted = !isstarted;
			}
			if (!Lock_the_game) {           //当触发开始按钮才可以玩游戏
				if (!lock_the_fisrtPoint) // lock 是获取的第一个点击位置的
				{
					p_firsttouch = Util.GetPointByXY(x, y);
					currentpoint = Util.getCoordinateByIndex(p_firsttouch);
					lock_the_fisrtPoint = true;
				} else {
					p_secondtouch = Util.GetPointByXY(x, y);
					
					//可以删除的
					if (logic.isDeleteable(p_firsttouch, p_secondtouch)) {
						logic.Delete(p_firsttouch, p_secondtouch);

						//判断是不是已经可以结束游戏
						if(logic.isFinish())
						{
							gameCount.setuseTime(Util.GAME_TIME_NUM-timess);
							gameCount.setSuccuse(true);
							activity.myHandler.removeMessages(0);
							Message message=activity.myHandler.obtainMessage(1, 1, 1, gameCount);
							activity.myHandler.sendMessage(message);
						}
						//判断游戏是否无解
						if (logic.isRefresh())
							logic.Refresh();
						
						currentpoint = null;
						lock_the_fisrtPoint = false;
					} 
					else {  //不可以删除的
						currentpoint = Util.getCoordinateByIndex(p_secondtouch);
						p_firsttouch = p_secondtouch;
						gameCount.addWrongCount();
						lock_the_fisrtPoint = true;
					}
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private void DrawCurrentPoint(Canvas canvas) {
		if (currentpoint != null && currentpoint.x != 0 && currentpoint.y != 0) {
			Rect rect = new Rect();
			rect.set((int) (currentpoint.x - Util.IMAGE_LENGTH * 0.1),
					(int) (currentpoint.y - Util.IMAGE_LENGTH * 0.1),
					(int) (currentpoint.x + Util.IMAGE_LENGTH * 1.1),
					(int) (currentpoint.y + Util.IMAGE_LENGTH * 1.1));

			canvas.drawBitmap(list.get(data[p_firsttouch.y][p_firsttouch.x]),
					null, rect, new Paint());
		}
	}

	private void DrawButton(Canvas canvas) {
		Bitmap quitBitmap = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.quit),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true);
		Bitmap startBitmap = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.start),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true);
		Bitmap pauseBitmap = Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.pause),
				Util.IMAGE_LENGTH, Util.IMAGE_LENGTH, true);

		canvas.drawBitmap(quitBitmap, 10, 10, new Paint());
		if (!isstarted) {
			canvas.drawBitmap(startBitmap, Util.WIDTH_DEVICE - 10
					- Util.IMAGE_LENGTH, 10, new Paint());
		} else {
			canvas.drawBitmap(pauseBitmap, Util.WIDTH_DEVICE - 10
					- Util.IMAGE_LENGTH, 10, new Paint());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		caDrawThread = new GameViewDrawThread(this, getHolder());
		caDrawThread.setFlag(true);
		caDrawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		caDrawThread.setFlag(false);
		while (retry) {
			try {
				caDrawThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			caDrawThread = null;

		}

	}

}
