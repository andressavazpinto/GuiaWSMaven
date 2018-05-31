/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserInterestDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.UserInterest;

/**
 *
 * @author Andressa
 */

@Path("users/interests")
public class UserInterestController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    @Path("/")
    public List<UserInterest> list() throws ClassNotFoundException, SQLException {                     
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            return userInterestDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response insert(UserInterest userinterest) throws ClassNotFoundException {
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            userInterestDAO.insert(userinterest);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DELETE       
    @Path("/{id}")
    public Response deleteUserInt(@PathParam("id") Integer id) throws ClassNotFoundException {        
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            userInterestDAO.delete(id);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }  
}
