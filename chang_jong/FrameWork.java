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
	//핸들러에서 전체 프로그램 변수 getter, setter를 하도록 하면 편리함. 대신 생성자에서 모두 핸들러를 받아야 함.
	private Handler handler;
	
	//test 삭제 예정
	private Star star;
	
	private int cursor_x, cursor_y;
	
	public FrameWork() {
		
	}
	
	public void init() {		
		//system
		mouseManager = new MouseManager();
		display = new Display("창종을 하자");
		handler = new Handler(this);
		
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		//entities, 테이블 변수들 정리해야 함. ---2018.03.19
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
		 * 스레드는 2개 이상 생성되지 않는다. 그러므로 running 변수를 통해 확인한다.
		 * 프로그램은 start 함수를 통해 시작되므로, 한 번 생성된 스레드를 다시 만들지 않는다.
		 * 
		 * 아래의 과정은 게임에서 시간에 따라 업데이트 하도록 하는 과정이다.
		 * 컴퓨터 사양에 따라서 업데이트 간격을 둔다면, 컴퓨터마다 제각각이기 때문에 이런 과정을 둔다.
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

	//업데이트
	public void tick() {
		this.cursor_x = mouseManager.x;
		this.cursor_y = mouseManager.y;

		
		for ( int i = 0 ; i < table.length ; i++ ) {
			// 테이블 클릭 시 처리
			if (table[i].click_table(cursor_x, cursor_y, mouseManager.cursor_clicked)) {
				display.menupanel.setList(true); // 메뉴 리스트 setEnable(true)
			
				// 테이블 설정
				display.menupanel.setTable(table[i]); 
				display.order.setTable(table[i]);
				display.order.listClear(new DefaultListModel()); // 주문 리스트 전체 지우기
				display.order.loadList(); // 테이블에 저장된 메뉴, 수량, 가격을 주문패널에 저장
				
				mouseManager.cursor_clicked = false;
				
				break;
			}
			
		}
		
		
		
		//테이블 관리 중일 때만 업데이트된다.
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
	
	//그리기
	public void render() {

		bs = display.getCanvas().getBufferStrategy();
		
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);//삼중버퍼를 이용한다.
			return ;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, display.getWidth(), display.getHeight());//전체 화면을 지운다.
		display.render_background(g);
		
		table[0].render(g);
		table[1].render(g);
		table[2].render(g);
		star.render(g);
		
		bs.show();
		g.dispose();
	}
	
	//스레드를 한개 이상 실행하지 않기 위해 이용한다.
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
