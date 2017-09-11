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
			System.out.println("게시글 갱신 서버 소켓 오류");
			e.printStackTrace();
		}
		
		panMain = new JPanel();
		lbMain = new JLabel("글 작성");
		lbTitle = new JLabel("제목");
		lbCon = new JLabel("내용");
		textArea = new JTextArea();
		textTitle = new JTextField();
		btnYes = new JButton("확인");
		btnNo = new JButton("취소");
		scrollText = new JScrollPane(textArea);

		// 가로 스크롤 안쓰게 함
		scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 세로 스크롤만 쓰게
		scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// textArea 자동 줄바꿈함수
		textArea.setLineWrap(true);

		// 위치 셋팅
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

		// 버튼 액션
		btnAC.btnYes(btnYes);
		btnAC.btnNo(btnNo);

		// 프레임 설정
		panMain.setLayout(null);
		add(panMain);
		setSize(400, 400);
		setResizable(false);
		setVisible(true);
	}

	/* 버튼 액션 클래스 */
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
							System.out.println("갱신 메세지 송신 오류");
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
