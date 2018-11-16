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
import model.ConnectGuides;
import util.StatusConnectGuides;

/**
 *
 * @author Andressa
 */
public class ConnectGuidesDAO {
    private final ConnectionJDBC connection;
    
    public ConnectGuidesDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }        
    
    private ConnectGuides parser(ResultSet rs) throws SQLException {
        ConnectGuides c = new ConnectGuides();
        
        c.setIdConnectGuides(rs.getInt("idConnectGuides"));
        c.setIdUser1(rs.getInt("id_user1"));
        c.setIdUser2(rs.getInt("id_user2"));
        c.setStatus(StatusConnectGuides.valueOf(rs.getString("status")));
                
        return c;
    }
    
    public ConnectGuides read(int idUser) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM connectGuides WHERE (id_user1 = ? or id_user2 = ?);";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, idUser);
            stmt.setInt(2, idUser);
            //stmt.setString(3, "Active");
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
    
    public ConnectGuides register(ConnectGuides connectGuides) throws SQLException, ClassNotFoundException {        
        String sqlQuery = "INSERT INTO connectGuides (id_user1, id_user2, status) VALUES (?, ?, ?);";                     
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            
            stmt.setInt(1, connectGuides.getIdUser1());
            stmt.setInt(2, connectGuides.getIdUser2());
            stmt.setString(3, connectGuides.getStatus().toString());
            
            stmt.executeUpdate();                                                              
                       
            this.connection.commit();
            
        } catch (SQLException e) {
             this.connection.rollback();
             throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return connectGuides;
    }
    
    public void update(ConnectGuides connectGuides) throws SQLException {        
        String sqlQuery = "UPDATE connectGuides SET status = ?"
                + " WHERE id_user1 = ? or id_user2 = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            
            stmt.setString(1, connectGuides.getStatus().toString());
            stmt.setInt(2, connectGuides.getIdUser1());
            stmt.setInt(3, connectGuides.getIdUser1());
            
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
    
    public void delete(int id) throws SQLException {
        String sqlQuery = "DELETE FROM connectGuides WHERE idConnectGuides = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
            
            this.connection.commit();
            System.out.println("ConnectGuides deleted: " + id);
            
        } catch(SQLException e) {
            this.connection.rollback();
            throw e;           
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
