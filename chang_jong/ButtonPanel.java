package chang_jong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ButtonPanel extends JPanel implements ActionListener{

	private boolean tableButton_clicked = false;
	private JButton tableButton;
	
	public ButtonPanel() {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		//변수 이름이 에바참치라 수정함. (before) width -> wid 에바참치10참치
		int width=(res.width);				//버튼 패널 넓이
		int height=((res.height/10));			//버튼 패널 높이
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setSize(width, height);
		
		
		tableButton = new JButton("[테이블 관리]");
		tableButton.setBounds(0, 0, width/15, height);
		add(tableButton);
		tableButton.addActionListener(this);// 03.27 대원이가 추가.

	}

	//테이블 관리, 비관리 중에 따라 버튼 모양이 다르게 할 것.
	@Override
	public void actionPerformed(ActionEvent e) {
		tableButton_clicked = !tableButton_clicked;
		if (tableButton_clicked) {
			tableButton.setText("[테이블 관리중]");
		}else {
			tableButton.setText("[테이블 관리]");
		}
	}
	
	public boolean getTableButton_Clicked() {
		return this.tableButton_clicked;
	}
	
	public JButton getTableButton() {
		return this.tableButton;
	}
}
