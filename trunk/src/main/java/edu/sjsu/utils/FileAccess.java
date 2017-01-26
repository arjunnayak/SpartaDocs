package edu.sjsu.utils;


import edu.sjsu.models.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;
//import org.json.JSONObject;

public class FileAccess {
	private static String doclocation = File.separator + "src" + File.separator + 
			"main" + File.separator + "resources" + File.separator + "documents";

	//NOTE: Before function is called. doc.update() must be called at the time that the json was received by REST.
	//THIS MODIFIES THE PASSED IN DOCUMENT VARIABLE!! SETS THE LOCATION AND DATE CREATED
	public static void writeNewDocumentToFile(String json, Document doc) throws JSONException, IOException {
		FileOutputStream fop = null;
		File file;
		/* Unsure of the purpose of this so I just commented it out. 
		JSONObject jsonObj = new JSONObject(json);
		String docID = jsonObj.getString("DocID");
		String timestamp = jsonObj.getString("Date");
		*/
		String currentLocation = resolveDirectoryLocation();	
		
		String fileName = Long.toString(doc.getCurrentDate().getTime());
		//System.out.print(fileName);
		fileName = fileName.concat(doc.getName()); //Filename [Timestamp][Name].json
		String docType = (doc instanceof StudentDocument)? "Student" : "Major";
		String ownerID = (doc instanceof StudentDocument)? ((StudentDocument) doc).getPostedBy().getEmail() : Integer.toString(((DepartmentDocument) doc).getMajor().getMajorID());
		fileName = File.separator + docType + File.separator + ownerID + File.separator + fileName;
		String location = currentLocation.concat(fileName);
		resolveDir(currentLocation + File.separator + docType + File.separator + ownerID);
		System.out.println(location);
		file = new File(location);
		if (!file.exists()) {
			file.createNewFile();
		}
		fop = new FileOutputStream(file);
		// if file doesn't exists, then create it
		
		// get the content in bytes
		byte[] contentInBytes = json.getBytes();

		fop.write(contentInBytes);
		fop.flush();
		fop.close();
		
		try {
			if (fop != null) {
				fop.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(fileName);
		fileName = fileName.replace(File.separatorChar, File.pathSeparatorChar);
		System.out.println(fileName);
		doc.setLocation(fileName);
	}
	
	//TODO: Create writeUpdatedDocumentToFile
	public static void writeUpdateDocumentToFile(String json, Document doc) throws JSONException, IOException{
		FileOutputStream fop = null;
		File file;
		
		Timestamp date = doc.getCurrentDate();
		doc.setPreviousDate(date);
		Date currentTime = new Date();
		Timestamp newCurrentTime = new Timestamp(currentTime.getTime());
		doc.setCurrentDate(newCurrentTime);
		
		String currentLocation = resolveDirectoryLocation();
		String fileName = Long.toString(doc.getCurrentDate().getTime());
		fileName = fileName.concat(doc.getName());
		String docType = (doc instanceof StudentDocument)? "Student" : "Major";
		String ownerID = (doc instanceof StudentDocument)? ((StudentDocument) doc).getPostedBy().getEmail() : Integer.toString(((DepartmentDocument) doc).getMajor().getMajorID());
		
		fileName = File.separator + docType + File.separator + ownerID + File.separator + fileName;
		
		String location = currentLocation.concat(fileName);

		fileName = fileName.replace(File.separatorChar, File.pathSeparatorChar);
		doc.setLocation(fileName);
		
		resolveDir(currentLocation + File.separator + docType + File.separator + ownerID);
		
		file = new File(location);
		fop = new FileOutputStream(file);
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		byte[] contentInBytes = json.getBytes();
		
		fop.write(contentInBytes);
		fop.flush();
		fop.close();
		
		try{
			if(fop != null){
				fop.close();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	public static String readDocumentFromFile(Document doc){
		String currentDirectory = resolveDirectoryLocation();
		String location = currentDirectory.concat(doc.getLocation().replace(File.pathSeparatorChar, File.separatorChar));
		System.out.println(location);
		File file = null;
		FileInputStream fis = null;
		try{
			file = new File(location);
			fis = new FileInputStream(file);
			String jsonData = "";
			int readData = 0;
			while((readData = fis.read()) != -1 ){
				String data = Character.toString((char) readData);
				data = data.replace('\\',' ');
				jsonData = jsonData.concat(data);
				//System.out.println(jsonData);
			}
			
			return jsonData;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			try{
				if(fis != null){
					fis.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	private static String resolveDirectoryLocation(){
		String location = System.getProperty("user.dir");
		System.out.println("Current user dir: " + location);
		location = location.concat(doclocation);
		System.out.println("Going to output to: " + location);
		return location;
	}
	
	private static void resolveDir(String dir){
		File checkedDir = new File(dir);
		if(checkedDir.exists()){
			return;
		}else{
			checkedDir.mkdirs();
		}
	}
}
