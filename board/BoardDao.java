package board;

//CREATE TABLE BOARD (
//NUMBER INT AUTO_INCREMENT PRIMARY KEY,
//ID VARCHAR(12),TITLE VARCHAR(200)
//CONTENTS VARCHAR(500));
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import join.JoinDao;

public class BoardDao {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
	private static final String DB_ID = "root";
	private static final String DB_PW = "sds1501";

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public BoardDao() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* DB에 추가 */
	public int BoardInsert(BoardDTO board) {
		int result = 0;
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "INSERT INTO BOARD(ID,TITLE,CONTENTS,DATE)" + " VALUES(?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, board.getId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContents());
			pstmt.setString(4, board.getDate());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/* DB 삭제 */
	public int BoardDelete(String id, int number) {
		int result = 0;
		try {
			String sql = null;
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);

			if (id.equals("운영자")) {
				sql = "DELETE FROM BOARD WHERE NUMBER = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, number);
				result = pstmt.executeUpdate();

			} else {
				sql = "DELETE FROM BOARD WHERE ID = ? and NUMBER = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, number);
				result = pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/* DB 업데이트 */
	public int BoardUpdate(String id, String title, String content, String date, int number) {
		int result = 0;
		try {
			String sql = null;
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			if (id.equals("운영자")) {
				sql = "UPDATE BOARD SET TITLE = ? ,CONTENTS = ?, DATE = ? WHERE NUMBER = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, date);
				pstmt.setInt(4, number);
				result = pstmt.executeUpdate();
			} else {
				sql = "UPDATE BOARD SET TITLE = ? ,CONTENTS = ?, DATE = ? WHERE ID = ? and NUMBER = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, date);
				pstmt.setString(4, id);
				pstmt.setInt(5, number);
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/* DB값 가져오기 */
	public Vector<BoardDTO> selectBoardVector(int number) { // DB 목록보기
		Vector<BoardDTO> boardList = new Vector<>();
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "SELECT NUMBER,ID,TITLE,CONTENTS,DATE FROM BOARD WHERE NUMBER = ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt(1));
				board.setId(rs.getString(2));
				board.setTitle(rs.getString(3));
				board.setContents(rs.getString(4));
				board.setDate(rs.getString(5));
				boardList.add(board);
			}
		} catch (SQLException e) {
		} finally {
			closeRs();
			closePstmt();
			closeConnection();
		}
		return boardList;
	}

	/* 게시판 검색 DB */
	public Vector<BoardDTO> TitleVector(boolean select, String title) { // DB 목록보기
		Vector<BoardDTO> boardList = new Vector<>();
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = null;
			if (select == true) {
				sql = "SELECT NUMBER,ID,TITLE,CONTENTS,DATE FROM BOARD WHERE TITLE LIKE ? ORDER BY NUMBER DESC";
			} else {
				sql = "SELECT NUMBER,ID,TITLE,CONTENTS,DATE FROM BOARD WHERE ID LIKE ? ORDER BY NUMBER DESC";
			}
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, "%" + title + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt(1));
				board.setId(rs.getString(2));
				board.setTitle(rs.getString(3));
				board.setContents(rs.getString(4));
				board.setDate(rs.getString(5));
				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeRs();
			closePstmt();
			closeConnection();
		}
		return boardList;
	}

	/* DB 목록보기 */
	public Vector<BoardDTO> showBoardVector() { // DB 목록보기
		Vector<BoardDTO> boardList = new Vector<>();
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "SELECT NUMBER,ID,TITLE,CONTENTS,DATE FROM BOARD ORDER BY NUMBER DESC ";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt(1));
				board.setId(rs.getString(2));
				board.setTitle(rs.getString(3));
				board.setContents(rs.getString(4));
				board.setDate(rs.getString(5));
				boardList.add(board);
			}
		} catch (SQLException e) {
		} finally {
			closeRs();
			closePstmt();
			closeConnection();
		}
		return boardList;
	}

	//////////////////////////////////////////////////////////////// 반납

	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closePstmt() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeRs() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
