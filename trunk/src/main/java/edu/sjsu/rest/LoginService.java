package edu.sjsu.rest;
import edu.sjsu.db.*;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

import java.util.Map;
import java.util.HashMap;
import javax.ws.rs.Consumes;

import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/login")
public class LoginService {
    
	@POST
	@Consumes("text/plain")
    public Response signinUser(String json) throws JSONException {
		JSONObject jsonObj = new JSONObject(json);
		String email = (String)jsonObj.get("email");
		String password = (String)jsonObj.get("password");
		String userType = (String)jsonObj.get("userType");
		System.out.println(email + "," + password + "," + userType);
        if(email.trim().length() > 0 && password.trim().length() > 0 && userType.trim().length() > 0) {
        	Map<String, Object> user = null;
        	if(userType.equals("student")) {
        		user = StudentDao.checkAuth(email, password);
        	} else if(userType.equals("faculty")) {
        		user = AdvisorDao.checkAuth(email, password);
        	}
        	if(user != null) {
        		JSONObject jsonResp = new JSONObject(user);
        		return Response.status(200).entity(jsonResp.toString()).build();
        	} else {
        		return Response.status(Response.Status.NOT_FOUND).entity("No verified users with passed in parameters").type(MediaType.TEXT_PLAIN).build();
        	}
        }
        return Response.status(Response.Status.PRECONDITION_FAILED).entity("Unknown failure").type(MediaType.TEXT_PLAIN).build();
    }
}