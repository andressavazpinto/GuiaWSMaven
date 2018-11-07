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
import javax.ws.rs.PUT;
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

@Path("users/interests/")
public class UserInterestController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    //@Path("")
    public List<UserInterest> list() throws ClassNotFoundException, SQLException {                     
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            return userInterestDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(UserInterestController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Path("")
    public int insertUserInterests(List<UserInterest> userinterest) throws ClassNotFoundException {
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            userInterestDAO.insertUserInterests(userinterest);
            return 1;
        } catch(SQLException e) {
            Logger.getLogger(UserInterestController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DELETE       
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) throws ClassNotFoundException {        
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            userInterestDAO.delete(id);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserInterestController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{idUser}")
    public List<UserInterest> get(@PathParam("idUser") int idUser) throws ClassNotFoundException {        
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            return userInterestDAO.listByUser(idUser);
        } catch(SQLException e) {
            Logger.getLogger(UserInterestController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)    
    @Path("{idUser}")
    public void update(@PathParam("idUser") int idUser, List<UserInterest> userinterest) throws ClassNotFoundException {        
        try {
            UserInterestDAO userInterestDAO = new UserInterestDAO();
            userInterestDAO.update(idUser, userinterest);            
        } catch(SQLException e) {
            Logger.getLogger(UserInterestController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
}
