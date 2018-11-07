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
import model.Search;
import model.User;
import dao.SearchDAO;
import model.Grade;
import util.StatusSearch;

/**
 *
 * @author Andressa
 */
@Path("users")
public class UserController {        
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)       
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
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.read(id);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("score/{id}")
    public Double getScore(@PathParam("id") int id) throws ClassNotFoundException {        
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.getScore(id);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    public int create(User user) throws ClassNotFoundException {
        int aux = -1;
        try {
            UserDAO userDAO = new UserDAO();           
            aux = userDAO.register(user);
            
            SearchDAO searchDAO = new SearchDAO();
            Search search = new Search();
            search.setStatus(StatusSearch.Searching);
            search.setIdUser(aux);
            searchDAO.register(search);
            
            return aux;            
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public User update(User user) throws ClassNotFoundException {        
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.update(user);            
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)    
    @Path("score/{id}")
    public void updateScore(@PathParam("id") int idUser, Grade grade) throws ClassNotFoundException {
        User u;
        double aux;
        double score;
        
        try {
            UserDAO userDAO = new UserDAO();
            u = userDAO.read(idUser);            
                        
            score = u.getScore();
            
            if(score == 0.0)
                aux = grade.getGrade();
            else
                aux = (score + grade.getGrade()) / 2;            
            
            UserDAO userDAO2 = new UserDAO();
            userDAO2.updateScore(idUser, aux);
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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("email/{email}")
    public boolean checkEmail(@PathParam("email") String email) throws ClassNotFoundException {                
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.checkEmail(email);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pass/{email}")
    public String getPass(@PathParam("email") String email) throws ClassNotFoundException {                
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.getPass(email);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
}
