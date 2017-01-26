package edu.sjsu.models;

import java.sql.Timestamp;
import java.util.HashMap;

public class Comment {
	private SchoolMember postedBy;
	private String message;
	private Timestamp date;
	private Document document;

	public Comment() {
	}
	
	public Comment(SchoolMember postedBy, String message, Timestamp date, Document document) {
		this.postedBy = postedBy;
		this.message = message;
		this.date = date;
		this.document = document;
	}

	public SchoolMember getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(SchoolMember postedBy) {
		this.postedBy = postedBy;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public HashMap<String, Object> getHashMap(){
		HashMap<String, Object> json = new HashMap<>();
		//One-off, DB has email but this has a SchoolMember object
		json.put("PostedBy", getPostedBy().getHashMap());
		json.put("Date", getDate());
		json.put("Message", getMessage());
		json.put("Document", getDocument().getHashMap());
		return json;
	}
	
}
