/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ChatDAO;
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
import model.Chat;

/**
 *
 * @author Andressa
 */
@Path("chat")
public class ChatController {        
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    @Path("")
    public List<Chat> list() throws ClassNotFoundException {                     
        try {
            ChatDAO chatDAO = new ChatDAO();
            return chatDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Chat get(@PathParam("id") int id) throws ClassNotFoundException {        
        try {
            ChatDAO chatDAO = new ChatDAO();
            System.out.println("id: " + id + " chat: " + chatDAO.read(id).toString());
            return chatDAO.read(id);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public int create(Chat chat) throws ClassNotFoundException {
        int aux = -1;
        try {
            ChatDAO chatDAO = new ChatDAO();           
            aux = chatDAO.register(chat);            
            return aux;
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public void update(Chat chat) throws ClassNotFoundException {        
        try {
            ChatDAO chatDAO = new ChatDAO();            
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
}