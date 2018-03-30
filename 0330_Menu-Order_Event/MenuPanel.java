package chang_jong;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MenuPanel extends JPanel {
	
	private JList menulist,paylist;
	private String[] menu = {"���ܹ�","�Ұ����","���������","�������̹��","��������", 
				 	"���","���繬��","��ġ����","���ڹ�ȸ��ħ","��������","�κ�����","����","����","�����","�����"}; 
	private String[] pay = {"12000", "10000", "9000", "9000", "7000", "6000", "30000", "30000", "25000",  
					"25000", "15000", "4000", "4000", "2000", "1000"}; 

	private JScrollPane scrollPane, scrollPane2;

	private Order orderpanel;
	private JList ordername, ordernum, orderpay; // �ֹ���, ����, ����
	private DefaultListModel mlistmodel, plistmodel, namemodel, nummodel, paymodel;

	private JLabel NameLabel, PriceLabel;
	
	public MenuPanel(Order order) {
		setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int wid=(res.width/4);				//���� �г� ����
		int hei=((res.height/10)*9);			//���� �г� ����
		
		menulist = new JList(menu);
		paylist = new JList(pay);
		menulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menulist.setVisibleRowCount(20);
		paylist.setVisibleRowCount(20);
		
		// �ֹ��г� ��ü ����
		orderpanel = order;
		ordername = orderpanel.NameList;
		ordernum = orderpanel.NumList;
		orderpay = orderpanel.PriceList;
				
		menulist.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        // ó�� ����Ŭ�� �� �ֹ� �߰�, ���� �ѹ� Ŭ�������� ���ϸ޴� �߰� ����
		        if (evt.getClickCount() >= 2) {
		        	// ����Ʈ ������ �ޱ�
		        	namemodel = (DefaultListModel) ordername.getModel();
		        	nummodel  = (DefaultListModel) ordernum.getModel();
		        	paymodel  = (DefaultListModel) orderpay.getModel();
		        	
		        	// Ŭ���� ������ �̸�, ���� ����
		        	String menuname = (String)list.getSelectedValue();
		        	String menupay = pay[list.getSelectedIndex()]; // �̸��� �ش��ϴ� �ε��� ����
				        	
				    // �޴� �ߺ� �˻�
				    int i;
				    for ( i = 0; i < namemodel.getSize() ; i++ ) {    		
				    	// ���� �޴��� �ִٸ�
				    	if ( namemodel.get(i).equals(menuname)) {		
				   		
				    		String selectnum = (String)nummodel.get(i); // ���ڿ� ����
				    		int selectnumi = Integer.parseInt(selectnum) + 1; // ���ڿ� ������ ��ȯ�� 1����
				        			
				    		// ����Ʈ ����
				    		nummodel.remove(i);
				    		nummodel.insertElementAt(Integer.toString(selectnumi), i); 
				        			
				    		String selectpay = (String)paymodel.get(i); // ���� ���ڿ� ����
				    		// ���� ���� + (��������/����  == 1�κ�) �� ����
				    		int selectpayi = Integer.parseInt(selectpay) + (Integer.parseInt(selectpay)/Integer.parseInt(selectnum));
				        			
				    		// ����Ʈ ����
				    		paymodel.remove(i);
				    		paymodel.insertElementAt(Integer.toString(selectpayi), i);
				        
				    		sumPay(paymodel);
				    		return; // ���� �Ϸ�
				    	}
				    }
				    // �ֹ����� �ش� �޴��� ���ٸ�
				    namemodel.insertElementAt(menuname, i);
				    nummodel.insertElementAt("1", i);
				    paymodel.insertElementAt(menupay, i);
				    ordername.setModel(namemodel);
				    ordernum.setModel(nummodel);
				    orderpay.setModel(paymodel);

		    		sumPay(paymodel);
		        } 
		    }
		});
		
		scrollPane = new JScrollPane(menulist);
		scrollPane2= new JScrollPane(paylist);
		
		NameLabel = new JLabel("���ĸ�");			//���ĸ��
		NameLabel.setBounds(0, 0, wid/3*2, 20);
		NameLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(NameLabel);
		
		PriceLabel = new JLabel("����");			//���ݶ�
		PriceLabel.setBounds(wid/3*2, 0, wid/3, 20);
		PriceLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(PriceLabel);
		scrollPane.setBounds(0, 20, wid/3*2, hei-20);
		scrollPane2.setBounds(wid/3*2, 20, wid/3, hei-20);
		add(scrollPane);
		add(scrollPane2);
	}
	
	// ����Ʈ ���� ���ؼ� �Ѱ��� �ؽ�Ʈ�ʵ忡 ����
	public void sumPay(DefaultListModel paymodel) {
		int sum = 0;
		for ( int i = 0; i < paymodel.size() ; i++ ) {
			sum += Integer.parseInt((String)paymodel.get(i));
		}
		//�Ѱ��� �ؽ�Ʈ�ʵ� ����
		orderpanel.TotalText.setText(Integer.toString(sum) + " ��");
	}
}
