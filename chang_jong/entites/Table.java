package chang_jong.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import chang_jong.utils.Handler;

public class Table {

	private Handler handler;
	
	private boolean cursor_entered = false;
	private boolean cursor_pressed = false;
	private boolean cursor_dragged = false;
	private boolean cursor_clicked = false;
	
	private int x, y;
	private int cursor_x, cursor_y;
	private static final int width = 120, height = 60;
	private BufferedImage table_img, out_table_img;
	/* ���� �������� ��Ÿ���� ���� ������
	 * ��������� x,y �������� �ٸ��� �ϰ� x�� ���Ѽ��� 40���� �Ͽ���.
	 * �̹����� Ŀ���� ���� ������ ũ�⵵ Ű��� �ɰ� ����.
	*/
	private final int xOffset = 40, yOffset = 25;
	private int temp;
	//warning, ���װ� �� �� ���� :: �ٸ� å����� ������ �� �ִ�.
	private final int edgeSize = 40;
	private ArrayList<String> menuArray,  numArray, payArray;
	
	public Table(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		
		
		menuArray = new ArrayList<String>();
		numArray = new ArrayList<String>();
		payArray = new ArrayList<String>();
		try {
			table_img = ImageIO.read(new File("res/table.png"));
			out_table_img = ImageIO.read(new File("res/star.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void tick() {		
		if (cursor_entered && cursor_pressed) {
			//Ŀ�� �߽����� ���̺��� �̵���Ų��.
			//����::���̺��� ������ �� �ִ�.
			x = cursor_x-width/2;
			y = cursor_y-height/2;
			
			temp = x%xOffset;
			x -= temp;
			temp = y%yOffset;
			y -= temp;
			
		}

		//table boundary
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		int tempAxis;
		tempAxis = handler.getDisplay().getCanvas_Width()-width;
		if (x>tempAxis)
			x = tempAxis;
		tempAxis = handler.getDisplay().getCanvas_Height()-height;
		if (y>tempAxis)
			y = tempAxis;
	}
	
	//���̺� �׸��� �׸�
	public void render(Graphics g) {
		
		if (cursor_entered)
			g.drawImage(out_table_img, x, y, width, height, null);
		else	
			g.drawImage(table_img, x, y, width, height, null);
	}
	
	//Ŀ���� ���̺� �ȿ� �� �ִ��� üũ
		public boolean check_table(int cursor_x, int cursor_y, boolean cursor_pressed, boolean cursor_dragged) {
			
			this.cursor_pressed = cursor_pressed;
			this.cursor_dragged = cursor_dragged;
			this.cursor_x = cursor_x;
			this.cursor_y = cursor_y;
			
			if (cursor_x >= x - edgeSize && cursor_x <= x+width + edgeSize
					&& cursor_y >= y - edgeSize && cursor_y <= y+height + edgeSize){
				cursor_entered = true;
				
				return true;
			} else {
				cursor_entered = false;
				
				return false;
			}
		}
		//���̺� �ȿ��� Ŭ���ҽ�
		public boolean click_table(int cursor_x, int cursor_y, boolean cursor_clicked) {
			
			this.cursor_x = cursor_x;
			this.cursor_y = cursor_y;
			this.cursor_clicked = cursor_clicked;
			
			if ( cursor_clicked == false) 
				return false;
			
			if (cursor_x >= x - edgeSize && cursor_x <= x+width + edgeSize
					&& cursor_y >= y - edgeSize && cursor_y <= y+height + edgeSize){
				cursor_entered = true;
				
				return true;
			} else {
				cursor_entered = false;
				
				return false;
			}
		}
		
		// ���̺� �ֹ� ��� ����
		public void addMenu(String menuname, String menunum, String menupay) {
			this.menuArray.add(menuname);
			this.numArray.add(menunum);
			this.payArray.add(menupay);
			
		}
		
		// ���̺� �ֹ� ���� ����
		public void setMenu(int index, String menunum, String menupay) {
			this.numArray.set(index, menunum);
			this.payArray.set(index, menupay);
		}
		
		// �޴� ��ȯ
		public ArrayList<String> getMenu() {
			return menuArray;
		}
		
		// ���� ��ȯ
		public ArrayList<String> getNum() {
			return numArray;
		}
		
		
		// ���� ��ȯ
		public ArrayList<String> getPay() {
			return payArray;
		}
		
}
