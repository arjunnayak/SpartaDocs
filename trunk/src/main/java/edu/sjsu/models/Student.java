package edu.sjsu.models;

import java.util.HashMap;

public class Student extends SchoolMember {
	private Advisor advisor;

	public Student() {
		super();
	}

	public Student(String email, String firstName, String lastName, String sID, Major major, Advisor advisor) {
		super(email, firstName, lastName, sID, major);
		this.advisor = advisor;
	}
	
	public Advisor getAdvisor() {
		return advisor;
	}
	
	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}
	
	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = super.getHashMap();
		if (advisor != null){
		json.put("Advisor", getAdvisor().getHashMap());
		}
		return json;
	}

}
