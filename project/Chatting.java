package project;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Chatting extends JFrame {
	JTable table1, table2;
	DefaultTableModel model1, model2;
	JTextPane pane;
	JTextField tf;
	JButton b1, b2, b3, b4, b5, b6;
	JScrollBar bar;

	public Chatting() {
		String col1[] = { "방이름", "인원" };
		String row1[][] = new String[0][2];
		model1 = new DefaultTableModel(row1, col1) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table1 = new JTable(model1);
		JScrollPane js1 = new JScrollPane(table1);

		String col2[] = { "상태", "ID", "생년월일" };
		String row2[][] = new String[0][3];
		model2 = new DefaultTableModel(row2, col2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table1.getTableHeader().setReorderingAllowed(false);
		table2 = new JTable(model2);

		JScrollPane js2 = new JScrollPane(table2);
		table2.getTableHeader().setReorderingAllowed(false);

		tf = new JTextField(15);

		JPanel p3 = new JPanel();
		b1 = new JButton("방만들기");

		/*
		 * 필요하면 쓰기. b2 = new JButton("방들어가기"); b3 = new JButton("1:1신청"); b4 = new
		 * JButton("쪽지보내기"); b6 = new JButton("나가기");
		 * 
		 * //
		 */
		p3.setLayout(new GridLayout(5, 5, 10, 10));
		p3.add(b1);
		/*
		 * 필요하면. p3.add(b2); p3.add(b3); p3.add(b4); p3.add(b6); //
		 */

		setLayout(null);
		js1.setBounds(10, 30, 600, 370);
		js2.setBounds(620, 30, 400, 370);

		p3.setBounds(10, 450, 310, 190);
		add(js1);
		add(js2);

		add(p3);

//		setSize(970, 620);
		//setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		//new Chatting();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
}
