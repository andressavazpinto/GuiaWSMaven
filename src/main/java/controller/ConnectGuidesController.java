/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectGuidesDAO;
import dao.SearchByRegionDAO;
import dao.SearchDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.ConnectGuides;
import model.Search;
import util.StatusConnectGuides;
import util.StatusSearch;

/**
 *
 * @author Andressa
 */
@Path("connectguides")
public class ConnectGuidesController {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public ConnectGuides searchRamdomly(Search s) throws SQLException, ClassNotFoundException {
    //conectar usuários com status searching               
             
        SearchDAO searchDAO = new SearchDAO();
        List<Integer> ids = searchDAO.listAllLessMe(s.getIdUser()); 
        System.out.println("ids: " + ids.toString());
        int n = -1;
        
        if(ids.size() > 0) {
            System.out.println("Ids de search usua´rios" + ids.toString());
            Random rand = new Random();            
            n = rand.nextInt(ids.size());                
                               
            try {
                ConnectGuides c = new ConnectGuides();            
                c.setIdUser1(s.getIdUser());
                c.setIdUser2(ids.get(n));
                c.setStatus(Enum.valueOf(StatusConnectGuides.class,"Found"));                
                
                ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
                ConnectGuides aux = connectGuidesDAO.register(c);
                return aux;
            } catch(ClassNotFoundException e) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }
    
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{city}/{id}")
    public ConnectGuides searchByRegion(@PathParam("city") String city, @PathParam("id") int id) throws SQLException, ClassNotFoundException {
    //conectar usuários com status searching               
             
        SearchByRegionDAO searchDAO = new SearchByRegionDAO();
        List<Integer> ids = searchDAO.getUsersCity(city, id); 
        System.out.println("ids: " + ids.toString());
        int n = -1;
        
        if(ids.size() > 0) {
            System.out.println("Ids de search usua´rios" + ids.toString());
            Random rand = new Random();            
            n = rand.nextInt(ids.size());                
                               
            try {
                ConnectGuides c = new ConnectGuides();            
                c.setIdUser1(id);
                c.setIdUser2(ids.get(n));
                c.setStatus(Enum.valueOf(StatusConnectGuides.class,"Found"));                
                
                ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
                ConnectGuides aux = connectGuidesDAO.register(c);
                return aux;
            } catch(ClassNotFoundException e) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        return null;
    }
    
    
    //sempre que mudar o status de um usuário (a partir do found, chamar o método abaixo)
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public void setConnectGuides(Search search) throws SQLException, ClassNotFoundException {
        //pegar o registro do connectguides deste usuário
        ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
        ConnectGuides connectGuides = connectGuidesDAO.read(search.getIdUser());
        
        int idUser2 = connectGuides.getIdUser1();
        if(connectGuides.getIdUser1() == search.getIdUser())
            idUser2 = connectGuides.getIdUser2();
        
        //pegar registro de search do user2
        SearchDAO searchDAO = new SearchDAO();
        Search search2 = searchDAO.read(idUser2);
        
        StatusSearch ss1 = search.getStatus();
        StatusSearch ss2 = search2.getStatus();
        
        StatusSearch found = Enum.valueOf(StatusSearch.class, "Found");
        StatusSearch waiting = Enum.valueOf(StatusSearch.class, "WaitingAnswer");
        StatusSearch accepted = Enum.valueOf(StatusSearch.class, "Accepted");
        StatusSearch rejected = Enum.valueOf(StatusSearch.class, "Rejected");
        
        StatusConnectGuides statusAtual = connectGuides.getStatus();
        StatusConnectGuides newStatus = Enum.valueOf(StatusConnectGuides.class, "bla");
        
        if(ss1 == found && ss2 == found)
            newStatus = Enum.valueOf(StatusConnectGuides.class, "Found");
        else if((ss1 == waiting | ss2 == waiting) && ((ss1 == found | ss1 == waiting | ss1 == accepted)) | (ss2 == found | ss2 ==waiting | ss2 == accepted))
            newStatus = Enum.valueOf(StatusConnectGuides.class, "WaitingAnswer");
        else if(ss1 == accepted && ss2 == accepted)
            newStatus = Enum.valueOf(StatusConnectGuides.class, "Acepted");
        else if((ss1 == rejected | ss2 == rejected) && ((ss1 == found | ss1 == waiting) | (ss2 == found | ss2 == waiting)))
            newStatus = Enum.valueOf(StatusConnectGuides.class, "Rejected");
                            
        //chamar a alteração de fato
        if (statusAtual != newStatus) {
            connectGuides.setStatus(newStatus);
            try {                            
                connectGuidesDAO.update(connectGuides);
                //return Response.status(Response.Status.OK).build();
            } catch(SQLException e) {
                Logger.getLogger(ConnectGuidesController.class.getName()).log(Level.SEVERE, null, e);
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            } 
        }            
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public ConnectGuides get(@PathParam("id") int idUser) throws ClassNotFoundException {        
        try {
            ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
            return connectGuidesDAO.read(idUser);
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) throws ClassNotFoundException {        
        try {
            ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
            connectGuidesDAO.delete(id);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    //@Path("{idConnectGuides}")
    public void desactiveChat(ConnectGuides connectGuides) throws ClassNotFoundException {
        try {
            ConnectGuidesDAO connectGuidesDAO = new ConnectGuidesDAO();
            connectGuidesDAO.update(connectGuides);
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
