package edu.sjsu.models;

import java.util.HashMap;

public abstract class SchoolMember {
	private String email, firstName, lastName, SID;
	private Major major;
	
	public SchoolMember(){
		
	}
	

	public SchoolMember(String email, String firstName, String lastName, String sID, Major major) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		SID = sID;
		this.major = major;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = new HashMap<>();
		json.put("Email", getEmail());
		json.put("FName", getFirstName());
		json.put("LName", getLastName());
		json.put("SID", getSID());
		json.put("Major", getMajor().getHashMap());
		return json;
	}

}
