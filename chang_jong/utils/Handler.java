package chang_jong.utils;

import chang_jong.ButtonPanel;
import chang_jong.Display;
import chang_jong.FrameWork;

public class Handler {
	
	private FrameWork framework;
	private Display display;
	private ButtonPanel buttonpanel;
	
	public Handler(FrameWork framework) {
		this.framework = framework;
		this.display = framework.getDisplay();
		//this.buttonpanel = this.display.getButtonPanel();
	}

	public FrameWork getFramework() {
		if (framework == null)
			System.out.println("handler send warning::framework is null");
		return framework;
	}

	public Display getDisplay() {
		if (display == null)
			System.out.println("handler send warning::display is null");
		return display;
	}

	public ButtonPanel getButtonpanel() {
		if (buttonpanel == null)
			System.out.println("handler send warning::buttonpanel is null");
		return buttonpanel;
	}

	public void setButtonpanel(ButtonPanel buttonpanel) {
		this.buttonpanel = buttonpanel;
	}
	
}
