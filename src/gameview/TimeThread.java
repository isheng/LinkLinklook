package gameview;


public class TimeThread extends Thread{
	private Gameview gameView;//����GameView������
	public boolean flag=true;//ѭ����־λ
	public TimeThread(Gameview gameView){//������
		this.gameView=gameView;//�õ�GameView������
	}
	public void run(){//��д��run����
		while(flag){
			try{
				Thread.sleep(1000);//˯��һ����
			}catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
			gameView.timess--;
		}
	}
}


//class Task implements Runnable {
//	
//	boolean flag=true;//ѭ����־λ
//	Gameview gameView;//����GameView������
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