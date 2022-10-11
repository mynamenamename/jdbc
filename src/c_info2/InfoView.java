package c_info2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InfoView {

	// 1. 멤버변수 선언
	JFrame f;
	JTextField tfName, tfId, tfTel, tfGender, tfAge, tfHome;
	JTextArea ta;
	JButton bAdd, bShow, bSearch, bDelete, bCancel, bExit;

	// 비즈니스로직- 모델단 (데이터베이스 저장)
	infoModel model;

	// 2. 객체 생성
	InfoView() {
		f = new JFrame("DBTest");

		tfName = new JTextField(15);
		tfId = new JTextField(15);
		tfTel = new JTextField(15);
		tfGender = new JTextField(15);
		tfAge = new JTextField(15);
		tfHome = new JTextField(15);

		bAdd = new JButton("Add");
		bShow = new JButton("Show");
		bSearch = new JButton("Search");
		bDelete = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bExit = new JButton("수정하기");

		// center 입력창
		ta = new JTextArea(40, 20);

		// 모델객체 생성
		try {
			model = new InfoModelimpl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// 3. 화면 구성하고 출력
	/*
	 * hint: 전체 프레임>> BorderLayout 지정 -west : JPanel 붙이기 >>> GridLayout(6,2)로 지정
	 * -center : textarea -south : JPanel 붙이기 >>> GridLayout(1,6)로 지정
	 */
	public void addLayout() {

		// 전체 프레임 정렬 방식
		f.setLayout(new BorderLayout());

		// 붙이기 작업

		// west
		JPanel pWest = new JPanel();

		// 모두 다 안에 넣기
		pWest.add(new JLabel("Name", JLabel.CENTER)); // 글자 가운데로
		pWest.add(tfName);
		pWest.add(new JLabel("ID", JLabel.CENTER));
		pWest.add(tfId);
		pWest.add(new JLabel("Tel", JLabel.CENTER));
		pWest.add(tfTel);
		pWest.add(new JLabel("Gender", JLabel.CENTER));
		pWest.add(tfGender);
		pWest.add(new JLabel("Age", JLabel.CENTER));
		pWest.add(tfAge);
		pWest.add(new JLabel("Home", JLabel.CENTER));
		pWest.add(tfHome);

		f.add(pWest, BorderLayout.WEST);
		pWest.setLayout(new GridLayout(6, 2)); // pWest 프레임 정렬 방식

		// center
		f.add(new JScrollPane(ta), BorderLayout.CENTER);

		// south
		JPanel pSouth = new JPanel();

		pSouth.add(bAdd);
		pSouth.add(bShow);
		pSouth.add(bSearch);
		pSouth.add(bDelete);
		pSouth.add(bCancel);
		pSouth.add(bExit);
		f.add(pSouth, BorderLayout.SOUTH);
		pSouth.setLayout(new GridLayout(1, 6));
		// f가 아니고 PSouth 넣는거 까먹지 말기
		// pSouth 프레임 정렬 방식

		// 화면 출력
		f.setBounds(100, 100, 600, 350);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Add 버튼이 눌렸을 때
	void eventProc() {
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertData();

			}
		});

		// Show 버튼이 눌렸을 때
		bShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();

			}
		});

		// Search 버튼이 눌렸을 때
		bSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();

			}
		});

		// 전화번호 텍스트필드에서 엔터 쳤을 때
		tfTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectByTel();

			}
		});

		// bDelete 버튼이 눌렸을때
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteByTel();

			}
		});

		// bExit 눌렀을 때 (수정)
		bExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modify();

			}
		});

		// bcancel 눌렀을때 -- 창에서만 다 지우기
		bCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearText();

			}
		});

	}// end of eventProc()

	// bExit 눌렀을 때 (수정) 함수
	void modify() { // insertdata랑 똑같음
		// (1) 사용자 입력값 얻어오기

		String name = tfName.getText();
		String id = tfId.getText();
		String tel = tfTel.getText();
		String gender = tfGender.getText();
		int age = Integer.valueOf(tfAge.getText());
		String home = tfHome.getText();

		// (2) 1번의 값들을 InfoVO에 지정 - (1) 생성자 (2) setter
		// infoVo vo = new infoVO(name, id, tel, gender, age,home); >> 생성자
		infoVO vo = new infoVO();

		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);

		// (3) 모델의 insertInfo() 호출
		try {
			model.modify(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// (4) 화면의 입력값들을 지우기
		clearText();

	}
	
	// bDelete 버튼이 눌렸을때의 함수
	
	void deleteByTel() {
		// (1) 입력한 전화번호 값을 얻어오기
		String tel = tfTel.getText();

		// (2) 모델단에 delete() 호출
		try {
			model.delete(tel);
			// (3) 화면 지우고
			clearText();
		} catch (SQLException e) {
			ta.setText("삭제실패: " + e.getMessage());
		}

	}
	// Search 버튼이 눌렸을 때, tel에서 엔터 쳤을때 
	
	void selectByTel() {
		try {
			// (1) 입력한 전화번호 값을 얻어오기
			String tel = tfTel.getText();

			// (2) 모델단에 selectByTel() 호출
			infoVO vo = model.selectByTel(tel);

			// (3) 받은 결과를 각각의 텍스트필드에 지정(출력)
			tfName.setText(vo.getName());
			tfId.setText(vo.getId());
			tfTel.setText(vo.getTel());
			tfGender.setText(vo.getGender());
			tfAge.setText(Integer.toString(vo.getAge()));
			tfHome.setText(vo.getHome());

		} catch (Exception ex) {
			ta.setText("전화번호 검색 실패 : " + ex.getMessage());
		}
	}// end of selectByTel()

	
	// Show 버튼이 눌렸을 때 
	void selectAll() {
		try {       // arraylist 쓰는 이유는 전체가 다 출력 되어야 하니께
			ArrayList<infoVO> data = model.selectAll();
			ta.setText("-----검색결과----- \n\n");
			for (infoVO vo : data) { // arraylist 써서 향상된 for문 사용 가능
				ta.append(vo.toString());
			}

		} catch (SQLException e) {
			ta.setText("검색실패: " + e.getMessage());
		}

	}
      
	// Add 버튼이 눌렸을 때   
	void insertData() {

		// (1) 사용자 입력값 얻어오기

		String name = tfName.getText();
		String id = tfId.getText();
		String tel = tfTel.getText();
		String gender = tfGender.getText();
		int age = Integer.valueOf(tfAge.getText());
		String home = tfHome.getText();

		// (2) 1번의 값들을 InfoVO에 지정 - (1) 생성자 (2) setter
		// infoVo vo = new infoVO(name, id, tel, gender, age,home); >> 생성자
		infoVO vo = new infoVO();

		vo.setName(name);
		vo.setId(id);
		vo.setTel(tel);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setHome(home);

		// (3) 모델의 insertInfo() 호출
		try {
			model.insertInfo(vo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// (4) 화면의 입력값들을 지우기
		clearText();

	}
    // 입력된 값 지우기
	void clearText() {
		tfName.setText(null);
		tfId.setText(null);
		tfTel.setText(null);
		tfGender.setText(null);
		tfAge.setText(null);
		tfHome.setText(null);

	}

	public static void main(String[] args) {

		InfoView info = new InfoView();
		info.addLayout();
		info.eventProc();

	}

}
