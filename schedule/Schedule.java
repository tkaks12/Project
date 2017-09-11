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
	public JPanel panTotal = new JPanel(); // 전체 패널
	JPanel panNorth = new JPanel(); // 버튼, 월~일 전체 패널
	JPanel panSouth = new JPanel(); // 스케줄표
	JPanel panWeek = new JPanel(); // 월~일 패널
	JPanel schedule[]; // 버튼 패널
	JPanel scheduletext[]; // 리스트 패널
	JTextField txtMonth, txtYear; // 중앙상단에 년, 월 표시
	int year, month, todayYear, todayMonth; // 년, 월, 지금년도, 지금월
	JButton btnBmonth, btnAmonth, btnByear, btnAyear, btnToday; // 이번달, 다음달, 작년, 내년, 올해
	JButton[] btnDay; // 일일 버튼
	JList[] listview; // 일일 리스트
	JLabel[] label; // 버튼 라벨
	Calendar today, cal; // 캘린더에 필요한 변수
	String[] week = { "일", "월", "화", "수", "목", "금", "토" }; // 월~일에 쓰이는 값
	Font txtf = new Font("Sherif", Font.BOLD, 18);; // 중앙상단에 년,월 폰트
	String[] listviewClear = {}; // 리스트 초기화용 배열
	ScheduleButtonAction ScheduleBtAction = new ScheduleButtonAction(); // 버튼액션 객체 생성
	ScheduleDB scheduleDB = new ScheduleDB(); // DB 객체 생성
	ArrayList<ScheduleVO> scheduleList = new ArrayList<>(); // 전체 리스트 DB에서 받는용
	Vector<String>[] vec = new Vector[42]; // 리스트에 넣기 위한 용도
	JScrollPane[] listScroll = new JScrollPane[42]; // 스크롤바 추가용도

	public Schedule() {
		today = Calendar.getInstance(); // 캘린더 정보 가져오기
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;
		today.set(Calendar.YEAR, year);
		today.set(Calendar.MONTH, (month - 1));
		today.set(Calendar.DATE, 1);

		cal = Calendar.getInstance(); // 오늘 데이터 가져오기 위해 연결
		todayYear = cal.get(Calendar.YEAR);
		todayMonth = cal.get(Calendar.MONTH) + 1;

		listview = new JList[42];
		label = new JLabel[7];
		btnDay = new JButton[42];
		schedule = new JPanel[7];
		scheduletext = new JPanel[7];

		// 벡터 문자열 초기화
		for (int i = 0; i < vec.length; i++) {
			vec[i] = new Vector<String>();
		}
		// 42개의 리스트뷰, 일일버튼 초기화
		for (int i = 0; i < listview.length; i++) {
			listview[i] = new JList();
			listview[i].setBorder(BorderFactory.createLineBorder(new Color(0xBDBDBD)));
			btnDay[i] = new JButton();
			btnDay[i].setBorder(BorderFactory.createLineBorder(new Color(0xBDBDBD)));
		}
		// 상단의 버튼추가부분
		for (int i = 0; i < schedule.length; i++) {
			schedule[i] = new JPanel();
			schedule[i].setLayout(new GridLayout(1, 7));
			scheduletext[i] = new JPanel();
			scheduletext[i].setLayout(new GridLayout(1, 7));
		}
		// 월~일 초기화
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
		panNorth.add(txtYear = new JTextField(year + "년", 4));
		panNorth.add(txtMonth = new JTextField(month + "월", 3));
		panNorth.add(btnAmonth = new JButton(">"));
		panNorth.add(btnAyear = new JButton(">>"));

		txtYear.setHorizontalAlignment(txtYear.CENTER);
		txtMonth.setHorizontalAlignment(txtMonth.CENTER);
		txtYear.setFont(txtf);
		txtMonth.setFont(txtf);
		txtYear.setEnabled(false);
		txtMonth.setEnabled(false);

		// 일요일 색상
		for (int i = 0; i < btnDay.length; i += 7) {
			btnDay[i].setForeground(Color.RED);
		}
		// 토요일 색상
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

		// Total판넬에 추가
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

		// 화면에 버튼과 스케줄 DB내용 셋팅
		schedulesetting();
		scheduleGetAllDate();
	}

	public void scheduleGetAllDate() { // listview초기화
		for (int i = 0; i < listview.length; i++) { // 0부터 listview 크기까지 반복
			listview[i].setListData(listviewClear); // listview 초기화
			vec[i].removeAllElements(); // vector 초기화
		}
		scheduleList = scheduleDB.ScheduleGetAll(year, month, "id"); // getAll에 db 연결
		for (ScheduleVO scVO : scheduleList) {
			String strDate = scVO.getDate();
			// db에 저장된 day값만을 추출
			int listPoint = Integer.parseInt(strDate.substring((strDate.lastIndexOf("-") + 1), strDate.length()));
			// 벡터에 1일 시작하는 위치 + 위에서 추출한 일에다가 값을 저장
			vec[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]
					.addElement("<html>" + scVO.getHour() + "시 " + scVO.getMin() + "분 / " + scVO.getContent()
							+ "<span hidden style=\"color:red\"> 　　　　　　　　　　　" + scVO.getSeed() + "</span></html>");
			// 벡터와 같은 위치에 벡터 데이터 추가
			listview[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]
					.setListData(vec[((today.get(Calendar.DAY_OF_WEEK) - 2) + listPoint)]);
		}
	}

	public void schedulesetting() { // 스크롤안에 텍스트 붙여준다
		for (int i = 0; i < btnDay.length; i++) { // 버튼 갯수인 0~41까지 반복
			if ((i / 7) == 0) {
				listScroll[i] = new JScrollPane(listview[i]);
				listScroll[i].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 좌우스크롤 막기
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
		// 스케줄 셋팅후 바로 버튼셋팅수행
		btnSetting();
	}

	public void btnSetting() {
		int dayCount = 1; // 날짜 일수 카운터
		for (int i = 0; i < (today.get(Calendar.DAY_OF_WEEK) - 1); i++) { // 1일 이전 공백
			btnDay[i].setText("");
			btnDay[i].setEnabled(false);
			listview[i].setEnabled(false);
			listview[i].setBackground(null);
		}
		for (int i = (today.get(Calendar.DAY_OF_WEEK) - 1); i < (today.getActualMaximum(Calendar.DATE) // 1일~마지막일 세팅
				+ (today.get(Calendar.DAY_OF_WEEK) - 1)); i++) {
			btnDay[i].setText(dayCount + "");
			dayCount++;
			btnDay[i].setEnabled(true);
			listview[i].setEnabled(true);
			listview[i].setBackground(new Color(0xE4F7BA));

		}
		for (int i = (today.getActualMaximum(Calendar.DATE)
				+ (today.get(Calendar.DAY_OF_WEEK) - 1)); i < btnDay.length; i++) { // 말일 부터 마지막까지 공백
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
					public void mousePressed(MouseEvent e) { // 클릭한 부분 초기화
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
						if (e.getClickCount() == 2) { // 더블클릭
							JList listviewselected = (JList) e.getSource();

							if (listviewselected.isSelectionEmpty()) {
								// 리스트가 비었을때 아무 동작도 안함
							} else { // 리스트에 값이 있을때 서브프레임 실행
								int y = 0;

								for (y = 0; y < listview.length; y++) { // 선택한 listview의 배열 위치 찾기
									if (listviewselected == listview[y]) {
										break;
									}
								}
								// 선택한 데이터에서 시간 추출
								int edithour = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf(">") + 1,
										listview[y].getSelectedValue().toString().indexOf("시")));
								// 선택한 데이터에서 분 추출
								int editmin = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf("시") + 2,
										listview[y].getSelectedValue().toString().indexOf("분")));
								// 선택한 데이터에서 내용 추출
								String editcontent = listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().indexOf("분") + 4,
										listview[y].getSelectedValue().toString().lastIndexOf("<s"));
								// 선택한 데이터에서 seed 추출
								int seed = Integer.parseInt(listview[y].getSelectedValue().toString().substring(
										listview[y].getSelectedValue().toString().lastIndexOf("　　") + 2,
										listview[y].getSelectedValue().toString().lastIndexOf("</s")));
								// eidt 프레임객체 생성하면서 데이터 전송
								scheduleEdit = new ScheduleEditFrame("id",
										year + "-" + month + "-" + btnDay[y].getText(), edithour, editmin, editcontent,
										seed, Schedule.this);
							}
						}
					}
				});
			}
		}

		public void btnDay(JButton[] btnDay) { // 버튼 클릭
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

					today.set(Calendar.YEAR, year); // 올해로 셋팅
					today.set(Calendar.MONTH, (month - 1)); // 요번 달로 셋팅
					today.set(Calendar.DATE, 1); // 1일로 셋팅
					txtMonth.setText(month + "월");
					txtYear.setText(year + "년");
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
					txtMonth.setText(month + "월");
					txtYear.setText(year + "년");
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
					// set : 해당월의 1일로 이동 & add : 해당월의 일로 이동
					// today.add(Calendar.YEAR, yearAdd);
					// today.add(Calendar.MONTH, +1);
					today.set(Calendar.YEAR, year);
					today.set(Calendar.MONTH, (month - 1));
					today.set(Calendar.DATE, 1);
					txtMonth.setText(month + "월");
					txtYear.setText(year + "년");
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
					txtYear.setText(year + "년");
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
					txtYear.setText(year + "년");
					btnSetting();
					scheduleGetAllDate();
					for (int x = 0; x < listview.length; x++)
						listview[x].clearSelection();
				}
			});
		}
	}

}