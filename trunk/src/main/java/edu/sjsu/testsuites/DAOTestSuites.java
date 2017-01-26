package edu.sjsu.testsuites;

import edu.sjsu.db.AdvisorDao;
import edu.sjsu.db.MajorDao;
import edu.sjsu.db.StudentDao;
import edu.sjsu.models.Advisor;

public class DAOTestSuites {
	public static void main(String[] args){
		
		String studentEmail = "frank.daniels@sjsu.edu";
		
		String result = StudentDao.getStudentString(studentEmail);
		
		System.out.println(result);
		
		String advisorEmail = "Ahmed.Yazdankah@sjsu.edu";
		result = AdvisorDao.getAdvisorString(advisorEmail);
		
		System.out.println(result);
		
		int majorID = 1;
		result = MajorDao.getMajorString(majorID);
		
		System.out.println(result);
		
		System.out.printf("Did the update succeed? %b %n", MajorDao.setMajorAdvisor(null, majorID));
		result = MajorDao.getMajorString(majorID);
		System.out.println(result);
		System.out.printf("Did the update succeed? %b %n", MajorDao.setMajorAdvisor(new Advisor("Ahmed.Yazdankah@sjsu.edu", "Ahmed", "Yazdankah", "001234567",
				null), majorID));
	}
}
