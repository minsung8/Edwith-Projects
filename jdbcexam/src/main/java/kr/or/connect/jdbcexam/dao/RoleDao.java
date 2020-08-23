package kr.or.connect.jdbcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.connect.jdbcexam.dto.Role;

public class RoleDao { 
	
	// 상수
	private static String dburl =   "jdbc:mysql://127.0.0.1:3306/connectdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String dbUser = "root";
	private static String dbpassword = "1234";

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