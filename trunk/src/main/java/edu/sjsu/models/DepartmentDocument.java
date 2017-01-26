package edu.sjsu.models;

import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONObject;

public class DepartmentDocument extends Document{
	private Major major;
	public DepartmentDocument() {
		super();
	}

	public DepartmentDocument(int docID, Timestamp dateCreated, String location, Timestamp lastUpdated, String name,
			Major major) {
		super(docID, dateCreated, location, lastUpdated, name);
		this.major = major;
	}
	
	public DepartmentDocument(String name, Major major){
		super(name);
		this.major = major;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}
	
	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = super.getHashMap();
		HashMap<String, Object> majorHash = major.getHashMap();
		JSONObject majorJSON = new JSONObject(majorHash);
		json.put("Major", majorJSON.toString());
		return json;
	}


	
}
