package schedule;

 
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * CREATE TABLE schedule(id VARCHAR(45), date VARCHAR(45), hour INT, min INT, content VARCHAR(1000), seed INT AUTO_INCREMENT PRIMARY KEY);
 *
 */
public class Schedule extends JPanel {
	public JPanel panTotal = new JPanel(); // ��ü �г�
	JPanel panNorth = new JPanel(); // ��ư, ��~�� ��ü �г�
	JPanel panSouth = new JPanel(); // ������ǥ
	JPanel panWeek = new JPanel(); // ��~�� �г�
	JPanel schedule[]; // ��ư �г�
	JPanel scheduletext[]; // ����Ʈ �г�
	JTextField txtMonth, txtYear; // �߾ӻ�ܿ� ��, �� ǥ��
	int year, month, todayYear, todayMonth; // ��, ��, ���ݳ⵵, ���ݿ�
	JButton btnBmonth, btnAmonth, btnByear, btnAyear, btnToday; // �̹���, ������, �۳�, ����, ����
	JButton[] btnDay; // ���� ��ư
	JList[] listview; // ���� ����Ʈ
	JLabel[] label; // ��ư ��
	Calendar today, cal; // Ķ������ �ʿ��� ����
	String[] week = { "��", "��", "ȭ", "��", "��", "��", "��" }; // ��~�Ͽ� ���̴� ��
	Font txtf = new Font("Sherif", Font.BOLD, 18);; // �߾ӻ�ܿ� ��,�� ��Ʈ
	String[] listviewClear = {}; // ����Ʈ �ʱ�ȭ�� �迭
	ScheduleButtonAction ScheduleBtAction = new ScheduleButtonAction(); // ��ư�׼� ��ü ����
	ScheduleDB scheduleDB = new ScheduleDB(); // DB ��ü ����
	ArrayList<ScheduleVO> scheduleList = new ArrayList<>(); // ��ü ����Ʈ DB���� �޴¿�
	Vector<String>[] vec = new Vector[42]; // ����Ʈ�� �ֱ� ���� �뵵
	JScrollPane[] listScroll = new JScrollPane[42]; // ��ũ�ѹ� �߰��뵵

	public Schedule() {
		today = Calendar.getInstance(); // Ķ���� ���� ��������
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;
		today.set(Calendar.YEAR, year);
		today.set(Calendar.MONTH, (month - 1));
		today.set(Calendar.DATE, 1);

		cal = Calendar.getInstance(); // ���� ������ �������� ���� ����
		todayYear = cal.get(Calendar.YEAR);
		todayMonth = cal.get(Calendar.MONTH) + 1;

		listview = new JList[42];
		label = new JLabel[7];
		btnDay = new JButton[42];
		schedule = new JPanel[7];
		scheduletext = new JPanel[7];

		// ���� ���ڿ� �ʱ�ȭ
		for (int i = 0; i < vec.length; i++) {
			vec[i] = new Vector<String>();
		}
		// 42���� ����Ʈ��, ���Ϲ�ư �ʱ�ȭ
		for (int i = 0; i < listview.length; i++) {
			listview[i] = new JList();
			listview[i].setBorder(BorderFactory.createLineBorder(new Color(0xBDBDBD)));
			btnDay[i] = new JButton();
			btnDay[i].setBorder(BorderFactory.createLineBorder(new Color(0xBDBDBD)));
		}
		// ����� ��ư�߰��κ�
		for (int i = 0; i < schedule.length; i++) {
			schedule[i] = new JPanel();
			schedule[i].setLayout(new GridLayout(1, 7));
			scheduletext[i] = new JPanel();
			scheduletext[i].setLayout(new GridLayout(1, 7));
		}
		// ��~�� �ʱ�ȭ
		for (int i = 0; i < week.length; i++) {
			label[i] = new JLabel(week[i]);
			label[i].setHorizontalAlignment(label[i].CENTER);
			panWeek.add(label[i]);
		}

		label[0].setForeground(Color.RED);
		label[6].setForeground(Color.BLUE);

		panTotal.setLayout(null);
		panNorth.setLayout(null);
		panSouth.setLayout(new GridLayout(12, 6));
		panWeek.setLayout(new GridLayout(1, 7));

		panNorth.add(btnToday = new JButton("today"));
		panNorth.add(btnByear = new JButton("<<"));
		panNorth.add(btnBmonth = new JButton("<"));
		panNorth.add(txtYear = new JTextField(year + "��", 4));
		panNorth.add(txtMonth = new JTextField(month + "��", 3));
		panNorth.add(btnAmonth = new JButton(">"));
		panNorth.add(btnAyear = new JButton(">>"));

		txtYear.setHorizontalAlignment(txtYear.CENTER);
		txtMonth.setHorizontalAlignment(txtMonth.CENTER);
		txtYear.setFont(txtf);
		txtMonth.setFont(txtf);
		txtYear.setEnabled(false);
		txtMonth.setEnabled(false);

		// �Ͽ��� ����
		for (int i = 0; i < btnDay.length; i += 7) {
			btnDay[i].setForeground(Color.RED);
		}
		// ����� ����
		for (int i = 6; i < btnDay.length; i += 7) {
			btnDay[i].setForeground(Color.BLUE);
		}

		int northWidth = 80, northHeight = 25;

		btnToday.setSize(70, 20);
		btnToday.setLocation(10, 10);
		btnByear.setSize(northWidth, northHeight);
		btnByear.setLocation(140, 10);
		btnBmonth.setSize(northWidth, northHeight);
		btnBmonth.setLocation(260, 10);
		btnAyear.setSize(northWidth, northHeight);
		btnAyear.setLocation(700, 10);
		btnAmonth.setSize(northWidth, northHeight);
		btnAmonth.setLocation(580, 10);

		txtYear.setSize(northWidth, northHeight);
		txtYear.setLocation(380, 10);
		txtMonth.setSize(northWidth, northHeight);
		txtMonth.setLocation(460, 10);

		int scWidth = 950, scHeight = 20;
		int sctxtHeight = 60;

		schedule[0].setSize(scWidth, scHeight);
		schedule[0].setLocation(0, 70);
		scheduletext[0].setSize(scWidth, sctxtHeight);
		scheduletext[0].setLocation(0, 90);
		schedule[1].setSize(scWidth, scHeight);
		schedule[1].setLocation(0, 150);
		scheduletext[1].setSize(scWidth, sctxtHeight);
		scheduletext[1].setLocation(0, 170);
		schedule[2].setSize(scWidth, scHeight);
		schedule[2].setLocation(0, 230);
		scheduletext[2].setSize(scWidth, sctxtHeight);
		scheduletext[2].setLocation(0, 250);
		schedule[3].setSize(scWidth, scHeight);
		schedule[3].setLocation(0, 310);
		scheduletext[3].setSize(scWidth, sctxtHeight);
		scheduletext[3].setLocation(0, 330);
		schedule[4].setSize(scWidth, scHeight);
		schedule[4].setLocation(0, 390);
		scheduletext[4].setSize(scWidth, sctxtHeight);
		scheduletext[4].setLocation(0, 410);
		schedule[5].setSize(scWidth, scHeight);
		schedule[5].setLocation(0, 470);
		scheduletext[5].setSize(scWidth, sctxtHeight);
		scheduletext[5].setLocation(0, 490);

		panWeek.setSize(scWidth, 15);
		panWeek.setLocation(0, 45);
		panNorth.setSize(scWidth, 40);
		panNorth.setLocation(0, 0);

		// Total�ǳڿ� �߰�
		for (int i = 0; i < 6; i++) {
			panTotal.add(schedule[i]);
			panTotal.add(scheduletext[i]);
		}
		panTotal.add(panNorth);
		panTotal.add(panWeek);

		ScheduleBtAction.btnBmonth(btnBmonth);
		ScheduleBtAction.btnAmonth(btnAmonth);
		ScheduleBtAction.btnAyear(btnAyear);
		ScheduleBtAction.btnByear(btnByear);
		ScheduleBtAction.btnDay(btnDay);
		ScheduleBtAction.btnToday(btnToday);
		ScheduleBtAction.listviewDoubleClicked(listview);

		// ȭ�鿡 ��ư�� ������ DB���� ����
		schedulesetting();
		scheduleGetAllDate();
	}

	public void scheduleGetAllDate() { // listview�ʱ�ȭ
		for (int i = 0; i < listview.length; i++) { // 0���� listview ũ����� �ݺ�
			listview[i].setListData(listviewClear); // listview �ʱ�ȭ
			vec[i].removeAllElements(); // vector �ʱ�ȭ
		}
		scheduleList = scheduleDB.ScheduleGetAll(year, month, "id"); // getAll�� db ����
		for (ScheduleVO scVO : scheduleList) {
			String strDate = scVO.getDate();
			// db�� ����� day������ ����
			int listPoint = Integer.parseInt(strDate.substring((strDate.lastIndexOf("-") + 1), strDate.length()));
			// ���Ϳ� 1�� �����ϴ� ��ġ + ������ ������ �Ͽ��ٰ� ���� ����
			vec[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]
					.addElement("<html>" + scVO.getHour() + "�� " + scVO.getMin() + "�� / " + scVO.getContent()
							+ "<span hidden style=\"color:red\"> ����������������������" + scVO.getSeed() + "</span></html>");
			// ���Ϳ� ���� ��ġ�� ���� ������ �߰�
			listview[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]
					.setListData(vec[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]);
		}
	}

	public void schedulesetting() { // ��ũ�Ѿȿ� �ؽ�Ʈ �ٿ��ش�
		for (int i = 0; i < btnDay.length; i++) { // ��ư ������ 0~41���� �ݺ�
			if ((i / 7) == 0) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // �¿콺ũ�� ����
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 1) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 2) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 3) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 4) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 5) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			} else if ((i / 7) == 6) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scheduletext[(i / 7)].add(listScroll[i]);
				schedule[(i / 7)].add(btnDay[i]);
			}
		}
		// ������ ������ �ٷ� ��ư���ü���
		btnSetting();
	}

	public void btnSetting() {
		int dayCount = 1; // ��¥ �ϼ� ī����
		for (int i = 0; i < (today.get(Calendar.DAY_OF_WEEK) - 1); i++) { // 1�� ���� ����
			btnDay[i].setText("");
			btnDay[i].setEnabled(false);
			listview[i].setEnabled(false);
			listview[i].setBackground(null);
		}
		for (int i = (today.get(Calendar.DAY_OF_WEEK) - 1); i < (today.getActualMaximum(Calendar.DATE) // 1��~�������� ����
				+ (today.get(Calendar.DAY_OF_WEEK) - 1)); i++) {
			btnDay[i].setText(dayCount + "");
			dayCount++;
			btnDay[i].setEnabled(true);
			listview[i].setEnabled(true);
			listview[i].setBackground(new Color(0xE4F7BA));

		}
		for (int i = (today.getActualMaximum(Calendar.DATE)
				+ (today.get(Calendar.DAY_OF_WEEK) - 1)); i < btnDay.length; i++) { // ���� ���� ���������� ����
			btnDay[i].setText("");
			btnDay[i].setEnabled(false);
			listview[i].setEnabled(false);
			listview[i].setBackground(null);
		}
	}

	class ScheduleButtonAction {
		ScheduleInsertFrame scheduleInsert;
		ScheduleEditFrame scheduleEdit;

		public void listviewDoubleClicked(JList[] listview) {
			for (int i = 0; i < 42; i++) {
				listview[i].addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mousePressed(MouseEvent e) { // Ŭ���� �κ� �ʱ�ȭ
						JList listviewChoice = (JList) e.getSource();
						int y = 0;
						for (y = 0; y < listview.length; y++) {
							if (listviewChoice != listview[y]) {
								listview[y].clearSelection();
							}
						}
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) { // ����Ŭ��
							JList listviewselected = (JList) e.getSource();

							if (listviewselected.isSelectionEmpty()) {
								// ����Ʈ�� ������� �ƹ� ���۵� ����
							} else { // ����Ʈ�� ���� ������ ���������� ����
								int y = 0;

								for (y = 0; y < listview.length; y++) { // ������ listview�� �迭 ��ġ ã��
									if (listviewselected == listview[y]) {
										break;
									}
								}
								// ������ �����Ϳ��� �ð� ����
								int edithour = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf(">") + 1,
										listview[y].getSelectedValue().toString().indexOf("��")));
								// ������ �����Ϳ��� �� ����
								int editmin = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf("��") + 2,
										listview[y].getSelectedValue().toString().indexOf("��")));
								// ������ �����Ϳ��� ���� ����
								String editcontent = listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf("��") + 4,
										listview[y].getSelectedValue().toString().lastIndexOf("<s"));
								// ������ �����Ϳ��� seed ����
								int seed = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().lastIndexOf("����") + 2,
										listview[y].getSelectedValue().toString().lastIndexOf("</s")));
								// eidt �����Ӱ�ü �����ϸ鼭 ������ ����
								scheduleEdit = new ScheduleEditFrame("id",
										year + "-" + month + "-" + btnDay[y].getText(), edithour, editmin, editcontent,
										seed, Schedule.this);
							}
						}
					}
				});
			}
		}

		public void btnDay(JButton[] btnDay) { // ��ư Ŭ��
			for (int i = 0; i < 42; i++) {
				btnDay[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton selected = (JButton) e.getSource();
						if (selected.getText() != "") {
							scheduleInsert = new ScheduleInsertFrame("id",
									year + "-" + month + "-" + selected.getText(), Schedule.this);
						}
					}
				});
			}
		}

		public void btnToday(JButton btnToday) {
			btnToday.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					year = todayYear;
					month = todayMonth;

					today.set(Calendar.YEAR, year); // ���ط� ����
					today.set(Calendar.MONTH, (month - 1)); // ��� �޷� ����
					today.set(Calendar.DATE, 1); // 1�Ϸ� ����
					txtMonth.setText(month + "��");
					txtYear.setText(year + "��");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}

		public void btnBmonth(JButton btnBmonth) {
			btnBmonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					month -= 1;
					if (month < 1) {
						month = month + 12;
						year -= 1;
					}
					today.set(Calendar.YEAR, year);
					today.set(Calendar.MONTH, (month - 1));
					today.set(Calendar.DATE, 1);
					txtMonth.setText(month + "��");
					txtYear.setText(year + "��");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}

		public void btnAmonth(JButton btnAmonth) {
			btnAmonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					month += 1;
					if (month > 12) {
						month = month % 12;
						year += 1;
					}
					// set : �ش���� 1�Ϸ� �̵� & add : �ش���� �Ϸ� �̵�
					// today.add(Calendar.YEAR, yearAdd);
					// today.add(Calendar.MONTH, +1);
					today.set(Calendar.YEAR, year);
					today.set(Calendar.MONTH, (month - 1));
					today.set(Calendar.DATE, 1);
					txtMonth.setText(month + "��");
					txtYear.setText(year + "��");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}

		public void btnByear(JButton btnByear) {
			btnByear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					year -= 1;
					today.set(Calendar.YEAR, year);
					today.set(Calendar.MONTH, (month - 1));
					today.set(Calendar.DATE, 1);
					txtYear.setText(year + "��");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}

		public void btnAyear(JButton btnAyear) {
			btnAyear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					year += 1;

					today.set(Calendar.YEAR, year);
					today.set(Calendar.MONTH, (month - 1));
					today.set(Calendar.DATE, 1);
					txtYear.setText(year + "��");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}
	}

}