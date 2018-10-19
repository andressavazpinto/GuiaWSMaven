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
import model.SearchByRegion;
import util.StatusSearch;

/**
 *
 * @author Andressa
 */
public class SearchByRegionDAO {
    private final ConnectionJDBC connection;
    
    public SearchByRegionDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }  
    
    public List<SearchByRegion> getRegions(int id) throws SQLException {
        String sqlQuery = "SELECT DISTINCT localization.city, localization.country, search.statusSearch "
                + "FROM localization, user, search WHERE (search.statusSearch = ? AND"
                + " localization.idLocalization = user.id_localization and user.idUser = search.id_user "
                + "AND id_user != ?) ORDER BY localization.country";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, "Searching");
            stmt.setInt(2, id);
            rs = stmt.executeQuery();
            
            List<SearchByRegion> regions = new ArrayList();
            
            while(rs.next()) {
                regions.add(parserRegion(rs));
            }               
            return regions;
            
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public List<SearchByRegion> getUsersCity(String city, int id) throws SQLException {
        String sqlQuery = "SELECT localization.city, localization.country, user.idUser, search.statusSearch "
                + "FROM localization, user, search WHERE (search.statusSearch = ? "
                + "AND localization.idLocalization = user.id_localization AND"
                + " user.idUser = search.id_user and localization.city = ? AND id_user != ?)"
                + " ORDER BY localization.country;";
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, "Searching");
            stmt.setString(2, city);
            stmt.setInt(3, id);
            rs = stmt.executeQuery();                        
            
            List<SearchByRegion> usersCity = new ArrayList();
            
            while(rs.next()) {
                usersCity.add(parserRegion(rs));
            }   
            
            return usersCity;
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    private SearchByRegion parserRegion(ResultSet rs) throws SQLException {
        SearchByRegion s = new SearchByRegion();
        
        s.setCity(rs.getString("city"));
        s.setCountry(rs.getString("country"));
        s.setStatusSearch(StatusSearch.valueOf(rs.getString("statusSearch")));
        try {
            s.setIdUser(rs.getInt("idUser"));
        } catch(Exception e) {
            s.setIdUser(0);
        }
        return s;
    }
}
