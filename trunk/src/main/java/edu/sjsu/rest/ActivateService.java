package edu.sjsu.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import edu.sjsu.db.*;
import edu.sjsu.models.*;

@Path("/activate")
public class ActivateService {  
	@GET
	@Path("/student/{email}") 
	public Response activateStudent(@PathParam("email") String email) {
		Student temp = StudentDao.getStudent(email);
		if(temp == null) {
			return Response.status(404).entity("User not found").build();
		}
		StudentDao.activateStudent(email);
		try {
			return Response.temporaryRedirect(new URI("http://localhost:8080/MASSware/")).build();
		} catch (URISyntaxException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GET
	@Path("/advisor/{email}") 
	public Response activateAdvisor(@PathParam("email") String email) {
		Advisor temp = AdvisorDao.getAdvisor(email);
		if(temp == null) {
			return Response.status(404).entity("User not found").build();
		}
		AdvisorDao.activateAdvisor(email);
		try {
			return Response.temporaryRedirect(new URI("http://localhost:8080/MASSware/")).build();
		} catch (URISyntaxException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}