package logIn;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import join.Join;
import join.JoinDao;
import project.MainFrame;

public class LogIn extends JFrame {
	private JPanel panelId, panelPass, panelBt;
	private JLabel labelId, labelPass;
	private JPasswordField textPass;
	private JTextField textId;
	private JButton btnLo, btnNew, btnmod;
	JoinDao join = new JoinDao();

	public LogIn() {
		setTitle("로그인");
		panelId = new JPanel();
		panelBt = new JPanel();
		panelPass = new JPanel();
		labelId = new JLabel("아이디");
		labelPass = new JLabel("비밀번호");
		textId = new JTextField(10);
		textPass = new JPasswordField(10);
		btnLo = new JButton("로그인");
		btnNew = new JButton("회원가입");
		btnmod = new JButton("탈퇴 or 수정");
		textId.setSize(200, 200);
		textPass.setSize(200, 200);
		panelId.add(labelId);
		panelId.add(textId);
		
		// 로그인 버튼
		btnLo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JoinDao dao = new JoinDao();
				char[] pass = textPass.getPassword();
				String sps = new String(pass);
				// 로그인이 되면 로그인창 닫아주고 메인프레임 실행
				if (dao.LogCheck(textId.getText(), sps) == true) {
					
					/*추가*/
					String id = textId.getText();
					
					MainFrame open = new MainFrame(id);
					dispose();
				} else {
					System.out.println("아이디나 비번 오류");
				}
			}
		});

		// 회원가입 버튼
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Join a = new Join();
			}
		});

		btnmod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		
		panelPass.add(labelPass);
		panelPass.add(textPass);

		panelBt.add(btnmod);
		panelBt.add(btnLo);
		panelBt.add(btnNew);

		add(panelId, BorderLayout.PAGE_START);
		add(panelPass, BorderLayout.CENTER);
		add(panelBt, BorderLayout.PAGE_END);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		LogIn a = new LogIn();
	}
}
