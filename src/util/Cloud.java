package util;

import java.util.Random;

public class Cloud {

	private int x;			//ͼƬ���Ͻǵ�x����
	
	private int y;			//ͼƬ���Ϸ���y����
	
	private int bitmap;		//��ʹ�õ�ͼƬ
	
	private int direction;	//����1��ʾ�������ң�0��ʾ��������Ĭ��Ϊ��������
	
	private int speed;		//�ƶ����ٶ�
	
	public Cloud() {
		Random random = new Random();
		speed = random.nextInt(2) + 1;
		direction = 1;
		bitmap = random.nextInt(3);
		x = -100;
		y = random.nextInt((int)( Util.HEIGTH_DEVICE* 0.3));
	}
	
	/**
	 * ��⵱ǰ���Ƿ񻹿ɼ�
	 * @return
	 */
	public boolean isVisible() {
		if (x < -100 || x >Util.WIDTH_DEVICE) {
			return false;
		}
		return true;
	}
	
	/**
	 * �ƽ����ƶ�
	 */
	public void move() {
		//�����ƶ�
		if (direction == 1) {
			this.x += speed;
		} else {
			this.x -= speed;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getBitmap() {
		return bitmap;
	}

	public void setBitmap(int bitmap) {
		this.bitmap = bitmap;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
