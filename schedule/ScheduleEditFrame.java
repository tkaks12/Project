package schedule;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScheduleEditFrame extends JFrame {
	JPanel panelTime, panelContent, panelBtn, panelTotal;
	JLabel labelTime, labelContent, labelHour, labelMin;
	JTextArea txtArea;
	JButton btnYes, btnNo, btnDel;
	JScrollPane scrollPane;
	JComboBox comboHour, comboMin;
	btnAction btnAction = new btnAction();
	String id, date, content;
	int hour, min, seed;
	ScheduleDB scheduleDB = new ScheduleDB();
	Schedule sc;
	
	public ScheduleEditFrame(String id, String date, int hour, int min, String content, int seed, Schedule sc) {
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.content = content;
		this.seed = seed;
		this.sc = sc;	//schedule프레임의 주소값을 넘겨받음
		panelTime = new JPanel();
		panelContent = new JPanel();
		panelBtn = new JPanel();
		panelTotal = new JPanel();

		panelTime.setLayout(null);
		panelContent.setLayout(null);
		panelBtn.setLayout(null);
		panelTotal.setLayout(null);

		comboHour = new JComboBox();
		comboMin = new JComboBox();

		// 24시,60분 콤보박스 생성
		for (int i = 0; i < 24; i++) {
			comboHour.addItem(i);
		}
		for (int i = 0; i < 60; i++) {
			comboMin.addItem(i);
		}
		
		comboHour.setSelectedItem(hour);
		comboMin.setSelectedItem(min);

		labelTime = new JLabel("시간");
		labelHour = new JLabel("시");
		labelMin = new JLabel("분");
		labelContent = new JLabel("내용");

		txtArea = new JTextArea(content);
		txtArea.setLineWrap(true);

		btnYes = new JButton("확인");
		btnNo = new JButton("취소");
		btnDel = new JButton("삭제");

		scrollPane = new JScrollPane(txtArea);

		panelTime.setSize(400, 50);
		panelTime.setLocation(0, 0);
		labelTime.setFont(new Font("Sherif", Font.BOLD, 16));
		labelTime.setSize(100, 30);
		labelTime.setLocation(30, 15);
		labelHour.setFont(new Font("Sherif", Font.BOLD, 16));
		labelHour.setSize(100, 30);
		labelHour.setLocation(140, 15);
		labelMin.setFont(new Font("Sherif", Font.BOLD, 16));
		labelMin.setSize(100, 30);
		labelMin.setLocation(240, 15);
		comboHour.setFont(new Font("Sherif", Font.BOLD, 16));
		comboHour.setSize(50, 30);
		comboHour.setLocation(80, 15);
		comboMin.setFont(new Font("Sherif", Font.BOLD, 16));
		comboMin.setSize(50, 30);
		comboMin.setLocation(180, 15);

		panelContent.setSize(400, 100);
		panelContent.setLocation(0, 50);
		labelContent.setFont(new Font("Sherif", Font.BOLD, 16));
		labelContent.setSize(100, 30);
		labelContent.setLocation(30, 5);
		scrollPane.setSize(300, 80);
		scrollPane.setLocation(80, 10);
		scrollPane.setAutoscrolls(true);

		panelBtn.setSize(400, 50);
		panelBtn.setLocation(0, 150);
		btnYes.setSize(80, 30);
		btnYes.setLocation(70, 0);
		btnNo.setSize(80, 30);
		btnNo.setLocation(170, 0);
		btnDel.setSize(80, 30);
		btnDel.setLocation(270, 0);

		panelTime.add(labelTime);
		panelTime.add(labelHour);
		panelTime.add(labelMin);
		panelTime.add(comboHour);
		panelTime.add(comboMin);

		panelContent.add(labelContent);
		panelContent.add(scrollPane);

		panelBtn.add(btnYes);
		panelBtn.add(btnNo);
		panelBtn.add(btnDel);

		panelTotal.add(panelTime);
		panelTotal.add(panelContent);
		panelTotal.add(panelBtn);

		add(panelTotal);

		btnAction.btnYesAction(btnYes);
		btnAction.btnNoAction(btnNo);
//		btnAction.comboHourAction(comboHour);
//		btnAction.comboMinAction(comboMin);
		btnAction.btnDeletActino(btnDel);

		setResizable(false);
		setSize(400, 220);
		setVisible(true);
	}

	class btnAction {
		public void btnYesAction(JButton btnYes) {
			btnYes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hour = (int) comboHour.getSelectedItem();
					min = (int) comboMin.getSelectedItem();
					content = txtArea.getText();
					int result = scheduleDB.ScheduleEdit(id, date, hour, min, content, seed);
					if (result == 1) {
						dispose();
						sc.scheduleGetAllDate();
					}
					
				}
			});
		}

		public void btnDeletActino(JButton btnDel) {
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = scheduleDB.ScheduleDel(seed);
					if (result == 1) {
						dispose();
						sc.scheduleGetAllDate();
					}
				}
			});
		}

		public void btnNoAction(JButton btnNo) {
			btnNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}

//		public void comboHourAction(JComboBox comboHour) {
//			comboHour.addItemListener(new ItemListener() {
//				@Override
//				public void itemStateChanged(ItemEvent e) {
//					JComboBox combo = (JComboBox) e.getSource();
//					if ((int) combo.getSelectedItem() < 0) {
//						combo.setSelectedItem(0);
//					} else if ((int) combo.getSelectedItem() > 23) {
//						combo.setSelectedItem(23);
//					}
//				}
//			});
//		}
//
//		public void comboMinAction(JComboBox comboMin) {
//			comboMin.addItemListener(new ItemListener() {
//				@Override
//				public void itemStateChanged(ItemEvent e) {
//					JComboBox combo = (JComboBox) e.getSource();
//					if ((int) combo.getSelectedItem() < 0) {
//						combo.setSelectedItem(0);
//					} else if ((int) combo.getSelectedItem() > 59) {
//						combo.setSelectedItem(59);
//					}
//				}
//			});
//		}

	}
}
