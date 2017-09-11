package join;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinDao {

	private boolean check = false;
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
	private static final String DB_ID = "root";
	private static final String DB_PW = "sds1501";

	public static String aaa = "이거 나오면 안된다";	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	JoinDTO zzz = new JoinDTO();
	////////////////////////// 메소드 영역//////////////////////////////////
	public JoinDao() {
		try {
			// 드라이버 로딩은 한번만 하면 되므로 생성자로 로딩을 한다.
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("jar파일 드라이버 로딩 오류");
			e.printStackTrace();
		}

	}

	// 회원정보 ADD
	public int ADD(JoinDTO join) {
		int result = 0;
		if (check == false) {
			System.out.println("아이디 중복확인 해주세요");
			return result = 0;
		}
		System.out.println("asdfasdf");
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			String sql = "INSERT INTO TB_MEMBER(id,password,name,birth,gender,email,phone)" + "VALUES(?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, join.getId());
			pstmt.setString(2, join.getPass());
			pstmt.setString(3, join.getName());
			pstmt.setString(4, join.getBirth());
			pstmt.setString(5, join.getGender());
			pstmt.setString(6, join.getMail());
			pstmt.setString(7, join.getPhone());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("ADD 오류");
				e.printStackTrace();
			}

		}
		return result;
	}
	// 중복확인
	public int idcheck(String id) {
		check = false;
		int result = 0;
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);

			String sql = "SELECT * FROM TB_MEMBER WHERE ID=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("이미 존재하는 아이디");
				
			}else { 
				System.out.println("사용 가능한 아이디입니다.");
				check = true;
			}
		} catch (SQLException e) {
			System.out.println("로그인 오류");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}return result;
	}
	// 로그인
	public boolean LogCheck(String id, String password) {
		boolean result = false;
		try {
			con = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);

			String sql = "SELECT * FROM TB_MEMBER WHERE ID=? && PASSWORD=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {		
				zzz.setId(rs.getString(1));
				System.out.println("아이디는 "+zzz.getId());
				aaa = zzz.getId();
				System.out.println("아디 비번 일치");
				result = true;
			}else {
				System.out.println("아이디나 비번 오류");}
			
		} catch (SQLException e) {
			System.out.println("로그인 오류");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// 반납
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