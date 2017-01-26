package edu.sjsu.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.sjsu.models.SchoolMember;
import edu.sjsu.utils.DBUtils;

public class Advisor extends SchoolMember {
	public Advisor(){
		super();
	}

	public Advisor(String email, String firstName, String lastName, String sID, Major major) {
		super(email, firstName, lastName, sID, major);
	}
}
