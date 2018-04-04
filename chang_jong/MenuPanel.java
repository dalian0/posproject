package chang_jong;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import chang_jong.entites.Table;


public class MenuPanel extends JPanel implements MouseListener {
	
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
	private Table table;
	
	public MenuPanel(Order order) {
		setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int wid=(res.width/4);				//���� �г� ����
		int hei=((res.height/10)*9);			//���� �г� ����
		
		// �޴�, ���� ����Ʈ ��ü ����
		menulist = new JList(menu);
		paylist = new JList(pay);
		// ����Ʈ ������ �ϳ����� ���ð����ϰ� ����
		menulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 20�� ���̰� �ϱ�
		menulist.setVisibleRowCount(20);
		paylist.setVisibleRowCount(20);
		
		// �ֹ��г� ��ü ����
		orderpanel = order;
		ordername = orderpanel.NameList;
		ordernum = orderpanel.NumList;
		orderpay = orderpanel.PriceList;
				
		menulist.addMouseListener(this);
		paylist.addMouseListener(this);
		
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
	
	@Override
    public void mouseClicked(MouseEvent evt) {
		JList list = (JList)evt.getSource();
		// ������ ����Ʈ�� ������ �ε��� 
		int index = list.getSelectedIndex();
       	paylist.setSelectedIndex(index);
       	paylist.setSelectionBackground(new Color(184, 207, 229));
       	menulist.setSelectedIndex(index);
       	menulist.setSelectionBackground(new Color(184, 207, 229));
       	
	    // ó�� ����Ŭ�� �� �ֹ� �߰�, ���� �ѹ� Ŭ�������� ���ϸ޴� �߰� ����
	    if (evt.getClickCount() >= 2) {
	       	// ����Ʈ ������ �ޱ�
	       	namemodel = (DefaultListModel) ordername.getModel();
	       	nummodel  = (DefaultListModel) ordernum.getModel();
	       	paymodel  = (DefaultListModel) orderpay.getModel();
	        	
	       	// Ŭ���� ������ �̸�, ���� ����
	       	String menuname = menu[index];
	       	String menupay = pay[index]; // �̸��� �ش��ϴ� �ε��� ����
	        	
		    // �޴� �ߺ� �˻�
		    int i;
		    for ( i = 0; i < namemodel.getSize() ; i++ ) {    		
		    	// ���� �޴��� �ִٸ�
		    	if ( namemodel.get(i).equals(menuname)) {		
		    
		    		
		    		String selectnum = (String)nummodel.get(i); // ���ڿ� ����
		    		int selectnumi = Integer.parseInt(selectnum) + 1; // ���ڿ� ������ ��ȯ�� 1����
		        			
		    		// ���� ����Ʈ ����
		    		nummodel.remove(i);
		    		nummodel.insertElementAt(Integer.toString(selectnumi), i); 
			        			
		    		String selectpay = (String)paymodel.get(i); // ���� ���� ���ڿ� ����
		    		// ���� ���� + (��������/����  == 1�κ�) �� ����
		    		int selectpayi = Integer.parseInt(selectpay) + (Integer.parseInt(selectpay)/Integer.parseInt(selectnum));
		    		// ���� ����Ʈ ����
		    		paymodel.remove(i);
		    		paymodel.insertElementAt(Integer.toString(selectpayi), i);
			        
		    		orderpanel.sumPay(paymodel);
		    		
		    		// ���̺� ����
		    		table.setMenu(i, Integer.toString(selectnumi), Integer.toString(selectpayi));
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

	    	orderpanel.sumPay(paymodel);

	    	// ���̺� ����
    		table.addMenu(menuname, "1", menupay);
	    }
	}


		
    @Override
    public void mouseEntered(MouseEvent evt) {}

    @Override
    public void mouseExited(MouseEvent evt) {}

    @Override
    public void mousePressed(MouseEvent evt) {}

    @Override
    public void mouseReleased(MouseEvent evt) {}
   
    public void setList(boolean arg) {
    	menulist.setEnabled(arg);
    	paylist.setEnabled(arg);
    }
    
    // ���̺��� �����ϴ� �޼ҵ�
    public void setTable(Table table) {
    	this.table = table;
    }

}
