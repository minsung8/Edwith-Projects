package kr.or.connect.jdbcexam;

import kr.or.connect.jdbcexam.dao.RoleDao;
import kr.or.connect.jdbcexam.dto.Role;

public class JDBCExam2 {

	public static void main(String[] args) {
		
		RoleDao dao = new RoleDao();
		
		int deleteCount = dao.deleteRole(500);
		
		System.out.println(deleteCount);
		
	}

}
