/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import infra.ConnectionMySQLJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import util.StatusUser;
import infra.ConnectionJDBC;

/**
 *
 * @author Andressa
 */
public class UserDAO {
    private final ConnectionJDBC connection;
    
    public UserDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }
    
    public int register(User user) throws SQLException, ClassNotFoundException {
        int id=-1;
        String sqlQuery = "INSERT INTO user (name, dateOfBirth, language, email,"
                + "password, id_localization, statusAccount) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String sqlQuery2 = "SELECT LAST_INSERT_ID() AS 'aux';";
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getDateOfBirth());
            stmt.setString(3, user.getLanguage());            
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getIdLocalization());
            stmt.setString(7, user.getStatusAccount().toString());            
            
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
    
    public User update(User user) throws SQLException, ClassNotFoundException {
        User u = new User();
        String sqlQuery = "UPDATE user SET name = ?, dateOfBirth = ?, language = ?,"
                + "email = ?, password = ? /*, id_localization = ?*/ WHERE idUser = ?;";
        
        String sqlQuery2 = "UPDATE user SET name = ?, dateOfBirth = ?, language = ?,"
                + "email = ? /*, id_localization = ?*/ WHERE idUser = ?;";
        
        String pass = user.getPassword();
        System.out.println(pass);
        
        if(pass == null | pass.equals("") | pass.matches(" ")) {            
            try {
                PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery2);
            
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getDateOfBirth());
                stmt.setString(3, user.getLanguage());                
                stmt.setString(4, user.getEmail());            
                //stmt.setInt(5, user.getIdLocalization());
                //stmt.setString(8, user.getStatusAccount().toString());
                stmt.setInt(5, user.getIdUser());
            
                stmt.executeUpdate();            
                //u = read(user.getIdUser());
                this.connection.commit();
            
            } catch(SQLException e) {
                this.connection.rollback();
                throw e;
            }
            finally {            
                try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        else {
            try {
                PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getDateOfBirth());
                stmt.setString(3, user.getLanguage());                
                stmt.setString(4, user.getEmail());
                stmt.setString(5, user.getPassword());
                //stmt.setInt(6, user.getIdLocalization());
                //stmt.setString(8, user.getStatusAccount().toString());
                stmt.setInt(6, user.getIdUser());
            
                stmt.executeUpdate();            
                //u = read(user.getIdUser());                  
                this.connection.commit();
            
            } catch(SQLException e) {
                this.connection.rollback();
                throw e;
            }
            finally {                
                try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
                
        return u;
    }

    public int delete(int id) throws SQLException, ClassNotFoundException {
        int affectedRows = 0;
        String sqlQuery = "DELETE FROM user WHERE idUser = ?;";
        
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
    
    public User read(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE idUser = ?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
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
    
    public double getScore(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT score FROM user WHERE idUser = ?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return rs.getDouble("score");
            }            
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }      
        return -1;
    }
    
    public List<User> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user ORDER by idUser";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            rs = stmt.executeQuery();
            
            List<User> users = new ArrayList();
            
            while(rs.next()) {
                users.add(parser(rs));                
            }
            
            return users;         
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }        
    }
    
    private User parser(ResultSet rs) throws SQLException {
        User u = new User();
        
        u.setIdUser(rs.getInt("idUser"));
        u.setName(rs.getString("name"));
        u.setDateOfBirth(rs.getString("dateOfBirth"));
        u.setLanguage(rs.getString("language"));        
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setIdLocalization(rs.getInt("id_localization"));        
        u.setStatusAccount(StatusUser.valueOf(rs.getString("statusAccount")));
        u.setScore(rs.getDouble("score"));
        
        return u;
    }
    
    public User login(User u) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE email = ? and password =? and statusAccount = 'Active';";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getPassword());
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
    
     public String getPass(String email) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT password FROM user WHERE email = ?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return rs.getString("password");
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
    
    public boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE email = ?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if(rs.next()) {               
                return true; //existe cadastro
            }            
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }			
        }
        return false;
    }    
    
    public void updateScore(int idUser, double score) throws SQLException, ClassNotFoundException {
        String sqlQuery = "UPDATE user SET score = ? WHERE idUser = ?;";
        PreparedStatement stmt = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            
            stmt.setDouble(1, score);               
            stmt.setInt(2, idUser);
           
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
    
    /*public boolean disable(int id) {
        String sqlQuery = "UPDATE user SET statusAccount='' "
    }*/
}
