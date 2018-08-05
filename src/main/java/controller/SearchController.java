/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SearchDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Search;

/**
 *
 * @author Andressa
 */
@Path("search")
public class SearchController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{id}")
    public Search get(@PathParam("id") int id) throws ClassNotFoundException {        
        try {
            SearchDAO searchDAO = new SearchDAO();
            return searchDAO.read(id);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public int create(Search search) throws ClassNotFoundException {
        try {
            SearchDAO searchDAO = new SearchDAO();
            return searchDAO.register(search);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)    
    @Path("")
    public void update(Search search) throws ClassNotFoundException {        
        try {
            SearchDAO searchDAO = new SearchDAO();
            searchDAO.update(search);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
}