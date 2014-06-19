package gameview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Util;

import android.graphics.Point;

public class Logic {
	private int width_;
	private int heigth_;
	private int[][] data;
	private final int NUM_OF_PIC = Util.Number_Of_Picture;
	private int count_of_game;

	public int getWith() {
		return width_;
	}

	public int getHeigth() {
		return heigth_;
	}

	public int[][] getData() {
		return data;
	}

	/*
	 * constructor with two parameter
	 */
	public Logic(int width, int heigth) {
		width_ = width + 2;
		heigth_ = heigth + 2;
		count_of_game = width * heigth;

		data = new int[heigth_][];
		for (int i = 0; i < heigth_; i++)
			data[i] = new int[width_];

		int[] a = new int[width * heigth / 2];
		Random random = new Random();
		for (int i = 0; i < a.length; i++)
			a[i] = random.nextInt(NUM_OF_PIC) + 1;

		int[] b = a.clone();
		shuffleArray(b);
		for (int i = 0; i < a.length; i++)
			data[i / width + 1][i % width + 1] = a[i];
		for (int i = a.length; i < a.length * 2; i++)
			data[i / width + 1][i % width + 1] = b[i - a.length];

	}

	/*
	 * no parameter for just for test
	 */
	public Logic() {
		width_ = 8;
		heigth_ = 7;
		data = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 6, 2, 7, 4, 7, 6, 0 }, { 0, 3, 6, 0, 5, 5, 5, 0 },
				{ 0, 4, 3, 0, 6, 2, 7, 0 }, { 0, 4, 7, 6, 3, 6, 1, 0 },
				{ 0, 5, 5, 5, 4, 3, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
	}

	/*
	 * shuffleArray 把一个数组随机排序 parameter 数组 return void
	 */
	private void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	/*
	 * 判断水平方向是不是有通路，可以删除 parameter 纵坐标Y应该一样的，横坐标x1,x2 return boolean
	 */
	private boolean isDeletableHorizon(int x1, int x2, int y) {
		if (x1 > x2) {
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
		}
		while (x1 < x2) {
			if ((++x1) == x2)
				return true;
			else if (data[y][x1] != 0)
				return false;
		}
		return false;
	}

	private boolean isDeletableVertical(int y1, int y2, int x) {
		if (y1 > y2) {
			int tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
		while (y1 < y2) {
			if ((++y1) == y2)
				return true;
			else if (data[y1][x] != 0)
				return false;
		}
		return false;
	}

	private boolean isDeletableByTwoStep(int x1, int y1, int x2, int y2) {
		if (data[y1][x2] == 0 && isDeletableHorizon(x1, x2, y1)
				&& isDeletableVertical(y1, y2, x2))
			return true;
		if (data[y2][x1] == 0 && isDeletableHorizon(x1, x2, y2)
				&& isDeletableVertical(y1, y2, x1))
			return true;
		return false;
	}

	private boolean isDeletableByThreeStep(int x1, int y1, int x2, int y2) {
		int x1_tmp = x1;
		while (x1_tmp < width_ - 1) { // 向左边走
			if (data[y1][++x1_tmp] == 0) {
				if (isDeletableByTwoStep(x1_tmp, y1, x2, y2))
					return true;
			} else
				break;
		}

		x1_tmp = x1;
		while (x1_tmp > 0) {
			if (data[y1][--x1_tmp] == 0) { // 向右边走
				if (isDeletableByTwoStep(x1_tmp, y1, x2, y2))
					return true;
			} else
				break;
		}

		int y1_tmp = y1;
		while (y1_tmp < heigth_ - 1) { // 向下面走
			if (data[++y1_tmp][x1] == 0) {
				if (isDeletableByTwoStep(x1, y1_tmp, x2, y2))
					return true;
			} else
				break;
		}

		y1_tmp = y1;
		while (y1_tmp > 0) {
			if (data[--y1_tmp][x1] == 0)
				if (isDeletableByTwoStep(x1, y1_tmp, x2, y2))
					return true;
				else
					break;
		}

		return false;

	}

	public boolean isDeleteable(Point p1, Point p2) {
		if (p1 == p2)
			return false;
		int posX = p1.x;
		int posY = p1.y;
		int desX = p2.x;
		int desY = p2.y;
		if (data[posY][posX] == data[desY][desX]) {
			if (posY == desY) {
				if (isDeletableHorizon(posX, desX, posY)) {
					// System.out.println("delete use one step"); // just for
					// test
					return true;
				} else {
					if (isDeletableByThreeStep(posX, posY, desX, desY)) {
						// System.out.println("delete use three step");
						return true;
					} else
						return false;
				}
			}
			if (posX == desX) {
				if (isDeletableVertical(posY, desY, posX)) {
					return true;
				} else {
					if (isDeletableByThreeStep(posX, posY, desX, desY)) {
						return true;
					} else
						return false;
				}
			}

			if (isDeletableByTwoStep(posX, posY, desX, desY)) {
				System.out.println("delete use two step");
				return true;
			} else if (isDeletableByThreeStep(posX, posY, desX, desY)) {
				System.out.println("delete use three step");
				return true;
			} else
				return false;
		} else
			return false;

	}

	public boolean isRefresh() {
		List<Point> list = new ArrayList<Point>();
		for (int i = 0; i < heigth_; i++) {
			for (int j = 0; j < width_; j++) {
				if (data[i][j] != 0)
					list.add(new Point(j, i));
			}
		}
		Point p1;
		Point p2;
		for (int i = 0; i < list.size(); i++) {
			p1 = list.get(i);
			// System.out.println(p1);
			for (int j = i + 1; j < list.size(); j++) {
				p2 = list.get(j);
				// System.out.println(p2);
				if (isDeleteable(p1, p2)) {
					// System.out.println("delete"+i);
					return false;
				}
			}
		}
		return true;
	}

	public void Refresh() {
		if (isRefresh()) {
			List<Point> list = new ArrayList<Point>();

			for (int i = 0; i < heigth_; i++) {
				for (int j = 0; j < width_; j++) {
					if (data[i][j] != 0)
						list.add(new Point(j, i));
				}
			}
			for (int i = 0; i < list.size(); i++) {
				Point p1 = list.get(i);
				for (int j = i + 1; j < list.size(); j++) {
					Point p2 = list.get(j);
					int tmp = data[p1.y][p1.x];
					data[p1.y][p1.x] = data[p2.y][p2.x];
					data[p2.y][p2.x] = tmp;
					if (isRefresh()) {
						data[p2.y][p2.x] = data[p1.y][p1.x];
						data[p1.y][p1.x] = tmp;
						continue;
					} else {
						return;
					}
				}
			}
		}
	}

	public void Delete(Point p1, Point p2) {
		if (isDeleteable(p1, p2)) {
			data[p1.y][p1.x] = 0;
			data[p2.y][p2.x] = 0;
			count_of_game -= 2;
		}
	}

	public boolean isFinish() {
		if (count_of_game != 0)
			return false;
		else
			return true;
	}

	public void Dispaly_for_test() {
		for (int i = 0; i < heigth_; i++) {
			for (int j = 0; j < width_; j++)
				System.out.print(data[i][j] + ", ");
			System.out.println();
		}

	}

	public static void main(String[] args) {
		Logic logic = new Logic(2, 2);
		logic.Dispaly_for_test();
		// Point p1=new Point(1,1);
		// Point p2=new Point(2,2);
		if (logic.isRefresh()) {
			logic.Refresh();
			logic.Dispaly_for_test();
		}
	}

}
