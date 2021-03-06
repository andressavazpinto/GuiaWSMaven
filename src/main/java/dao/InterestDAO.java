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
import model.Interest;

/**
 *
 * @author Andressa
 */
public class InterestDAO {
    private final ConnectionJDBC connection;
    
    public InterestDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }
    
    public List<Interest> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM interest ORDER by idInterest";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            
            List<Interest> interests = new ArrayList();
            
            while(rs.next()) {
                interests.add(parser(rs));                
            }            
            return interests;         
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    private Interest parser(ResultSet rs) throws SQLException {
        Interest i = new Interest();
        
        i.setIdInterest(rs.getInt("idInterest"));
        i.setName(rs.getString("name"));
        
        return i;
    }        
}
