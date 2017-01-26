package edu.sjsu.models;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;


public abstract class Document {
	private int docID;
	private Timestamp currentDate;
	private String location;
	private Timestamp previousDate;
	private String name;
	
	public Document() {

	}
	
	public Document(int docID, Timestamp currentDate, String location, Timestamp previousDate, String name) {
		this.currentDate = currentDate;
		this.location = location;
		this.previousDate = previousDate;
		this.name = name;
		this.docID = docID;
	}
	
	public Document(String name){
		Date date = new Date();
		this.currentDate = new Timestamp(date.getTime());
		this.name = name;
	}

	public Timestamp getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Timestamp currentDate) {
		this.currentDate = currentDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(Timestamp previousDate) {
		this.previousDate = previousDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDocID(){
		return docID;
	}
	public void setDocID(int docID){
		this.docID = docID;
	}


	public HashMap<String, Object> getHashMap() {
		HashMap<String, Object> json = new HashMap<>();
		json.put("Date", getCurrentDate());
		//json.put("DocLocation", getLocation());Only used internally not externally.
		json.put("PreviousDate", getPreviousDate());
		json.put("DocName", getName());
		json.put("DocID", getDocID());
		return json;
	}
}
