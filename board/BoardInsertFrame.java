package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BoardInsertFrame extends JFrame {
	private JPanel panMain;
	private JLabel lbMain, lbTitle, lbCon;
	private JButton btnYes, btnNo;
	private JTextField textTitle;
	private JTextArea textArea;
	private JScrollPane scrollText;
	private btnACtion btnAC = new btnACtion();
	private String id, title, content;
	private BoardDTO BoardDB;
	private Board bd;
	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	private Date day = new Date();

	private BufferedWriter bw ; 
	
	public BoardInsertFrame(String id, Board bd, Socket refreshSocket) {
		this.id = id;
		this.bd = bd;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(refreshSocket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("�Խñ� ���� ���� ���� ����");
			e.printStackTrace();
		}
		
		panMain = new JPanel();
		lbMain = new JLabel("�� �ۼ�");
		lbTitle = new JLabel("����");
		lbCon = new JLabel("����");
		textArea = new JTextArea();
		textTitle = new JTextField();
		btnYes = new JButton("Ȯ��");
		btnNo = new JButton("���");
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
		btnYes.setBounds(100, 300, 80, 35);
		btnNo.setBounds(210, 300, 80, 35);
		textArea.requestFocus();

		panMain.add(scrollText);
		panMain.add(lbMain);
		panMain.add(lbTitle);
		panMain.add(lbCon);
		panMain.add(btnYes);
		panMain.add(btnNo);
		panMain.add(textTitle);

		// ��ư �׼�
		btnAC.btnYes(btnYes);
		btnAC.btnNo(btnNo);

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
					bdDto = new BoardDTO();
					bdDto.setId(id);
					bdDto.setTitle(title);
					bdDto.setContents(content);
					bdDto.setDate(dateFormat.format(day));

					bdDao = new BoardDao();
					if (1 == bdDao.BoardInsert(bdDto)) {
//						bd.resetContent();
//						bd.showContent();
						bd.setCount();
						
						try {
							bw.write("refresh\n");
						} catch (IOException e1) {
							System.out.println("���� �޼��� �۽� ����");
							e1.printStackTrace();
						}
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
	}

}
