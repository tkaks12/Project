package schedule;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ScheduleInsertFrame extends JFrame {
	JPanel panelTime, panelContent, panelBtn, panelTotal;
	JLabel labelTime, labelContent, labelHour, labelMin;
	JTextArea txtArea;
	JButton btnYes, btnNo;
	JScrollPane scrollPane;
	JComboBox comboHour, comboMin;
	btnAction btnAction = new btnAction();
	String id, date, content;
	int hour, min;
	ScheduleDB scheduleDB = new ScheduleDB();
	Schedule sc;
	
	public ScheduleInsertFrame(String id, String date, Schedule sc) {
		this.id = id;
		this.date = date;
		this.sc = sc;	//Schedule의 주소값을 받아온다
		
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

		// 시간,분 콤보버튼 추가
		for (int hour = 0; hour < 24; hour++) {
			comboHour.addItem(hour);
		}
		for (int min = 0; min < 60; min++) {
			comboMin.addItem(min);
		}

		labelTime = new JLabel("시간");
		labelHour = new JLabel("시");
		labelMin = new JLabel("분");
		labelContent = new JLabel("내용");

		txtArea = new JTextArea();
		txtArea.setLineWrap(true);

		btnYes = new JButton("확인");
		btnNo = new JButton("취소");

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
		btnYes.setSize(100, 30);
		btnYes.setLocation(90, 0);
		btnNo.setSize(100, 30);
		btnNo.setLocation(210, 0);

		panelTime.add(labelTime);
		panelTime.add(labelHour);
		panelTime.add(labelMin);
		panelTime.add(comboHour);
		panelTime.add(comboMin);

		panelContent.add(labelContent);
		panelContent.add(scrollPane);

		panelBtn.add(btnYes);
		panelBtn.add(btnNo);

		panelTotal.add(panelTime);
		panelTotal.add(panelContent);
		panelTotal.add(panelBtn);

		add(panelTotal);

		btnAction.btnYesAction(btnYes);
		btnAction.btnNoAction(btnNo);
//		btnAction.comboHourAction(comboHour);
//		btnAction.comboMinAction(comboMin);

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
					int result = scheduleDB.ScheduleInsert(id, date, hour, min, content);
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
