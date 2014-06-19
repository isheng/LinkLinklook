package util;

import android.graphics.Point;

public class Util {
	public static int WIDTH_DEVICE=0;
	public static int HEIGTH_DEVICE=0;
	
	public static int ROWSIZE = 6;				//����Ϊ5��
	public static int COLUMNSIZE = 7;			//����Ϊ6��
	public static int IMAGE_LENGTH = 0;		//ʵ����ʾ��ͼƬ��Ⱥ͸߶ȣ���Ҫ������Ļ��������
	public static int LEFT_TO_SCREEM = 0;	//��ߵ�һ��ͼƬ����Ļ��ߵľ���
	public static int UP_TO_SCREEN = 0;	//�����һ��ͼƬ����Ļ�ϱߵľ���
	public static int MAXSPACETIME = 2000;  //��������֮������ʱ����
	
	public static int GAME_LEVEL=1;
	public static int GAME_TIME_NUM=60;
	
	public static int Number_Of_Picture=10;
	
	public static Point GetPointByXY(double x,double y)
	{
		Point point;
		if(x<LEFT_TO_SCREEM || x>LEFT_TO_SCREEM+COLUMNSIZE*IMAGE_LENGTH 
				||y <UP_TO_SCREEN || y>UP_TO_SCREEN+ROWSIZE*IMAGE_LENGTH)
		{
			point=new Point(0,0);
			return point;
		}
		
		else {
			int indexx= (int)(x-LEFT_TO_SCREEM)/IMAGE_LENGTH+1;
			int indexy= (int)(y-UP_TO_SCREEN)/IMAGE_LENGTH+1;
			point=new Point(indexx,indexy);
			return point;
		}		
	}
	
	/**
	 * ����map���������õ���ͼƬ����Ļ�����Ϸ�������ֵ
	 * @param indexPoint
	 * @return
	 */
	public static Point getCoordinateByIndex(Point indexPoint) {
		if (indexPoint == null) {
			return null;
		}
		if (indexPoint.x >= 0 && indexPoint.y >= 0 && indexPoint.x <= COLUMNSIZE + 1 && indexPoint.y <= ROWSIZE + 1) {
			Point point = new Point();
			point.x = LEFT_TO_SCREEM + (indexPoint.x - 1) *  IMAGE_LENGTH;
			point.y = UP_TO_SCREEN + (indexPoint.y - 1) *  IMAGE_LENGTH;
			//System.out.printf("������ת���������x=%d��y=%d\n", point.x, point.y);
			return point;
		}
		return null;
	}
	
	
}
