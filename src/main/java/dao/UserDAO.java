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
import util.Status;
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
        String sqlQuery = "INSERT INTO user (name, dateOfBirth, language, occupation, email,"
                + "password, localization, statusAccount) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        String sqlQuery2 = "SELECT LAST_INSERT_ID() AS 'aux';";
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getDateOfBirth());
            stmt.setString(3, user.getLanguage());
            stmt.setString(4, user.getOccupation());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getLocalization());
            stmt.setString(8, user.getStatusAccount().toString());
            
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
                        
        return id;
    }
    
    public User update(User user) throws SQLException, ClassNotFoundException {
        User u = new User();
        String sqlQuery = "UPDATE user SET name = ?, dateOfBirth = ?, language = ?, occupation = ?,"
                + "email = ?, password = ?, localization = ? WHERE idUser = ?;";
        
        String sqlQuery2 = "UPDATE user SET name = ?, dateOfBirth = ?, language = ?, occupation = ?,"
                + "email = ?, localization = ? WHERE idUser = ?;";
        
        if(user.getPassword() == null ) {
            try {
                PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery2);
            
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getDateOfBirth());
                stmt.setString(3, user.getLanguage());
                stmt.setString(4, user.getOccupation());
                stmt.setString(5, user.getEmail());            
                stmt.setString(6, user.getLocalization());
                //stmt.setString(8, user.getStatusAccount().toString());
                stmt.setInt(7, user.getIdUser());
            
                stmt.executeUpdate();
            
                u = read(user.getIdUser());
                
                this.connection.commit();
            
            } catch(SQLException e) {
                this.connection.rollback();
                throw e;
            }
        }
        else {
            try {
                PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getDateOfBirth());
                stmt.setString(3, user.getLanguage());
                stmt.setString(4, user.getOccupation());
                stmt.setString(5, user.getEmail());
                stmt.setString(6, user.getPassword());
                stmt.setString(7, user.getLocalization());
                //stmt.setString(8, user.getStatusAccount().toString());
                stmt.setInt(8, user.getIdUser());
            
                stmt.executeUpdate();
            
                u = read(user.getIdUser());
                        
                this.connection.commit();
            
            } catch(SQLException e) {
                this.connection.rollback();
                throw e;
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
        
        return affectedRows;
    }
    
    public User read(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE idUser = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                return parser(rs);
            }            
        } catch(SQLException e) {
            throw e;
        }
        return null;
    }
    
    public List<User> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user ORDER by idUser";       
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            ResultSet rs = stmt.executeQuery();
            
            List<User> users = new ArrayList();
            
            while(rs.next()) {
                users.add(parser(rs));                
            }
            
            return users;         
        } catch(SQLException e) {
            throw e;
        }
    }
    
    private User parser(ResultSet rs) throws SQLException {
        User u = new User();
        
        u.setIdUser(rs.getInt("idUser"));
        u.setName(rs.getString("name"));
        u.setDateOfBirth(rs.getString("dateOfBirth"));
        u.setLanguage(rs.getString("language"));
        u.setOccupation(rs.getString("occupation"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setLocalization(rs.getString("localization"));        
        u.setStatusAccount(Status.valueOf(rs.getString("statusAccount")));
        
        return u;
    }
    
    public User login(User u) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE email = ? and password =? and statusAccount = 'Active';";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getPassword());
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                return parser(rs);
            }            
        } catch(SQLException e) {
            throw e;
        }
        return null;
    }
    
    
    public boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM user WHERE email = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {               
                return true; //existe cadastro
            }            
        } catch(SQLException e) {
            throw e;
        }
        return false;
    }    
    
    /*public boolean disable(int id) {
        String sqlQuery = "UPDATE user SET statusAccount='' "
    }*/
}
