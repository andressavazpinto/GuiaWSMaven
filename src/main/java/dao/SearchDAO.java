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
        s.setStatus(StatusSearch.valueOf(rs.getString("status")));
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
            stmt.close();
            rs.close();			
        }
        return null;
    }
    
    public List<Integer> read() throws SQLException {        
        String sqlQuery = "select id_user from search where status = \'Searching\';";        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            
            List<Integer> results = new ArrayList();
            List<Search> search = new ArrayList();
            int i = 0;
            
            while(rs.next()) {
                results.add(rs.getInt("id_user"));
                i++;
            }
            return results;
        } catch(SQLException e) {
            throw e;
        }
    }
    
    public void register(Search search) throws SQLException, ClassNotFoundException {        
        String sqlQuery = "INSERT INTO search (status, id_user)"
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
    }
    
    public void update(Search search) throws SQLException {        
        String sqlQuery = "UPDATE search SET status = ?"
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
    }
}