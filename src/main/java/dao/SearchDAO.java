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
import model.Search;
import util.StatusSearch;

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
    
    public int register(Search search) throws SQLException, ClassNotFoundException {
        int id=-1;
        String sqlQuery = "INSERT INTO search (status, idUser)"
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
                        
        return id;
    }
    
    public void update(Search search) throws SQLException {        
        String sqlQuery = "UPDATE search SET status = ?"
                + " WHERE idUser = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);            
            
            stmt.setString(1, search.getStatus().toString());
            stmt.setInt(2, search.getIdUser());
            
            stmt.executeUpdate();            
            
            this.connection.commit();
            this.connection.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
    }
}