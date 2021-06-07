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
	
	public List<StudentVo> selectBySname(String name) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "   where sname like '%" + name + "%'"
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
	
	public List<StudentVo> selectByMajor(String strMajor) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "   where major like '%" + strMajor + "%'"
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

	public boolean updateStudent(String oldSno, StudentVo newVo) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "update tbl_student"
					+ "   set sno = ?, sname = ?, syear = ?, gender = ?, major = ?, score = ?"
					+ "   where sno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newVo.getSno());
			pstmt.setString(2, newVo.getSname());
			pstmt.setInt(3, newVo.getSyear());
			pstmt.setString(4, newVo.getGender());
			pstmt.setString(5, newVo.getMajor());
			pstmt.setInt(6, newVo.getScore());
			pstmt.setString(7, oldSno);
			
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

	public boolean deleteStudent(String sno) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "delete from tbl_student"
					+ "   where sno = '" + sno + "'";
			pstmt = conn.prepareStatement(sql);
			
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
	
	public int checkSno(String sno) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) from tbl_student"
					+ "   where sno = '" + sno + "'"; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				
				return count;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pstmt, conn);
		}
		return -1;
	}

	public StudentVo selectBySno(String oldSno) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			String sql = "select * from tbl_student"
					+ "	  where sno = '" + oldSno +"'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String sno = rs.getString(1);
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
}
