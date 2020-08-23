package kr.or.connect.jdbcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import kr.or.connect.jdbcexam.dto.Role;

public class RoleDao { 
	
	// 상수
	private static String dburl =   "jdbc:mysql://127.0.0.1:3306/connectdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String dbUser = "root";
	private static String dbpassword = "1234";
	
	public List<Role> getRoles(){
		
		List<Role> list = new ArrayList<Role>();
		
		try { 
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sql = "select description, role_id from role";
		
		try(Connection conn = DriverManager.getConnection(dburl, dbUser, dbpassword);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			try(ResultSet rs = ps.executeQuery()) {
				
				while (rs.next()) {
					String description = rs.getString("description");
					int roleId = rs.getInt("role_id");
					Role role = new Role(roleId, description);
					list.add(role);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return list;
	}
	
	public int deleteRole(Integer roleId) {
		
		int deleteCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpassword);
			String sql = "delete from role where role_id = ?";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, roleId);
			
			deleteCount = ps.executeUpdate();
			
		} catch (Exception e) {
			
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
			
		return deleteCount;
	}
	
	public int addRole(Role role) {
		
		int insertCount = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		
	
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dburl, dbUser, dbpassword);
			String sql = "insert into role (role_id, description) values (?, ?)";

			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, role.getRoleId());
			ps.setString(2, role.getDescription());
			
			insertCount = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
		
		return insertCount;
		
	}

	public Role getRole(Integer roleId) {		// role 객체를 가져오는 메서드
		
		Role role = null;
		 
		Connection conn = null;				// 연결객체
		PreparedStatement ps = null;		// 명령어 객체
		ResultSet rs = null; 				// 결과값 객체
		
		// 모든 객체 닫아주기 
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");			// 드라이버 로딩
			conn = DriverManager.getConnection(dburl, dbUser, dbpassword);
			ps = conn.prepareStatement("select description, role_id from role where role_id = ?"); // 쿼리문 넣기
			ps.setInt(1, roleId);			// 1번째 물음표 위치에 roleId 를 넣으라는 명령
			rs = ps.executeQuery();				// 쿼리문 실행
			
			
			// 결과값이 있다면
			if (rs.next()) {
				String description = rs.getString(1);				// 1번째 칼럼 가져오기
				Integer id = rs.getInt("role_id");			// 컬럼 이름으로 값 가져오기
				
				role = new Role(id, description);		// role 객체 만들기
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {

				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e2) {

				}
			}
		}
				
		return role;
		
	}
	
}