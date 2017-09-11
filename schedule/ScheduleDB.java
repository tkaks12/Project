package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDB {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	final String DB_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
	final String DB_ID = "root";
	final String DB_PW = "sds1501";

	public ArrayList<ScheduleVO> ScheduleGetAll(int year, int month, String id) {
		ArrayList<ScheduleVO> scheduleList = new ArrayList<>(); // 스케쥴VO 가변배열 생성
		con = null;
		pstmt = null;
		
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "select * from schedule where date like ? and id=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, year+"-"+month+"%");
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleVO scVO = new ScheduleVO();
				scVO.setId(rs.getString(1));
				scVO.setDate(rs.getString(2));
				scVO.setHour(rs.getInt(3));
				scVO.setMin(rs.getInt(4));
				scVO.setContent(rs.getString(5));
				scVO.setSeed(rs.getInt(6));
				scheduleList.add(scVO);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ScheduleGetAll 드라이버 로딩 오류");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ScheduleGetAll DB 연결 실패");
			e.printStackTrace();
		} finally {
			
		}
		return scheduleList;
	}
	
	public int ScheduleDel(int seed) {
	      con = null;
	      pstmt = null;
	      int result = 1111;
	      
	      try {
	         Class.forName(DB_DRIVER);
	         con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
	         String sql = "DELETE FROM schedule WHERE seed = ?";
	         pstmt = con.prepareStatement(sql);
	         
	         pstmt.setInt(1, seed);
	         
	         result = pstmt.executeUpdate();
	         
	      } catch (ClassNotFoundException e) {
	         System.out.println("드라이버 로딩 오류");
	         e.printStackTrace();
	      } catch (SQLException e) {
	         System.out.println("DB 연결 실패");
	         e.printStackTrace();
	      } finally {
	         closePstmt();
	         closeConnection();
	      }
	      return result;
	   }
	
	
	public int ScheduleEdit(String id, String date, int hour, int min, String Content, int seed) {
		con = null;
		pstmt = null;
		int result = 0;
		
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "update schedule set id=?, date=?, hour=?, min=?, content=? where seed=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, date);
			pstmt.setInt(3, hour);
			pstmt.setInt(4, min);
			pstmt.setString(5, Content);
			pstmt.setInt(6, seed);
		
			result = pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 오류");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		} finally {
			closePstmt();
			closeConnection();
		}
		return result;
	}
	
	public int ScheduleInsert(String id, String date, int hour, int min, String Content) {
		con = null;
		pstmt = null;
		int result = 0;
		
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "insert into schedule (id, date, hour, min, Content) VALUES(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, date);
			pstmt.setInt(3, hour);
			pstmt.setInt(4, min);
			pstmt.setString(5, Content);

			result = pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 오류");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		} finally {
			closePstmt();
			closeConnection();
		}
		return result;
	}
	
	public void closeConnection() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closePstmt() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeRS() {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
