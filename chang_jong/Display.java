package chang_jong;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Display extends JFrame{

	private String title;
	protected Order order;
	protected MenuPanel menupanel;
	//buttonpanel은 display에서 추가하되, button은 framwork에서 추가하면 좋겠음.
	private ButtonPanel buttonpanel;
	private Canvas canvas;
	private int canvas_width, canvas_height;
	
	private BufferedImage table_map;
	
	public Display(String title) {
		this.title = title;
		
		init_frame();
	}
	
	private void init_frame() {
		this.setTitle(this.title);
		this.setSize(673, 415);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		
		//canvas
		try {
			table_map = ImageIO.read(new File("res/table_map.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		this.canvas = new Canvas();
		canvas_width = res.width/2;
		canvas_height = (res.height/10)*9;
		System.out.println(canvas_width+","+canvas_height);
		canvas.setBounds(0, 0, canvas_width, canvas_height);	//03.27 : 대원이가 수정함, 변수로 대체
		this.canvas.setPreferredSize(new Dimension(res.width/2, (res.height/10)*9));
		this.canvas.setMaximumSize(new Dimension(res.width/2, (res.height/10)*9));
		this.canvas.setMinimumSize(new Dimension(res.width/2, (res.height/10)*9));
		//this.canvas.setBackground(new Color(255, 100, 255));
		
		//order
		order = new Order();
		order.setBounds(res.width/2, 0, res.width/4, (res.height/10)*9);
		
		menupanel= new MenuPanel(order);
		menupanel.setList(false);
		menupanel.setBounds(res.width/2+ res.width/4, 0, res.width/4, (res.height/10)*9);
		buttonpanel = new ButtonPanel();
		buttonpanel.setBounds(0, (res.height/10)*9, res.width, (res.height/10));
		//buttonpanel.getTableButton().addActionListener(this);
		getContentPane().setLayout(null);
	
		getContentPane().add(this.canvas);
		getContentPane().add(order);
		getContentPane().add(menupanel);
		getContentPane().add(buttonpanel);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	public void render_background(Graphics g) {
		g.drawImage(table_map, 0, 0, null);
	}

	public Canvas getCanvas() {
		if (canvas == null)
			System.out.println("canvas is null");
		return canvas;
	}
	
	public ButtonPanel getButtonPanel() {
		if (this.buttonpanel == null)
			System.out.println("buttonPanel is null");
		return this.buttonpanel;
	}
	
	public int getCanvas_Width() {
		return this.canvas_width;
	}
	
	public int getCanvas_Height() {
		return this.canvas_height;
	}
}
