import javax.swing.JFrame;

public class TestFrame extends JFrame {
	
	MenuPanel menupanel;
	TestFrame() {
		setTitle("test");
		setSize(300,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menupanel = new MenuPanel();
		add(menupanel);
		
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new TestFrame();
	}
}