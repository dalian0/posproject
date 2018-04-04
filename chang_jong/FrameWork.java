package chang_jong;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.DefaultListModel;

import chang_jong.entites.Star;
import chang_jong.entites.Table;
import chang_jong.input_manager.MouseManager;
import chang_jong.utils.Handler;

public class FrameWork implements Runnable{

	private boolean running = false;
	
	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;
	private Table[] table;
	private Display display;
	private MouseManager mouseManager;
	//�ڵ鷯���� ��ü ���α׷� ���� getter, setter�� �ϵ��� �ϸ� ����. ��� �����ڿ��� ��� �ڵ鷯�� �޾ƾ� ��.
	private Handler handler;
	
	//test ���� ����
	private Star star;
	
	private int cursor_x, cursor_y;
	
	public FrameWork() {
		
	}
	
	public void init() {		
		//system
		mouseManager = new MouseManager();
		display = new Display("â���� ����");
		handler = new Handler(this);
		
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		//entities, ���̺� ������ �����ؾ� ��. ---2018.03.19
		table = new Table[3];
		
		table[0] = new Table(1, 1, handler);
		table[1] = new Table(180, 1, handler);
		table[2] = new Table(1, 120, handler);
		
		star = new Star();
	}
	
	@Override
	public void run() {
	
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		//int ticks = 0;
		
		/*
		 * ������� 2�� �̻� �������� �ʴ´�. �׷��Ƿ� running ������ ���� Ȯ���Ѵ�.
		 * ���α׷��� start �Լ��� ���� ���۵ǹǷ�, �� �� ������ �����带 �ٽ� ������ �ʴ´�.
		 * 
		 * �Ʒ��� ������ ���ӿ��� �ð��� ���� ������Ʈ �ϵ��� �ϴ� �����̴�.
		 * ��ǻ�� ��翡 ���� ������Ʈ ������ �дٸ�, ��ǻ�͸��� �������̱� ������ �̷� ������ �д�.
		 * */
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				//ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				//System.out.println("Ticks and Frames: " + ticks);
				//ticks = 0;
				timer = 0;
			}
		}
		
		this.stop();
	}

	//������Ʈ
	public void tick() {
		this.cursor_x = mouseManager.x;
		this.cursor_y = mouseManager.y;

		
		for ( int i = 0 ; i < table.length ; i++ ) {
			// ���̺� Ŭ�� �� ó��
			if (table[i].click_table(cursor_x, cursor_y, mouseManager.cursor_clicked)) {
				display.menupanel.setList(true); // �޴� ����Ʈ setEnable(true)
			
				// ���̺� ����
				display.menupanel.setTable(table[i]); 
				display.order.setTable(table[i]);
				display.order.listClear(new DefaultListModel()); // �ֹ� ����Ʈ ��ü �����
				display.order.loadList(); // ���̺� ����� �޴�, ����, ������ �ֹ��гο� ����
				
				mouseManager.cursor_clicked = false;
				
				break;
			}
			
		}
		
		
		
		//���̺� ���� ���� ���� ������Ʈ�ȴ�.
		if (display.getButtonPanel().getTableButton_Clicked()) {
			table[0].tick();
			table[0].check_table(cursor_x, cursor_y, mouseManager.cursor_pressed, mouseManager.cursor_dragged);
			table[1].tick();
			table[1].check_table(cursor_x, cursor_y, mouseManager.cursor_pressed, mouseManager.cursor_dragged);
			table[2].tick();
			table[2].check_table(cursor_x, cursor_y, mouseManager.cursor_pressed, mouseManager.cursor_dragged);
					
		}
		star.getCursorInform(mouseManager.cursor_clicked, cursor_x, cursor_y);
	}
	
	//�׸���
	public void render() {

		bs = display.getCanvas().getBufferStrategy();
		
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);//���߹��۸� �̿��Ѵ�.
			return ;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, display.getWidth(), display.getHeight());//��ü ȭ���� �����.
		display.render_background(g);
		
		table[0].render(g);
		table[1].render(g);
		table[2].render(g);
		star.render(g);
		
		bs.show();
		g.dispose();
	}
	
	//�����带 �Ѱ� �̻� �������� �ʱ� ���� �̿��Ѵ�.
	public synchronized void start() {
		if (running)
			return ;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return ;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Display getDisplay() {
		if (this.display == null)
			System.out.println("error::FrameWork's display is null");
		return this.display;
	}
	
}
