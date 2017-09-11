package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BoardEditFrame extends JFrame {
	private JPanel panMain;
	private JLabel lbMain, lbTitle, lbCon;
	private JButton btnYes, btnNo, btnDel;
	private JTextField textTitle;
	private JTextArea textArea;
	private JScrollPane scrollText;
	private btnACtion btnAC = new btnACtion();
	private String id, title, content, date;
	private int number;
	private Board bd;
	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	private Date day = new Date();
	private BoardDao dao = new BoardDao();

	public BoardEditFrame(String id,int number, Board bd) {
		this.id = id;
		this.number = number;
		this.bd = bd;

		/* Ŭ���� �κ��� DB���� ������ */
		Vector<BoardDTO> boardVector = dao.selectBoardVector(number);
		int count = 0; // �Խñ� count;
		for (BoardDTO b : boardVector) {
			id = b.getId();
			title = b.getTitle();
			content = b.getContents();
			date = b.getDate();
		}

		panMain = new JPanel();
		lbMain = new JLabel("�� �ۼ�");
		lbTitle = new JLabel("����");
		lbCon = new JLabel("����");
		textTitle = new JTextField(title);
		textArea = new JTextArea(content);
		btnYes = new JButton("Ȯ��");
		btnNo = new JButton("���");
		btnDel = new JButton("����");
		scrollText = new JScrollPane(textArea);

		// ���� ��ũ�� �Ⱦ��� ��
		scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// ���� ��ũ�Ѹ� ����
		scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// textArea �ڵ� �ٹٲ��Լ�
		textArea.setLineWrap(true);

		// ��ġ ����
		lbMain.setBounds(180, 10, 50, 30);
		lbTitle.setBounds(20, 50, 50, 30);
		lbCon.setBounds(20, 80, 50, 30);
		scrollText.setBounds(60, 90, 300, 200);
		textTitle.setBounds(60, 55, 300, 20);
		btnYes.setBounds(60, 300, 80, 35);
		btnNo.setBounds(150, 300, 80, 35);
		btnDel.setBounds(240, 300, 80, 35);
		textArea.requestFocus();

		panMain.add(scrollText);
		panMain.add(lbMain);
		panMain.add(lbTitle);
		panMain.add(lbCon);
		panMain.add(btnYes);
		panMain.add(btnNo);
		panMain.add(btnDel);
		panMain.add(textTitle);

		// ��ư �׼�
		btnAC.btnYes(btnYes);
		btnAC.btnNo(btnNo);
		btnAC.btnDel(btnDel);

		// ������ ����
		panMain.setLayout(null);
		add(panMain);
		setSize(400, 400);
		setResizable(false);
		setVisible(true);
	}

	/* ��ư �׼� Ŭ���� */
	class btnACtion {
		BoardDTO bdDto;
		BoardDao bdDao;

		public void btnYes(JButton btnYes) {
			btnYes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					title = textTitle.getText();
					content = textArea.getText();
					date = dateFormat.format(day);

					bdDao = new BoardDao();
					if (bdDao.BoardUpdate(id,title, content, date, number) == 1) {
						bd.resetContent();
						bd.showContent();
						bd.setCount();
					} else {
					}
					dispose();
				}
			});
		}

		public void btnNo(JButton btnNo) {
			btnNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}

		public void btnDel(JButton jbtDel) {
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bdDao = new BoardDao();
					if (bdDao.BoardDelete(id,number) == 1) {
						bd.resetContent();
						bd.showContent();
						bd.setCount();
					} else {
					}
					dispose();
				}
			});
		}

	}

}
