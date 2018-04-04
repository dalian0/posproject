package chang_jong.input_manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * 1. 테이블을 드래그해서 이동시켜서 위치 조정
 * 2. 테이블을 한번 클릭하고, 원하는 위치에서 다시 클릭해서 위치 조정
 * 
 * 
 * 테이블 안내 창이 어떻게 사라지게 할 것인지
 * 		1. 안내창에 x버튼
 * 		2. 안내창 바깥을 누르면 없어짐.
 * 	안내창이 자꾸 따라다니는 증상 없애야함.
 *
 * */

public class MouseManager extends MouseAdapter implements MouseListener{
	
	public int x, y;
	public boolean cursor_pressed;
	public boolean cursor_dragged;
	public boolean cursor_clicked;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		cursor_clicked = true;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		cursor_pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		
		//커서 상태를 모두 false로 초기화
		cursor_pressed = false;
		cursor_dragged = false;
		cursor_clicked = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		
		cursor_dragged = true;
	}
}
