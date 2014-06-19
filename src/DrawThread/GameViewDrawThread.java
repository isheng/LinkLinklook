package DrawThread;


import gameview.Gameview;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread {

	private Gameview gameView;
	
	private SurfaceHolder surfaceHolder;
	
	private boolean flag;

	private long sleepSpan = 100;
	
	public GameViewDrawThread(Gameview gameView, SurfaceHolder surfaceHolder) {
		this.gameView = gameView;
		this.surfaceHolder = surfaceHolder;
	}

	public void run() {
		Canvas c;//»­²¼
		while (this.flag) {
			c = null;
			try{
				c=this.surfaceHolder.lockCanvas();
				synchronized (this.surfaceHolder) {
					gameView.onDraw(c);
				}
			}finally{
				if(c!=null)
				{
					this.surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			try {
				Thread.sleep(sleepSpan);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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
