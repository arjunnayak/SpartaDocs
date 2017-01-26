package edu.sjsu.db;
import java.sql.*;

import edu.sjsu.models.Advisor;
import edu.sjsu.models.SchoolMember;
import edu.sjsu.models.Student;
import edu.sjsu.utils.*;

public abstract class SchoolMemberDao {
	
	public static String getUUIDForHash(String hash) {
		try {
			Connection conn = DBUtils.getConn();
			if (conn == null) return null;
			String sqlStr = "select * from User where hash=?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1,  hash);
			ResultSet result = pstmt.executeQuery();
			if (result == null) { 
				return null;    
			} else {
				result.next();
				String uuid = String.valueOf(result.getInt("idUser"));
				return uuid;
			}    
		} catch(SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} 	
		return null;
	}
	
	//Returns a student or advisor object depending on who that email belongs to
	public static SchoolMember getSchoolMember(String email) {
		Student possible_student = StudentDao.getStudent(email);
		Advisor possible_advisor = AdvisorDao.getAdvisor(email);
		if (possible_student != null) {
			return possible_student;
		}
		else if (possible_advisor != null){
			return possible_advisor;
		}
		return null;
	}
}
