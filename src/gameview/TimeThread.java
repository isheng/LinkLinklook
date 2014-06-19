package gameview;


public class TimeThread extends Thread{
	private Gameview gameView;//声明GameView的引用
	public boolean flag=true;//循环标志位
	public TimeThread(Gameview gameView){//构造器
		this.gameView=gameView;//得到GameView的引用
	}
	public void run(){//重写的run方法
		while(flag){
			try{
				Thread.sleep(1000);//睡眠一秒种
			}catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常信息
			}
			gameView.timess--;
		}
	}
}


//class Task implements Runnable {
//	
//	boolean flag=true;//循环标志位
//	Gameview gameView;//声明GameView的引用
//
//    @Override
//    public void run() {
//        while(flag) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            gameView.timess--;
//        }
//    }
//}