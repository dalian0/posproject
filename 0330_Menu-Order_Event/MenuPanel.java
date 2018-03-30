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
	private String[] menu = {"돌솥밥","불고기백반","간장게장백반","생선구이백반","된장찌개백반", 
				 	"백반","도루묵찜","김치전골","가자미회무침","제육볶음","두부전골","소주","맥주","음료수","공기밥"}; 
	private String[] pay = {"12000", "10000", "9000", "9000", "7000", "6000", "30000", "30000", "25000",  
					"25000", "15000", "4000", "4000", "2000", "1000"}; 

	private JScrollPane scrollPane, scrollPane2;

	private Order orderpanel;
	private JList ordername, ordernum, orderpay; // 주문명, 수량, 가격
	private DefaultListModel mlistmodel, plistmodel, namemodel, nummodel, paymodel;

	private JLabel NameLabel, PriceLabel;
	
	public MenuPanel(Order order) {
		setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int wid=(res.width/4);				//음식 패널 넓이
		int hei=((res.height/10)*9);			//음식 패널 높이
		
		menulist = new JList(menu);
		paylist = new JList(pay);
		menulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		menulist.setVisibleRowCount(20);
		paylist.setVisibleRowCount(20);
		
		// 주문패널 객체 참조
		orderpanel = order;
		ordername = orderpanel.NameList;
		ordernum = orderpanel.NumList;
		orderpay = orderpanel.PriceList;
				
		menulist.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        // 처음 더블클릭 시 주문 추가, 이후 한번 클릭만으로 동일메뉴 추가 가능
		        if (evt.getClickCount() >= 2) {
		        	// 리스트 아이템 받기
		        	namemodel = (DefaultListModel) ordername.getModel();
		        	nummodel  = (DefaultListModel) ordernum.getModel();
		        	paymodel  = (DefaultListModel) orderpay.getModel();
		        	
		        	// 클릭한 아이템 이름, 가격 저장
		        	String menuname = (String)list.getSelectedValue();
		        	String menupay = pay[list.getSelectedIndex()]; // 이름에 해당하는 인덱스 참조
				        	
				    // 메뉴 중복 검사
				    int i;
				    for ( i = 0; i < namemodel.getSize() ; i++ ) {    		
				    	// 같은 메뉴가 있다면
				    	if ( namemodel.get(i).equals(menuname)) {		
				   		
				    		String selectnum = (String)nummodel.get(i); // 숫자열 저장
				    		int selectnumi = Integer.parseInt(selectnum) + 1; // 숫자열 정수로 변환후 1증가
				        			
				    		// 리스트 수정
				    		nummodel.remove(i);
				    		nummodel.insertElementAt(Integer.toString(selectnumi), i); 
				        			
				    		String selectpay = (String)paymodel.get(i); // 가격 숫자열 저장
				    		// 기존 가격 + (기존가격/수량  == 1인분) 값 저장
				    		int selectpayi = Integer.parseInt(selectpay) + (Integer.parseInt(selectpay)/Integer.parseInt(selectnum));
				        			
				    		// 리스트 수정
				    		paymodel.remove(i);
				    		paymodel.insertElementAt(Integer.toString(selectpayi), i);
				        
				    		sumPay(paymodel);
				    		return; // 동작 완료
				    	}
				    }
				    // 주문서에 해당 메뉴가 없다면
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
		
		NameLabel = new JLabel("음식명");			//음식명라벨
		NameLabel.setBounds(0, 0, wid/3*2, 20);
		NameLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(NameLabel);
		
		PriceLabel = new JLabel("가격");			//가격라벨
		PriceLabel.setBounds(wid/3*2, 0, wid/3, 20);
		PriceLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(PriceLabel);
		scrollPane.setBounds(0, 20, wid/3*2, hei-20);
		scrollPane2.setBounds(wid/3*2, 20, wid/3, hei-20);
		add(scrollPane);
		add(scrollPane2);
	}
	
	// 리스트 가격 합해서 총가격 텍스트필드에 설정
	public void sumPay(DefaultListModel paymodel) {
		int sum = 0;
		for ( int i = 0; i < paymodel.size() ; i++ ) {
			sum += Integer.parseInt((String)paymodel.get(i));
		}
		//총가격 텍스트필드 설정
		orderpanel.TotalText.setText(Integer.toString(sum) + " 원");
	}
}
