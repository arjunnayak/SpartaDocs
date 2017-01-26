package edu.sjsu.rest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.db.AdvisorDao;
import edu.sjsu.db.DocumentDao;
import edu.sjsu.db.MajorDao;
import edu.sjsu.db.StudentDao;
import edu.sjsu.models.Advisor;
import edu.sjsu.models.DepartmentDocument;
import edu.sjsu.models.Document;
import edu.sjsu.models.Major;
import edu.sjsu.models.Student;
import edu.sjsu.models.StudentDocument;
import edu.sjsu.utils.FileAccess;
 
//This needs to be generalized so that we can store student and advisor documents with the same functions. Since
//  we have a decent model structure with inheritance we can do so with some instanceof testing and type casting
@Path("/document")
public class DocumentService {
	/*
	 * Retrieves Latest Version of document given a supplied DocID, DocType, and StudentEmail or MajorID
	 * Takes JSON with DocType ["StudentDocument" | "DepartmentDocument], DocID, [StudentEmail | MajorID]
	 * Returns JSON with { {DocumentInfo} , .. , .. , {UserData : JSONDATA} }
	 */
	@GET
	@Path("/retrieve")
	@Consumes("text/plain")	
	public Response getDocument(@QueryParam("DocID") String docIDParam, 
								@QueryParam("Email") String emailParam) throws JSONException {
		
		int docID = Integer.parseInt(docIDParam);
		Document doc = null;
		Student student = StudentDao.getStudent(emailParam);
		
		doc = DocumentDao.getStudentDocument(docID, student);
		
		try{
			return Response.status(Response.Status.ACCEPTED).entity(FileAccess.readDocumentFromFile(doc)).build();
		}catch (Exception e){
			e.printStackTrace();
		}
		return Response.status(Response.Status.PRECONDITION_FAILED).entity("Could not access file for Document.").build();
	}
	
	/*
	 * Retrieveall function 
	 * Retrieve document info ONLY not contents. Need to call getDocument or getVersionDocument
	 * Takes JSON with DocType("StudentDocument"|"DepartmentDocument"), UserEmail if getting a student's document or a MajorID if getting all major's documents
	 * Returns JSON in format { [UserEmail | MajorID] : [ {Date : "Date", .... }, .... ] }
	 */
	@GET
	@Path("/retrieveall")
	@Produces("text/plain")
	public Response getAllDocuments(@QueryParam("Email") String emailParam) throws JSONException {
		ArrayList<Document> list = new ArrayList<Document>();
		String identifier = "documents";
		System.out.println(emailParam);
		Student user = StudentDao.getStudent(emailParam);
		System.out.println(user.getEmail() + " " + user.getFirstName() + " " + user.getAdvisor());
		list = DocumentDao.getAllStudentDocs(user);	
		System.out.println(list.toString());
		String jsonResponse = "{ " + identifier + " : [ ";
		Iterator<Document> docIterate = list.iterator();
		if(!docIterate.hasNext()){
			jsonResponse = jsonResponse.concat("null ] }");
		}
		while(docIterate.hasNext()){
			Document doc = (StudentDocument) docIterate.next();
			if(doc != null) {
				System.out.println(doc.toString());
				JSONObject jsonDoc = new JSONObject(doc.getHashMap());
				jsonResponse = jsonResponse.concat(jsonDoc.toString().concat(","));
			}
		}
		jsonResponse.substring(0, jsonResponse.length() - 1); //takes out the last comma
		jsonResponse = jsonResponse.concat(" ] }");
		JSONObject json = new JSONObject(jsonResponse);
		return Response.status(Response.Status.ACCEPTED).entity(json.toString()).build();
	}
	
	/*
	 * Retrieves Specified Version of document given a supplied DocID, Date, DocType, and StudentEmail or MajorID
	 * Takes JSON with DocType ["StudentDocument" | "DepartmentDocument], Date, DocID, [StudentEmail | MajorID]
	 * Returns JSON with { {DocumentInfo} , .. , .. , {UserData : JSONDATA} }  
	 * 
	 */
	@GET
	@Path("/retrieveversion")
	@Consumes("text/plain")
	public Response getVersionedDocument(@QueryParam("DocID") String docIDParam,
										@QueryParam("Date") String dateParam,
										@QueryParam("Email") String emailParam) throws JSONException{
		
		System.out.println(docIDParam);
		System.out.println(dateParam);
		System.out.println(emailParam);
		
		int docID = Integer.parseInt(docIDParam);
		Timestamp date = Timestamp.valueOf(dateParam);
		Student student = StudentDao.getStudent(emailParam);
		Document doc = null;
		
		doc = DocumentDao.getStudentDocumentByDate(docID, student, dateParam);
		
		HashMap<String, Object> hashMap = doc.getHashMap();
		try{
			return Response.status(Response.Status.ACCEPTED).entity(FileAccess.readDocumentFromFile(doc)).build();
		}catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.PRECONDITION_FAILED).entity("Could not retrieve document").build();
		}
	}

    /*
     * Create a brand new document
     * Takes JSON with Email, DocName, UserType ["Student" | "Advisor"], and JsonData
     * Returns message with why failure occurred.
     */	
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDocument(String json) throws JSONException {
    	System.out.print("got to create");
    	JSONObject jsonObj = new JSONObject(json);
    	String userEmail = (String)jsonObj.get("Email");
		String docName = (String)jsonObj.get("DocName"); //name of doc, hold release, grad etc
		String userType = (String)jsonObj.get("UserType");
		String fileData = (String)jsonObj.get("FileData"); 
		//Previous data should not be saved into the file as it has no relation to the contents of the document and can be redundant. 
		StudentDocument student_doc = null;
		DepartmentDocument dept_doc = null;
		try{
			if(userType.compareTo("Student") == 0){
				Student user = StudentDao.getStudent(userEmail);
				student_doc = new StudentDocument(docName, user);
				Advisor advisor = user.getAdvisor();
				FileAccess.writeNewDocumentToFile(fileData, student_doc);//This method will write all base attributes required by database. 
				if(!DocumentDao.storeUserDocument(student_doc, user)){
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				return Response.status(Response.Status.ACCEPTED).build();
			}else{
				Advisor admin = AdvisorDao.getAdvisor(userEmail);
				dept_doc = new DepartmentDocument(docName, admin.getMajor());
				FileAccess.writeNewDocumentToFile(fileData, dept_doc);
				if(!DocumentDao.storeMajorDocument(dept_doc, admin.getMajor())){
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				return Response.status(Response.Status.ACCEPTED).build();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    //update an EXISTING document Students should be the only ones to alter their document data as Advisors should only approve or reject documents not alter internal data. 
    @POST
    @Path("/update")
    @Consumes("text/plain")
    public Response updateDocument(String json) throws JSONException {
    	JSONObject jsonObj = new JSONObject(json);
    	int docID = Integer.parseInt((String)jsonObj.get("DocID"));
    	Timestamp documentDate = Timestamp.valueOf((String)jsonObj.getString("DocTimestamp"));
    	String userEmail = (String)jsonObj.get("UserEmail");
    	String userData = (String)jsonObj.get("UserData");
    	
    	Student user = StudentDao.getStudent(userEmail);
    	Document primedDoc = DocumentDao.getStudentDocumentVersion(docID, documentDate, user);    	
    	try{
    		FileAccess.writeUpdateDocumentToFile(userData, primedDoc);
    		if(!DocumentDao.updateUserDocument(primedDoc)){
    			return Response.status(Response.Status.PRECONDITION_FAILED).entity("Could not update StudentDocument in Database.").build();
    		}
    		return Response.status(Response.Status.ACCEPTED).entity("Student saved updated Document.").build();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	//TODO: add more detailed failure message
        return Response.status(Response.Status.PRECONDITION_FAILED).entity("Failed to write updated document").build();
    }
    

}