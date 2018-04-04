package chang_jong.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Star {

	private static final int width = 120, height = 120;
	private int x, y;
	private BufferedImage star_img;
	private boolean cursor_clicked = false;
	
	public Star() {
		try {
			star_img = ImageIO.read(new File("res/star.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void render(Graphics g) {
		if (cursor_clicked)
			g.drawImage(star_img, 0, 0, width, height, null);
	}
	
	public void getCursorInform(boolean cursor_clicked, int cursor_x, int cursor_y) {
		this.cursor_clicked = cursor_clicked;
		
		if (cursor_clicked) {
			this.x = cursor_x;
			this.y = cursor_y;
			//System.out.println("clicked" + cursor_clicked);
		}
	}
}
