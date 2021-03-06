/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.InterestDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Interest;

/**
 *
 * @author Andressa
 */
@Path("interests")
public class InterestController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    @Path("")
    public List<Interest> list() throws ClassNotFoundException, SQLException {                     
        try {
            InterestDAO interestDAO = new InterestDAO();
            return interestDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
}
