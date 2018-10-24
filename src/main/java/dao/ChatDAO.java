/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import infra.ConnectionJDBC;
import infra.ConnectionMySQLJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Chat;
import util.StatusChat;

/**
 *
 * @author Andressa
 */
public class ChatDAO {
    private final ConnectionJDBC connection;
    
    public ChatDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }
    
    public int register(Chat chat) throws SQLException, ClassNotFoundException {
        int id=-1;
        String sqlQuery = "INSERT INTO chat (id_user1, id_user2, status)"
                + " VALUES (?, ?, ?);";
        String sqlQuery2 = "SELECT LAST_INSERT_ID() AS 'aux';";
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, chat.getIdUser1());
            stmt.setInt(2, chat.getIdUser2());
            stmt.setString(3, chat.getStatus().toString());
            
            stmt.executeUpdate();                        
            
            PreparedStatement stmt2 = this.connection.getConnection().prepareStatement(sqlQuery2);
            
            ResultSet rs = stmt2.executeQuery();            
            
            if(rs.next())
                id = rs.getInt("aux");
            
            this.connection.commit();            
        } catch (SQLException e) {
             this.connection.rollback();
             throw e;
        }
        finally {
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return id;
    }
    
    public void update(Chat chat) throws SQLException, ClassNotFoundException {        
        String sqlQuery = "UPDATE user SET status = ?"
                + " WHERE id_user1 = ? OR id_user2 = ?;";
                                   
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
        
            stmt.setString(1, chat.getStatus().toString());
            stmt.setInt(2, chat.getIdUser1());
            stmt.setInt(3, chat.getIdUser1());                            
            
            stmt.executeUpdate();
            this.connection.commit();
            
        } catch(SQLException e) {
            this.connection.rollback();
            throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public Chat read(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM chat WHERE status = ? & (id_user1 = ? OR id_user2 = ?);";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            stmt.setString(1, "Active");
            stmt.setInt(2, id);
            stmt.setInt(3, id);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return parser(rs);
            }            
        } catch(SQLException e) {
            throw e;
        }
        finally {            
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }
    
    public List<Chat> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM chat ORDER by idChat";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            rs = stmt.executeQuery();
            
            List<Chat> chats = new ArrayList();
            
            while(rs.next()) {
                chats.add(parser(rs));                
            }
            
            return chats;         
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    private Chat parser(ResultSet rs) throws SQLException {
        Chat chat = new Chat();
        
        chat.setIdUser1(rs.getInt("id_user1"));
        chat.setIdUser2(rs.getInt("id_user2"));
        chat.setIdChat(rs.getInt("idChat"));      
        chat.setStatus(StatusChat.valueOf(rs.getString("status")));
        
        return chat;
    }                  
}
