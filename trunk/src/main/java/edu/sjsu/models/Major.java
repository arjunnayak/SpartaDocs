package edu.sjsu.models;

import java.util.HashMap;

public class Major {
	private String name, deanEmail, location, phone;
	private int majorID;
	
	public Major(int majorID, String name, String deanEmail, String location, String phone) {
		this.majorID = majorID;
		this.name = name;
		this.deanEmail = deanEmail;
		this.location = location;
		this.phone = phone;
	}
	public int getMajorID(){
		return majorID;
	}
	
	public void setMajorID(int majorID){
		this.majorID = majorID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeanEmail() {
		return deanEmail;
	}

	public void setDeanEmail(String deanEmail) {
		this.deanEmail = deanEmail;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = new HashMap<String, Object>();
		json.put("DeanEmail", getDeanEmail());
		json.put("MajorName", getName());
		json.put("Phone", getPhone());
		json.put("Location", getLocation());
		return json;
	}

}
