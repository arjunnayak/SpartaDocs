package edu.sjsu.rest;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.db.AdvisorDao;
import edu.sjsu.db.StudentDao;
import edu.sjsu.models.Advisor;
 
@Path("/signup")
public class SignupService {
    
    @POST
    @Consumes("text/plain")
    public Response signupUser(String json) throws JSONException {
    	JSONObject jsonObj = new JSONObject(json);
    	System.out.println(jsonObj.toString());
		String fname = (String)jsonObj.get("FName");
		String lname = (String)jsonObj.get("LName");
		String sid = (String)jsonObj.get("SID");
		String major = (String)jsonObj.get("Major");
		String email = (String)jsonObj.get("Email");
		String password = (String)jsonObj.get("Password");
		String userType = (String)jsonObj.get("userType");
        if(fname.trim().length() > 0
        		&& lname.trim().length() > 0
        		&& sid.trim().length() > 0
        		&& major.trim().length() > 0
        		&& email.trim().length() > 0 
        		&& password.trim().length() > 0
        		&& userType.trim().length() > 0) {
        	boolean success = false;
        	String verificationLink = "";
        	if(userType.equals("student")) {
        		Advisor randomAdvisor = AdvisorDao.getRandomAdvisor();
        		if(randomAdvisor != null){
        			success = StudentDao.storeStudent(fname, lname, sid, major, email, password, randomAdvisor.getEmail());
        		}else{
        			success = StudentDao.storeStudent(fname, lname, sid, major, email, password, null);
        		}
        		verificationLink = "http://localhost:8080/MASSware/rest/activate/student/" + email; 
        	} else {
        		success = AdvisorDao.storeAdvisor(fname, lname, sid, major, email, password);
        		verificationLink = "http://localhost:8080/MASSware/rest/activate/advisor/" + email;
        	}
        	if(success) {
        		try {
        			generateAndSendEmail(email, verificationLink);
        			return Response.status(Response.Status.ACCEPTED).build();
        		} catch(Exception e) {
        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        		}
        	}
        }
        return Response.status(Response.Status.PRECONDITION_FAILED).build();
    }
    
    public static void generateAndSendEmail(String email, String verificationLink) throws AddressException, MessagingException {

    	Properties mailServerProperties = null;
    	Session getMailSession = null;
    	MimeMessage generateMailMessage = null;
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject("SpartaDocs Verification Link");
		String emailBody = "Activate your SpartaDocs account: "+verificationLink+"<br><br> With much love, <br>The SpartaDocs Team";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
		String teamEmail = "spartadocs.cs160@gmail.com";
		String gmailPassword = "massware";
		transport.connect("smtp.gmail.com", teamEmail, gmailPassword);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
		System.out.println("\n Email Sent");
		return;
	}
}