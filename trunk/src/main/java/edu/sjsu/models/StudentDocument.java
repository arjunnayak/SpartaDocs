package edu.sjsu.models;

import java.sql.Timestamp;
import java.util.HashMap;
import org.json.JSONObject;

public class StudentDocument extends Document {
	
	private Student postedBy;
	private Advisor advisor;
	private String status;
	
	public StudentDocument() {
		super();
		this.postedBy = null;
	}

	public StudentDocument(int docID, Timestamp dateCreated, String location, Timestamp lastUpdated, String name,
			Student postedBy, String status, Advisor advisor) {
		super(docID, dateCreated, location, lastUpdated, name);
		this.postedBy = postedBy;
		this.advisor = advisor;
		this.status = status;
	}
	
	public StudentDocument(String name, Student postedBy){
		super(name);
		this.postedBy = postedBy;
		this.advisor = postedBy.getAdvisor();
	}

	public Student getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(Student postedBy) {
		this.postedBy = postedBy;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public Advisor getAdvisor(){
		return advisor;
	}
	
	public void setAdvisor(Advisor advisor){
		this.advisor = advisor;
	}
	
	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = super.getHashMap();
		if (advisor != null){
		HashMap<String, Object> advisorHash = advisor.getHashMap();
		JSONObject advisorJSON = new JSONObject(advisorHash);
		json.put("Advisor", advisorJSON.toString());
		}
		HashMap<String, Object> studentHash = postedBy.getHashMap();
		JSONObject studentJSON = new JSONObject(studentHash);
		json.put("Student", studentJSON.toString());
		return json;
	}


}
