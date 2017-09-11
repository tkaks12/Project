package join;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Join extends JFrame {
	// 라벨8개 아디 비번 이름 생년월일 이메일 전번 회원가입
	// 라디오버튼2개
	// 텍스트7개 아디 비번 이름 생년월일 이멜 전번
	// 콤보박스2개 //010,02,@naver,daum등등 (직접입력포함)
	// 버튼3개 //중복,확인,취소
	private JRadioButton rbM, rbW;
	private JTextField textId, textName, textBirth, textMail, textPhone;
	private JPasswordField textPass;
	private JButton btnOver, btnCon, btnCan;
	private JPanel panel;
	private ImageIcon icon;
	private String mw = "";
	JoinDTO people;
	JoinDao join = new JoinDao();
	


	public Join() {

		// C:\Users\student\Desktop\lhg
		icon = new ImageIcon("C:\\캡쳐.JPG");
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(), 0, 0, null);
			}
		};
		rbM = new JRadioButton("남자");
		rbW = new JRadioButton("여자");
		textId = new JTextField();
		textPass = new JPasswordField();
		textName = new JTextField();
		textBirth = new JTextField();
		textMail = new JTextField();
		textPhone = new JTextField();
		btnOver = new JButton("");
		btnCon = new JButton("");
		btnCan = new JButton("");
		if(rbM.isSelected()) {
			mw = "M";
		}else if(rbW.isSelected()) {
			mw = "W";
		}

		//////////////////////// 버튼 중복적용/////////////////////
		ButtonGroup size = new ButtonGroup();
		size.add(rbM);
		size.add(rbW);
		/////////////////////////////////////////////////////
		panel.setLayout(null);
		textId.setBounds(173, 74, 155, 20);
		textPass.setBounds(173, 123, 155, 20);
		textName.setBounds(173, 165, 155, 20);
		textBirth.setBounds(173, 205, 155, 20);
		textMail.setBounds(173, 282, 155, 20);
		textPhone.setBounds(173, 327, 155, 20);
		rbM.setBounds(172, 240, 20, 20);
		rbW.setBounds(262, 240, 20, 20);
		btnOver.setBounds(345, 56, 104, 38);
		btnCon.setBounds(100, 380, 120, 45);
		btnCan.setBounds(260, 380, 120, 45);
		//////////////////////////////////////////////////////////
		// 가입
		btnCon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(join.ADD(joinsub())>0) {
				System.out.println("가입이 완료됬습니다.");
				setVisible(false);
				}
				else System.out.println("가입실패");
			}
		});
		// 중복확인
		btnOver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				join.idcheck(textId.getText());
			}
		});
		// 취소
		btnCan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
				setVisible(false);
			}
		});
		/////////////////////// 그림 뒤에 숨기기////////////////////
		btnCon.setContentAreaFilled(false);
		btnCan.setContentAreaFilled(false);
		btnOver.setContentAreaFilled(false);
		textId.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textPass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textBirth.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textMail.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		textPhone.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		////////////////////////////////////////////////////////

		panel.add(rbM);
		panel.add(rbW);
		panel.add(textId);
		panel.add(textPass);
		panel.add(textName);
		panel.add(textBirth);
		panel.add(textMail);
		panel.add(textPhone);
		panel.add(btnOver);
		panel.add(btnCon);
		panel.add(btnCan);

		add(panel);

		setResizable(false);
		setSize(475, 480);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public JoinDTO joinsub() {
		people = new JoinDTO();
		String id = textId.getText();
		char[] pwd = textPass.getPassword();
		String pass = new String(pwd);
		String name = textName.getText();
		String birth = textBirth.getText();
		String mail = textMail.getText();
		String phone = textPhone.getText();
		if(rbM.isSelected()) {
			mw = "M";
		}else if(rbW.isSelected()) {
			mw = "W";
		}
		people.setId(id);
		people.setPass(pass);
		people.setName(name);
		people.setBirth(birth);
		people.setGender(mw);
		people.setMail(mail);
		people.setPhone(phone);
		
		return people;
	}
}
