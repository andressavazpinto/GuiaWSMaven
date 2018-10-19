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
import model.Search;
import util.StatusSearch;
import java.util.List;

/**
 *
 * @author Andressa
 */
public class SearchDAO {
    private final ConnectionJDBC connection;
    
    public SearchDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }        
    
    private Search parser(ResultSet rs) throws SQLException {
        Search s = new Search();
        
        s.setIdSearch(rs.getInt("idSearch"));
        s.setStatus(StatusSearch.valueOf(rs.getString("statusSearch")));
        s.setIdUser(rs.getInt("id_user"));
        
        return s;
    }
    
    public Search read(int id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM search WHERE id_user = ?;";
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
    
    public List<Integer> listAllLessMe(int id) throws SQLException {        
        String sqlQuery = "SELECT id_user FROM search WHERE statusSearch = \'Searching\' AND id_user != ?;";        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            List<Integer> results = new ArrayList();
            List<Search> search = new ArrayList();
            int i = 0;
            
            while(rs.next()) {
                results.add(rs.getInt("id_user"));
                System.out.println("lista read() id: " + rs.getObject("id_user"));
                i++;
            }
            return results;
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public void register(Search search) throws SQLException, ClassNotFoundException {        
        String sqlQuery = "INSERT INTO search (statusSearch, id_user)"
                + " VALUES (?, ?);";        
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            stmt.setString(1, search.getStatus().toString());
            stmt.setInt(2, search.getIdUser());            
            
            stmt.executeUpdate();                                                              
                       
            this.connection.commit();            
        } catch (SQLException e) {
             this.connection.rollback();
             throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public void update(Search search) throws SQLException {        
        String sqlQuery = "UPDATE search SET statusSearch = ?"
                + " WHERE id_user = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            
            stmt.setString(1, search.getStatus().toString());
            stmt.setInt(2, search.getIdUser());
            
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
}