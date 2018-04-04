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
		//���� �̸��� ������ġ�� ������. (before) width -> wid ������ġ10��ġ
		int width=(res.width);				//��ư �г� ����
		int height=((res.height/10));			//��ư �г� ����
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setSize(width, height);
		
		
		tableButton = new JButton("[���̺� ����]");
		tableButton.setBounds(0, 0, width/15, height);
		add(tableButton);
		tableButton.addActionListener(this);// 03.27 ����̰� �߰�.

	}

	//���̺� ����, ����� �߿� ���� ��ư ����� �ٸ��� �� ��.
	@Override
	public void actionPerformed(ActionEvent e) {
		tableButton_clicked = !tableButton_clicked;
		if (tableButton_clicked) {
			tableButton.setText("[���̺� ������]");
		}else {
			tableButton.setText("[���̺� ����]");
		}
	}
	
	public boolean getTableButton_Clicked() {
		return this.tableButton_clicked;
	}
	
	public JButton getTableButton() {
		return this.tableButton;
	}
}
