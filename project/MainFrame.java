package project;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import schedule.*;
import board.*;

public class MainFrame extends JFrame {
	String id;

	public MainFrame(String id) {
		this.id = id;
		setTitle("4팀");
		createTabbedPane();
		setSize(960, 620);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void createTabbedPane() {
		// TODO Auto-generated method stub
		// tab생성
		JTabbedPane pane = new JTabbedPane();
		add(pane);

		// 게시판
		Board board = new Board(id);
		pane.addTab("<html><body><table width='210'>Bulletin Board</table></body></html>", board.panTotal);

		// 스케쥴
		Schedule schedule = new Schedule();
		pane.addTab("<html><body><table width='210'>Schedule</table></body></html>", schedule.panTotal);

		// "<html><body><table width='150'>Other</table></body></html>"
		// 채팅
		Chatting chat = new Chatting();
		pane.addTab("<html><body><table width='210'>Chatting</table></body></html>", chat.getContentPane());

		// 다른 것
		Other other = new Other();
		pane.addTab("<html><body><table width='210'>Other</table></body></html>", other.getContentPane());

	}

	// public static void main(String[] args) {
	// new MainFrame();
	// }

}