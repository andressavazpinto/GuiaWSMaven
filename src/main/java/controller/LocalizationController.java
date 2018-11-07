/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LocalizationDAO;
import java.sql.SQLException;
import java.util.List;
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
import model.Localization;

/**
 *
 * @author Andressa
 */
@Path("localization")
public class LocalizationController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    //@Path("")
    public List<Localization> list() throws ClassNotFoundException, SQLException {                     
        try {
            LocalizationDAO localizationDAO = new LocalizationDAO();
            return localizationDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(LocalizationController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Localization get(@PathParam("id") int id) throws ClassNotFoundException {        
        try {
            LocalizationDAO localizationDAO = new LocalizationDAO();
            return localizationDAO.read(id);
        } catch(SQLException e) {
            Logger.getLogger(LocalizationController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@Path("")
    public int register(Localization localization) throws ClassNotFoundException {
        try {
            LocalizationDAO localizationDAO = new LocalizationDAO();
            return localizationDAO.register(localization);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(LocalizationController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)   
    //@Path("")
    public void update(Localization localization) throws ClassNotFoundException {        
        try {
            LocalizationDAO localizationDAO = new LocalizationDAO();
            localizationDAO.update(localization);            
        } catch(SQLException e) {
            Logger.getLogger(LocalizationController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
}