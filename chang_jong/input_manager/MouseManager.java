package chang_jong.input_manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * 1. ���̺��� �巡���ؼ� �̵����Ѽ� ��ġ ����
 * 2. ���̺��� �ѹ� Ŭ���ϰ�, ���ϴ� ��ġ���� �ٽ� Ŭ���ؼ� ��ġ ����
 * 
 * 
 * ���̺� �ȳ� â�� ��� ������� �� ������
 * 		1. �ȳ�â�� x��ư
 * 		2. �ȳ�â �ٱ��� ������ ������.
 * 	�ȳ�â�� �ڲ� ����ٴϴ� ���� ���־���.
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
		
		//Ŀ�� ���¸� ��� false�� �ʱ�ȭ
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
