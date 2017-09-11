package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Board extends JPanel {
	private JTable mainTable; // 메인 테이블
	private JScrollPane scroll; // 테이블 스크롤
	public JPanel panTotal; // 전체 화면 판
	private JTextField searchText; // 검색
	private JLabel countContent, showID;
	private JButton btnWrite, btnSearch, btnReset; // 쓰기&검색 버튼
	private JPanel panNorth; // 버튼&검색 판
	private Vector userRow; // 테이블에 추가되는 게시글
	private btnAction btnAC = new btnAction(); // 버튼액션
	private BoardDTO board = null;
	private BoardDao dao = new BoardDao();
	private JComboBox searchCombo; // 검색 콤보박스
	private int count = 0;
	private String[] name = { "제목", "아이디" }; // 콤보박스 아이템
	private String titleMain[] = { "No", "제목", "아이디", "날짜" };
	private DefaultTableModel model = new DefaultTableModel(titleMain, 0) { // table의 제목 고정
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	private String contentSearchMod = "기본";
	private String userID;
	final static int THREADMOD0 = 0;	
	final static int THREADMOD1	= 1;
	final static int THREADMOD2	= 2;	//스레드 잠시 정지 
	private int threadStart = THREADMOD0; // 0일때 스레드 시작
	private boolean select = true;

	// 게시판 갱신 소켓
	private Socket socket = null;
	/* 생성자 시작 */
	public Board(String id) {
		this.userID = id;
		mainTable = new JTable(model);
		mainTable.getTableHeader().setReorderingAllowed(false);// 타이틀 위치 못바꾸게하기
		mainTable.getColumn("No").setPreferredWidth(100);
		mainTable.getColumn("제목").setPreferredWidth(570);
		mainTable.getColumn("아이디").setPreferredWidth(100);
		mainTable.getColumn("날짜").setPreferredWidth(100);

		/* 버튼패널 생성 */
		int btnWith = 80, btnHeight = 25; // 버튼 크기 설정
		panNorth = new JPanel();
		panNorth.setBounds(25, 480, 900, 50);
		btnWrite = new JButton("작성");
		btnWrite.setBounds(710, 10, btnWith, btnHeight);
		btnSearch = new JButton("검색");
		btnSearch.setBounds(540, 10, btnWith, btnHeight);
		btnReset = new JButton(contentSearchMod);
		btnReset.setBounds(800, 10, btnWith, btnHeight);
		searchText = new JTextField();
		searchText.setBounds(430, 10, btnWith + 20, btnHeight);
		searchCombo = new JComboBox(name);
		searchCombo.setBounds(340, 10, btnWith, btnHeight);
		countContent = new JLabel();
		countContent.setBounds(110, 10, btnWith, btnHeight);
		countContent.setEnabled(false);
		showID = new JLabel(userID);
		showID.setBounds(10, 10, btnWith, btnHeight);
		showID.setEnabled(false);

		panNorth.setLayout(null);
		panNorth.add(searchCombo);
		panNorth.add(searchText);
		panNorth.add(countContent);
		panNorth.add(showID);
		panNorth.add(btnWrite);
		panNorth.add(btnSearch);
		panNorth.add(btnReset);

		scroll = new JScrollPane(mainTable);
		scroll.setBounds(25, 30, 900, 450);

		/* 액션 추가 */
//		new myThread(Board.this).start();
		initSocket();
		new ListenThread().start();		
		
		
		btnAC.btnWrite(btnWrite);
		btnAC.btnReset(btnReset);
		btnAC.btnSearch(btnSearch);
		btnAC.tableClick(mainTable);

		panTotal = new JPanel();
		panTotal.setLayout(null);
		panTotal.add(scroll);
		panTotal.add(panNorth);

	}
	
	public void initSocket() {
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 5555);
		} catch (IOException e) {
			System.out.println("서버와 연결 설정 오류");
			e.printStackTrace();
		}
	}
	

	/* 게시판 초기화 && 목록보기 메소드 */
	public void setCount() { // 게시글 수
		countContent.setText("게시물 : " + count + "개");
		// countContent.setForeground(Color.black); // setEnabled로인해 색이 안들어간다..
	}

	public void resetContent() { // 테이블 초기화
		model = (DefaultTableModel) mainTable.getModel();
		model.setNumRows(0);
	}

	public void showContent() { // 테이블 목록보기
		Vector<BoardDTO> boardVector = dao.showBoardVector();
		count = 0;
		for (BoardDTO b : boardVector) {
			int num = b.getNum();
			String id = b.getId();
			String title = b.getTitle();
			String date = b.getDate();

			userRow = new Vector<>();
			userRow.addElement(num);
			userRow.addElement(title);
			userRow.addElement(id);
			userRow.addElement(date);
			model.addRow(userRow);
			count++;
		}
	}

	public void titleContent(boolean select) { // 제목 & 이름 검색시 목록보기
		Vector<BoardDTO> boardVector = dao.TitleVector(select, searchText.getText());
		count = 0;
		for (BoardDTO b : boardVector) {
			int num = b.getNum();
			String id = b.getId();
			String title = b.getTitle();
			String date = b.getDate();

			userRow = new Vector<>();
			userRow.addElement(num);
			userRow.addElement(title);
			userRow.addElement(id);
			userRow.addElement(date);
			model.addRow(userRow);
			count++;
		}
	}

	/* 화면 갱신 스레드 클래스 */
//	class myThread extends Thread {
//		private Board bd;
//
//		public myThread(Board bd) {	//메인 프레임의 값을 받아온다
//				this.bd = bd;
//		}
//		@Override
//		public void run() {
//				while (true) {
//					if (threadStart == THREADMOD0) {	//0일때 전체 게시물 갱신 
//						bd.resetContent();
//						bd.showContent();
//						bd.setCount();
//					} else if(threadStart == THREADMOD1){	//1일때 검색한 게시물만 갱신
//						bd.resetContent();
//						bd.titleContent(select);
//						bd.setCount();
//					}
//					
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//		}
//	}
	// 화면 갱신 스레드2
	class ListenThread extends Thread{
		private BufferedReader br ;
		public ListenThread() {
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.out.println("Listen Thread 생성 오류: br확보 안됨");
				e.printStackTrace();
			}
		}
		
        @Override
        public void run() {
            try {
                while (true) {
                    String receiveMsg = br.readLine();
                    if(receiveMsg.equals("refresh")) {
                    	Board.this.resetContent();
						Board.this.showContent();
                    }
                    	
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/* 액션 클래스 */
	class btnAction {
		BoardInsertFrame bdInsert;

		public void btnWrite(JButton btnWrite) {
			btnWrite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					threadStart = THREADMOD0;
					bdInsert = new BoardInsertFrame(userID, Board.this, socket);
				
				}
			});
		}

		public void btnReset(JButton btnReset) {
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					threadStart = THREADMOD0;
					resetContent(); // 리셋
					showContent(); // 게시물초기화
					setCount();
					
				}
			});
		}

		public void btnSearch(JButton btnSearch) {
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (searchCombo.getSelectedItem().equals("제목")) {
						select = true;
					} else if (searchCombo.getSelectedItem().equals("아이디")) {
						select = false;
					}
					resetContent(); // 리셋
					titleContent(select); // 검색한 게시물 출력
					setCount();
					threadStart = THREADMOD1;
					
				}
			});
		}

		public void tableClick(JTable mainTable) {
			mainTable.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int row = mainTable.getSelectedRow();
						int col = mainTable.getSelectedColumn();
						int number = (int) mainTable.getValueAt(row, 0);
						BoardEditFrame bdEdit = new BoardEditFrame(userID, number, Board.this);
						threadStart = THREADMOD0;
					}
				}
			});
		}
	}
}
