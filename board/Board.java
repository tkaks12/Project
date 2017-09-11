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
	private JTable mainTable; // ���� ���̺�
	private JScrollPane scroll; // ���̺� ��ũ��
	public JPanel panTotal; // ��ü ȭ�� ��
	private JTextField searchText; // �˻�
	private JLabel countContent, showID;
	private JButton btnWrite, btnSearch, btnReset; // ����&�˻� ��ư
	private JPanel panNorth; // ��ư&�˻� ��
	private Vector userRow; // ���̺� �߰��Ǵ� �Խñ�
	private btnAction btnAC = new btnAction(); // ��ư�׼�
	private BoardDTO board = null;
	private BoardDao dao = new BoardDao();
	private JComboBox searchCombo; // �˻� �޺��ڽ�
	private int count = 0;
	private String[] name = { "����", "���̵�" }; // �޺��ڽ� ������
	private String titleMain[] = { "No", "����", "���̵�", "��¥" };
	private DefaultTableModel model = new DefaultTableModel(titleMain, 0) { // table�� ���� ����
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	private String contentSearchMod = "�⺻";
	private String userID;
	final static int THREADMOD0 = 0;	
	final static int THREADMOD1	= 1;
	final static int THREADMOD2	= 2;	//������ ��� ���� 
	private int threadStart = THREADMOD0; // 0�϶� ������ ����
	private boolean select = true;

	// �Խ��� ���� ����
	private Socket socket = null;
	/* ������ ���� */
	public Board(String id) {
		this.userID = id;
		mainTable = new JTable(model);
		mainTable.getTableHeader().setReorderingAllowed(false);// Ÿ��Ʋ ��ġ ���ٲٰ��ϱ�
		mainTable.getColumn("No").setPreferredWidth(100);
		mainTable.getColumn("����").setPreferredWidth(570);
		mainTable.getColumn("���̵�").setPreferredWidth(100);
		mainTable.getColumn("��¥").setPreferredWidth(100);

		/* ��ư�г� ���� */
		int btnWith = 80, btnHeight = 25; // ��ư ũ�� ����
		panNorth = new JPanel();
		panNorth.setBounds(25, 480, 900, 50);
		btnWrite = new JButton("�ۼ�");
		btnWrite.setBounds(710, 10, btnWith, btnHeight);
		btnSearch = new JButton("�˻�");
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

		/* �׼� �߰� */
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
			System.out.println("������ ���� ���� ����");
			e.printStackTrace();
		}
	}
	

	/* �Խ��� �ʱ�ȭ && ��Ϻ��� �޼ҵ� */
	public void setCount() { // �Խñ� ��
		countContent.setText("�Խù� : " + count + "��");
		// countContent.setForeground(Color.black); // setEnabled������ ���� �ȵ���..
	}

	public void resetContent() { // ���̺� �ʱ�ȭ
		model = (DefaultTableModel) mainTable.getModel();
		model.setNumRows(0);
	}

	public void showContent() { // ���̺� ��Ϻ���
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

	public void titleContent(boolean select) { // ���� & �̸� �˻��� ��Ϻ���
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

	/* ȭ�� ���� ������ Ŭ���� */
//	class myThread extends Thread {
//		private Board bd;
//
//		public myThread(Board bd) {	//���� �������� ���� �޾ƿ´�
//				this.bd = bd;
//		}
//		@Override
//		public void run() {
//				while (true) {
//					if (threadStart == THREADMOD0) {	//0�϶� ��ü �Խù� ���� 
//						bd.resetContent();
//						bd.showContent();
//						bd.setCount();
//					} else if(threadStart == THREADMOD1){	//1�϶� �˻��� �Խù��� ����
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
	// ȭ�� ���� ������2
	class ListenThread extends Thread{
		private BufferedReader br ;
		public ListenThread() {
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.out.println("Listen Thread ���� ����: brȮ�� �ȵ�");
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

	/* �׼� Ŭ���� */
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
					resetContent(); // ����
					showContent(); // �Խù��ʱ�ȭ
					setCount();
					
				}
			});
		}

		public void btnSearch(JButton btnSearch) {
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (searchCombo.getSelectedItem().equals("����")) {
						select = true;
					} else if (searchCombo.getSelectedItem().equals("���̵�")) {
						select = false;
					}
					resetContent(); // ����
					titleContent(select); // �˻��� �Խù� ���
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
