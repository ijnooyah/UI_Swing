package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
	private static StudentDao instance;

	private StudentDao() {
	}

	public static StudentDao getInstance() {
		if (instance == null) {
			instance = new StudentDao();
		}
		return instance;
	}

	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "exam01";
	private final String PASSWORD = "1234";

	private Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println(conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null) try {rs.close();} catch(Exception e) {e.printStackTrace();} 
		if (pstmt != null) try {pstmt.close();} catch(Exception e) {e.printStackTrace();} 
		if (conn != null) try {conn.close();} catch(Exception e) {e.printStackTrace();} 
	}

	public boolean insertStudent(StudentVo vo) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "insert into tbl_student"
					+ "   values (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getSno());
			pstmt.setString(2, vo.getSname());
			pstmt.setInt(3, vo.getSyear());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getMajor());
			pstmt.setInt(6, vo.getScore());
			
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(null, pstmt, conn);
		}
		
		return false;
	}
	
	public List<StudentVo> selectAll() {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "   order by sno"; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<StudentVo> list = new ArrayList<>();
			while(rs.next()) {
				String sno = rs.getString(1);
				String sname = rs.getString(2);
				int syear = rs.getInt(3);
				String gender = rs.getString(4);
				String major = rs.getString(5);
				int score = rs.getInt(6);
				
				StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
				list.add(vo);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public StudentVo selectBySno(String sno) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "   where sno = '" + sno + "'"
					+ "   order by sno"; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String sname = rs.getString(2);
				int syear = rs.getInt(3);
				String gender = rs.getString(4);
				String major = rs.getString(5);
				int score = rs.getInt(6);
				
				StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
				return vo;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
	
	public List<StudentVo> selectByMajor(String major) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "   where major = '" + major + "'"
					+ "   order by sno"; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<StudentVo> list = new ArrayList<>();
			while(rs.next()) {
				String sno = rs.getString(1);
				String sname = rs.getString(2);
				int syear = rs.getInt(3);
				String gender = rs.getString(4);
				int score = rs.getInt(6);
				
				StudentVo vo = new StudentVo(sno, sname, syear, gender, major, score);
				list.add(vo);
			}
			return list;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return null;
	}
}
