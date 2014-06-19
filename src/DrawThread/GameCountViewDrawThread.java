package DrawThread;

import gameview.GameCountView;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * ��Ϸͳ�ƽ���Ļ����߳�
 * @author Administrator
 *
 */
public class GameCountViewDrawThread extends Thread {
	
	private GameCountView gameCountView;
	
	private SurfaceHolder surfaceHolder = null;
	
	private boolean flag;

	private long sleepSpan = 10;

	public GameCountViewDrawThread(GameCountView gameCountView, SurfaceHolder surfaceHolder) {
		this.gameCountView = gameCountView;
		this.surfaceHolder = surfaceHolder;
	}
	
	@SuppressLint("WrongCall")
	public void run() {
		Canvas c;//����		
		while (flag) {
			c = null;
			c = surfaceHolder.lockCanvas(null);
			synchronized (this.surfaceHolder) {
				try{
					gameCountView.onDraw(c);
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	} finally {
		    		if (c != null) {
		    			surfaceHolder.unlockCanvasAndPost(c);
		    		}
		    	}
				try {
					Thread.sleep(sleepSpan );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
