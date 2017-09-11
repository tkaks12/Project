package board;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BoardServer {
	private ServerSocket serverSocket;
	private List<ChattingThread> threadList;

	// 서버 생성자
	public BoardServer() {
		threadList = new ArrayList<>();

		try {
			serverSocket = new ServerSocket(5555);
			while (true) {
				System.out.println("클라이언트를 기다리는 중..");
				Socket socket = serverSocket.accept();
				System.out.println("접속함:" + socket.getInetAddress());

				// 새로운 클라이언트 접속하면 새로운
				// 쓰레드 객체를 생성해서 리스트에 추가함.
				ChattingThread t = new ChattingThread(socket);
				threadList.add(t);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 서버의 리스트에 있는 모든 쓰레드에게 메세지 발송 명령해서
	// 모든 클라이언트에게 메세지 방송하기 메소드
	public void broadcast(String msg) {
		for (ChattingThread t : threadList) {
			t.speak(msg);
		}
	}

	// 쓰레드 목록에서 특정 쓰레드 삭제하기
	public void removeThread(ChattingThread t) {
		threadList.remove(t);
	}

	// 하나의 클라이언트가 접속했을 때 담당 쓰레드 클래스
	class ChattingThread extends Thread {
		private BufferedReader br;
		private BufferedWriter bw;

		public ChattingThread(Socket socket) {
			// 서버로부터 해당 클라이언트 소켓 전달받아서
			// 채팅 초기화 작업 수행하기
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					String msg = br.readLine();
					System.out.println("서버쪽에서 쓰레드 실행했어");
					if(msg.equals("refresh"))
						broadcast(msg);
				}
			} catch (IOException e) {
				// 담당 클라이언트가 퇴장했을 때
				removeThread(this);
				// e.printStackTrace();
			}
		}

		// 현재 쓰레드가 담당하는 클라이언트에게 메세지 보내기
		public void speak(String msg) {
			try {
				bw.write(msg + "\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		BoardServer server = new BoardServer();
	}
}
