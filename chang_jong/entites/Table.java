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
	/* 격자 움직임을 나타내기 위한 오프셋
	 * 현재까지는 x,y 오프셋을 다르게 하고 x의 제한선을 40으로 하였다.
	 * 이미지가 커짐에 따라 오프셋 크기도 키우면 될거 같다.
	*/
	private final int xOffset = 40, yOffset = 25;
	private int temp;
	//warning, 버그가 될 수 있음 :: 다른 책상까지 데려올 수 있다.
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
			//커서 중심으로 테이블을 이동시킨다.
			//버그::테이블이 합쳐질 수 있다.
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
	
	//테이블 그림을 그림
	public void render(Graphics g) {
		
		if (cursor_entered)
			g.drawImage(out_table_img, x, y, width, height, null);
		else	
			g.drawImage(table_img, x, y, width, height, null);
	}
	
	//커서가 테이블 안에 들어가 있는지 체크
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
		//테이블 안에서 클릭할시
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
		
		// 테이블에 주문 기록 저장
		public void addMenu(String menuname, String menunum, String menupay) {
			this.menuArray.add(menuname);
			this.numArray.add(menunum);
			this.payArray.add(menupay);
			
		}
		
		// 테이블에 주문 내역 수정
		public void setMenu(int index, String menunum, String menupay) {
			this.numArray.set(index, menunum);
			this.payArray.set(index, menupay);
		}
		
		// 메뉴 반환
		public ArrayList<String> getMenu() {
			return menuArray;
		}
		
		// 수랑 뱐환
		public ArrayList<String> getNum() {
			return numArray;
		}
		
		
		// 가격 반환
		public ArrayList<String> getPay() {
			return payArray;
		}
		
}
