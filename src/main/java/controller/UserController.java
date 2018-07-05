/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;

/**
 *
 * @author Andressa
 */
@Path("users")
public class UserController {        
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    @Path("")
    public List<User> list() throws ClassNotFoundException {                     
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.list();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public User get(@PathParam("id") int id) throws ClassNotFoundException {
        //Usuario u1 = new User(1, "Usuário1", "04/04/2000", "Português BR", "Estagiário", "usuario@teste.com", "123123", "São Paulo, SP - Brasil", Status.Ativo);
        
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.read(id);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public int create(User user) throws ClassNotFoundException {
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.register(user);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public User update(User user) throws ClassNotFoundException {        
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.update(user);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public User login(User user) throws ClassNotFoundException {
        try {
            UserDAO usuerDAO = new UserDAO();
            return usuerDAO.login(user);            
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DELETE       
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) throws ClassNotFoundException {        
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.delete(id);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
    
    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public Boolean checkEmail(@PathParam("email") String email) throws ClassNotFoundException {                
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.checkEmail(email);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }*/
}
