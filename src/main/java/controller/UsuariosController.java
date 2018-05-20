/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDAO;
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
import model.Usuario;

/**
 *
 * @author Andressa
 */
@Path("usuarios")
public class UsuariosController {        
    
    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMensagem(@QueryParam("usuario") String usuario) {
        return "Bem vindo " + usuario;
    }*/    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)   
    @Path("/")
    public List<Usuario> listUsuarios() throws ClassNotFoundException {                     
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.listar();
        } catch(SQLException e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Usuario getUsuario(@PathParam("id") int id) throws ClassNotFoundException {
        //Usuario u1 = new Usuario(1, "Usuário1", "04/04/2000", "Português BR", "Estagiário", "usuario@teste.com", "123123", "São Paulo, SP - Brasil", Status.Ativo);
        
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.consultar(id);
        } catch(SQLException e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public int create(Usuario usuario) throws ClassNotFoundException {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.cadastrar(usuario);
            //return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Usuario usuario) throws ClassNotFoundException {        
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.atualizar(usuario);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }
    
    @DELETE       
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) throws ClassNotFoundException {        
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch(SQLException e) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }   
    }   
}
