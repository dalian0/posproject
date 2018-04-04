package chang_jong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import chang_jong.entites.Table;

public class Order extends JPanel {

	protected JPanel ListPanel, ButtonPanel;
	protected JList NameList, NumList, PriceList; // 주문명, 수량, 가격
	protected JLabel NameLabel, NumLabel, PriceLabel, TotalLabel;
	protected JButton CancelButton, ResetButton, CalButton;
	protected JTextField TotalText;
	private DefaultListModel listmodel, NameModel, NumModel, PriceModel;
	private Table table;
	private ArrayList<String> menuArray;
	private ArrayList<String> numArray;
	private ArrayList<String> payArray;
	public Order() {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int wid=(res.width/4);				//주문 패널 넓이
		int hei=((res.height/10)*9);			//주문 패널 높이
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setSize(wid, hei);
		//리스트들 들어가 있는 패널
		ListPanel = new JPanel();			
		ListPanel.setBounds(0,0, wid, (hei/5)*4);
		add(ListPanel);
		ListPanel.setLayout(null);
		
		//음식명라벨
		NameLabel = new JLabel("주문명");			
		NameLabel.setBounds(0, 0, wid/2, 20);
		NameLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		ListPanel.add(NameLabel);
		
		//수량라벨
		NumLabel = new JLabel("수량");				
		NumLabel.setBounds(wid/2, 0, wid/4, 20);
		NumLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		ListPanel.add(NumLabel);
		
		//가격라벨
		PriceLabel = new JLabel("가격");			
		PriceLabel.setBounds((wid/2)+(wid/4), 0, wid/4, 20);
		PriceLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		ListPanel.add(PriceLabel);
		
		//음식명 리스트	
		NameList = new JList(new DefaultListModel());								
		NameList.setBounds(0, 20, wid/2, (hei/5)*4-20-hei/5/2);
		NameList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ListPanel.add(NameList);
		
		//수량 리스트
		NumList = new JList(new DefaultListModel());					
		NumList.setBounds(wid/2, 20, wid/4, (hei/5)*4-20-hei/5/2);
		NumList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ListPanel.add(NumList);
		
		//가격 리스트
		PriceList = new JList(new DefaultListModel());					
		PriceList.setBounds((wid/2)+(wid/4), 20, wid/4, (hei/5)*4-20-hei/5/2);
		PriceList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ListPanel.add(PriceList);
		
		//가격라벨
		TotalLabel = new JLabel("총 가격  : ");											
		TotalLabel.setBounds(0, (hei/5)*4-hei/5/2, wid/4, hei/5/2);
		ListPanel.add(TotalLabel);
		
		//총가격 텍스트 필드
		TotalText = new JTextField("0 원");			
		TotalText.setBounds(wid/4, (hei/5)*4-hei/5/2, wid/4*3, hei/5/2);
		TotalText.setEditable(false);
		ListPanel.add(TotalText);																
		
		ButtonPanel = new JPanel();				//버튼 패널
		ButtonPanel.setBounds(0, (hei/5)*4, wid, hei/5);
		add(ButtonPanel);
		ButtonPanel.setLayout(null);
		
		//취소 버튼
		CancelButton = new JButton("취소");
		CancelButton.setBounds(0, 0, wid/2, hei/5/2);
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectIndex = NameList.getSelectedIndices();

				// 리스트 아이템이 없거나 선택을 하지 않았을 경우 무시
				if ( selectIndex == null) {
					return;
				}
				
				// 동시에 다수 취소가 가능한 반복문
				// 다수 지울 시 앞의 인덱스부터 지울 경우 배열범위 예외가 발생하므로 끝에서 부터 삭제함
				for ( int i = selectIndex.length - 1; i >= 0 ; i-- ) {
					listCancel(listmodel,selectIndex[i]);
				}
					
				
			
			}
		});
		ButtonPanel.add(CancelButton);
		
		//초기화 버튼
		ResetButton = new JButton("초기화");
		ResetButton.setBounds(wid/2, 0, wid/2, hei/5/2);		
		ResetButton.addActionListener(new ActionListener() {
			// 리스트 아이템 전체 제거
			public void actionPerformed(ActionEvent e) {
				listReset(listmodel);
			}
		});
		ButtonPanel.add(ResetButton);
		
		//계산 버튼
		CalButton = new JButton("계산");
		CalButton.setBounds(0, hei/5/2, wid, hei/5/2);		
		CalButton.addActionListener(new ActionListener() {
			// 리스트 아이템 전체 제거
			public void actionPerformed(ActionEvent e) {
				listmodel = (DefaultListModel) NumList.getModel();
				
				//리스트가 비어있을 경우
				if ( listmodel.isEmpty() ) {
					JOptionPane.showMessageDialog(null, "계산할 수 없습니다.\n다시 확인해 주세요");
					return;
				}
				
				// 계산 팝업 예 버튼 클릭시 처리
				if(JOptionPane.showConfirmDialog(null, "계산하시겠습니까?", "계산확인", JOptionPane.YES_NO_OPTION) 
						== JOptionPane.YES_OPTION) {
					// DB에 주문내역 저장 코드 구현 나중에
					
					// 완료 팝업창 띄우고 리스트 전체 삭제
					JOptionPane.showMessageDialog(null, "계산이 완료되었습니다!");
					listReset(listmodel);
				}
				
			}
		});
		ButtonPanel.add(CalButton);

	}

	// 초기화 실행 메소드
	public void listReset(DefaultListModel listmodel) {
		// 리스트 전체 삭제
		listmodel = (DefaultListModel) NameList.getModel();
		listmodel.removeAllElements();
		listmodel = (DefaultListModel) NumList.getModel();
		listmodel.removeAllElements();
		listmodel = (DefaultListModel) PriceList.getModel();
		listmodel.removeAllElements();
		TotalText.setText("0 원");
		
		// 테이블 전체 삭제
		menuArray.clear();
		numArray.clear();
		payArray.clear();
		
	}

	// 초기화 실행 메소드
	public void listClear(DefaultListModel listmodel) {
		// 리스트 전체 삭제
		listmodel = (DefaultListModel) NameList.getModel();
		listmodel.removeAllElements();
		listmodel = (DefaultListModel) NumList.getModel();
		listmodel.removeAllElements();
		listmodel = (DefaultListModel) PriceList.getModel();
		listmodel.removeAllElements();
		TotalText.setText("0 원");
		
	}
	
	// 취소 실행 메소드
	public void listCancel(DefaultListModel listmodel, int index) {	
		// 해당 이름 리스트 삭제
		listmodel = (DefaultListModel) NameList.getModel();
		listmodel.remove(index);
		// 해당 수량 리스트 삭제
		listmodel = (DefaultListModel) NumList.getModel();
		listmodel.remove(index);
		listmodel = (DefaultListModel) PriceList.getModel();
		//가격
	/*	String totalprice_s = TotalText.getText().split(" ")[0];
		int totalprice = Integer.parseInt(totalprice_s);
		int minusprice = Integer.parseInt((String)listmodel.get(index));
		totalprice -= minusprice;
		TotalText.setText(Integer.toString(totalprice) + " 원");*/
		listmodel.remove(index);
		
		sumPay(listmodel);
		
		// 테이블 해당 주문 내역 삭제
		menuArray.remove(index);
		numArray.remove(index);
		payArray.remove(index);
		
	}
	
	// 테이블 내용 주문패널 리스트에 저장
	public void loadList() {
		ArrayList<String> menuArray = table.getMenu();
		ArrayList<String> numArray = table.getNum();
		ArrayList<String> payArray = table.getPay();
		
		// 비어있을 경우 무시
		if ( menuArray.isEmpty() ) 
			return;
		
		// 각 리스트들 리스트 모델로 받기
		NameModel = (DefaultListModel) NameList.getModel();
		NumModel  = (DefaultListModel) NumList.getModel();
		PriceModel  = (DefaultListModel) PriceList.getModel();
        
		for ( int i = 0; i < menuArray.size() ; i++ ) {
			NameModel.insertElementAt(menuArray.get(i), i);
			NumModel.insertElementAt(numArray.get(i), i);
			PriceModel.insertElementAt(payArray.get(i), i);
		}

		NameList.setModel(NameModel);
		NumList.setModel(NumModel);
		PriceList.setModel(PriceModel);
		
		sumPay(PriceModel);
	}

    // 테이블을 설정하는 메소드
    public void setTable(Table table) {
    	this.table = table;
    	this.menuArray = table.getMenu();
    	this.numArray = table.getNum();
    	this.payArray = table.getPay();
    }
    
	// 리스트 가격 합해서 총가격 텍스트필드에 설정
	public void sumPay(DefaultListModel paymodel) {
		int sum = 0;
		for ( int i = 0; i < paymodel.size() ; i++ ) {
			sum += Integer.parseInt((String)paymodel.get(i));
		}
		//총가격 텍스트필드 설정
		TotalText.setText(Integer.toString(sum) + " 원");
	}
}