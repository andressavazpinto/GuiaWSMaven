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
import model.UserInterest;

/**
 *
 * @author Andressa
 */
public class UserInterestDAO {
    private final ConnectionJDBC connection;
    
    public UserInterestDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }   
    
    public List<UserInterest> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user_interest ORDER by idUserInterest";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            rs = stmt.executeQuery();
            
            List<UserInterest> userInterests = new ArrayList();
            
            while(rs.next()) {
                userInterests.add(parser(rs));                
            }
            
            return userInterests;         
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    private UserInterest parser(ResultSet rs) throws SQLException {
        UserInterest i = new UserInterest();
        
        i.setIdUserInterest(rs.getInt("idUserInterest"));
        i.setIdUser(rs.getInt("id_user"));
        i.setIdInterest(rs.getInt("id_interest"));
        
        return i;
    }
    
    public boolean insert(UserInterest userInterest) throws SQLException {
        Boolean output = false;
        String sqlQuery = "INSERT INTO user_interest (id_user, id_interest) VALUES (?, ?);";        
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, userInterest.getIdUser());
            stmt.setInt(2, userInterest.getIdInterest());
            
            stmt.executeUpdate();            
            this.connection.commit();
            output = true;
        } catch (SQLException e) {
             this.connection.rollback();
             throw e;
        }

        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        
        return output;
    }
    
    public void insertUserInterests(List<UserInterest> userInterests) throws SQLException {        
        String sqlQuery = "INSERT INTO user_interest (id_user, id_interest) VALUES (?, ?);";        
                
        try {
            for(int i=0; i<userInterests.size(); i++) {
                PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
                stmt.setInt(1, userInterests.get(i).getIdUser());
                stmt.setInt(2, userInterests.get(i).getIdInterest());
            
                stmt.executeUpdate();
            }
            this.connection.commit();
        } catch (SQLException e) {
             this.connection.rollback();
             throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public int delete(int id) throws SQLException, ClassNotFoundException {
        int affectedRows = 0;
        String sqlQuery = "DELETE FROM user_interest WHERE idInterest = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            
            affectedRows = stmt.executeUpdate();
            this.connection.commit();
            this.connection.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return affectedRows;
    }
    
    public int deleteByUser(int idUser) throws SQLException, ClassNotFoundException {
        int affectedRows = 0;
        String sqlQuery = "DELETE FROM user_interest WHERE id_user = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, idUser);
            
            affectedRows = stmt.executeUpdate();
            this.connection.commit();
            this.connection.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return affectedRows;
    }
    
    public List<UserInterest> listByUser(int idUser) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user_interest WHERE id_user = ? ORDER BY id_interest;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();
            
            List<UserInterest> userInterests = new ArrayList();
            
            while(rs.next()) {
                userInterests.add(parser(rs));
            }    
            return userInterests;
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public void update(int idUser, List<UserInterest> userInterests) throws SQLException {        
        String sqlQuery = "DELETE FROM user_interest WHERE id_user = ?;";
        String sqlQuery2 = "INSERT INTO user_interest (id_user, id_interest) VALUES (?, ?);";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, idUser);                                    
            stmt.executeUpdate();
            
            for(int i=0; i<userInterests.size(); i++) {
                PreparedStatement stmt2 = this.connection.getConnection().prepareStatement(sqlQuery2);
                stmt2.setInt(1, userInterests.get(i).getIdUser());
                stmt2.setInt(2, userInterests.get(i).getIdInterest());
            
                stmt2.executeUpdate();
            }
            
            this.connection.commit();
            this.connection.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }   
}