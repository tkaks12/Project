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
		setTitle("�α���");
		panelId = new JPanel();
		panelBt = new JPanel();
		panelPass = new JPanel();
		labelId = new JLabel("���̵�");
		labelPass = new JLabel("��й�ȣ");
		textId = new JTextField(10);
		textPass = new JPasswordField(10);
		btnLo = new JButton("�α���");
		btnNew = new JButton("ȸ������");
		btnmod = new JButton("Ż�� or ����");
		textId.setSize(200, 200);
		textPass.setSize(200, 200);
		panelId.add(labelId);
		panelId.add(textId);
		
		// �α��� ��ư
		btnLo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JoinDao dao = new JoinDao();
				char[] pass = textPass.getPassword();
				String sps = new String(pass);
				// �α����� �Ǹ� �α���â �ݾ��ְ� ���������� ����
				if (dao.LogCheck(textId.getText(), sps) == true) {
					
					/*�߰�*/
					String id = textId.getText();
					
					MainFrame open = new MainFrame(id);
					dispose();
				} else {
					System.out.println("���̵� ��� ����");
				}
			}
		});

		// ȸ������ ��ư
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
