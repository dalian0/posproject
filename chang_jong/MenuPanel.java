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
	private String[] menu = {"돌솥밥","불고기백반","간장게장백반","생선구이백반","된장찌개백반", 
				 	"백반","도루묵찜","김치전골","가자미회무침","제육볶음","두부전골","소주","맥주","음료수","공기밥"}; 
	private String[] pay = {"12000", "10000", "9000", "9000", "7000", "6000", "30000", "30000", "25000",  
					"25000", "15000", "4000", "4000", "2000", "1000"}; 
	private JScrollPane scrollPane, scrollPane2;

	private Order orderpanel;
	private JList ordername, ordernum, orderpay; // 주문명, 수량, 가격
	private DefaultListModel mlistmodel, plistmodel, namemodel, nummodel, paymodel;

	private JLabel NameLabel, PriceLabel;
	private Table table;
	
	public MenuPanel(Order order) {
		setLayout(null);
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int wid=(res.width/4);				//음식 패널 넓이
		int hei=((res.height/10)*9);			//음식 패널 높이
		
		// 메뉴, 가격 리스트 객체 생성
		menulist = new JList(menu);
		paylist = new JList(pay);
		// 리스트 아이템 하나씩만 선택가능하게 설정
		menulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 20행 보이게 하기
		menulist.setVisibleRowCount(20);
		paylist.setVisibleRowCount(20);
		
		// 주문패널 객체 참조
		orderpanel = order;
		ordername = orderpanel.NameList;
		ordernum = orderpanel.NumList;
		orderpay = orderpanel.PriceList;
				
		menulist.addMouseListener(this);
		paylist.addMouseListener(this);
		
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
	
	@Override
    public void mouseClicked(MouseEvent evt) {
		JList list = (JList)evt.getSource();
		// 선택한 리스트의 아이템 인덱스 
		int index = list.getSelectedIndex();
       	paylist.setSelectedIndex(index);
       	paylist.setSelectionBackground(new Color(184, 207, 229));
       	menulist.setSelectedIndex(index);
       	menulist.setSelectionBackground(new Color(184, 207, 229));
       	
	    // 처음 더블클릭 시 주문 추가, 이후 한번 클릭만으로 동일메뉴 추가 가능
	    if (evt.getClickCount() >= 2) {
	       	// 리스트 아이템 받기
	       	namemodel = (DefaultListModel) ordername.getModel();
	       	nummodel  = (DefaultListModel) ordernum.getModel();
	       	paymodel  = (DefaultListModel) orderpay.getModel();
	        	
	       	// 클릭한 아이템 이름, 가격 저장
	       	String menuname = menu[index];
	       	String menupay = pay[index]; // 이름에 해당하는 인덱스 참조
	        	
		    // 메뉴 중복 검사
		    int i;
		    for ( i = 0; i < namemodel.getSize() ; i++ ) {    		
		    	// 같은 메뉴가 있다면
		    	if ( namemodel.get(i).equals(menuname)) {		
		    
		    		
		    		String selectnum = (String)nummodel.get(i); // 숫자열 저장
		    		int selectnumi = Integer.parseInt(selectnum) + 1; // 숫자열 정수로 변환후 1증가
		        			
		    		// 수량 리스트 수정
		    		nummodel.remove(i);
		    		nummodel.insertElementAt(Integer.toString(selectnumi), i); 
			        			
		    		String selectpay = (String)paymodel.get(i); // 기존 가격 숫자열 저장
		    		// 기존 가격 + (기존가격/수량  == 1인분) 값 저장
		    		int selectpayi = Integer.parseInt(selectpay) + (Integer.parseInt(selectpay)/Integer.parseInt(selectnum));
		    		// 가격 리스트 수정
		    		paymodel.remove(i);
		    		paymodel.insertElementAt(Integer.toString(selectpayi), i);
			        
		    		orderpanel.sumPay(paymodel);
		    		
		    		// 테이블에 수정
		    		table.setMenu(i, Integer.toString(selectnumi), Integer.toString(selectpayi));
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

	    	orderpanel.sumPay(paymodel);

	    	// 테이블에 저장
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
    
    // 테이블을 설정하는 메소드
    public void setTable(Table table) {
    	this.table = table;
    }

}
