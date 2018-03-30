package chang_jong;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.JFrame;

public class Display extends JFrame{

	private String title;
	private Order order;
	private MenuPanel menupanel;
	private ButtonPanel buttonpanel;
	private Canvas canvas;
	
	public Display(String title) {
		this.title = title;
		
		init_frame();
	}
	
	private void init_frame() {
		this.setTitle(this.title);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		this.canvas = new Canvas();
		canvas.setBounds(0, 0, res.width/2, (res.height/10)*9);
		this.canvas.setPreferredSize(new Dimension(res.width/2, (res.height/10)*9));
		this.canvas.setMaximumSize(new Dimension(res.width/2, (res.height/10)*9));
		this.canvas.setMinimumSize(new Dimension(res.width/2, (res.height/10)*9));
		//this.canvas.setBackground(new Color(255, 100, 255));
		order = new Order();
		order.setBounds(res.width/2, 0, res.width/4, (res.height/10)*9);
		menupanel= new MenuPanel(order);
		menupanel.setBounds(res.width/2+ res.width/4, 0, res.width/4, (res.height/10)*9);
		buttonpanel = new ButtonPanel();
		buttonpanel.setBounds(0, (res.height/10)*9, res.width, (res.height/10));
		getContentPane().setLayout(null);
	
		getContentPane().add(this.canvas);
		getContentPane().add(order);
		getContentPane().add(menupanel);
		getContentPane().add(buttonpanel);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);
	}
	

	public Canvas getCanvas() {
		if (canvas == null)
			System.out.println("canvas is null");
		return canvas;
	}

	
}
