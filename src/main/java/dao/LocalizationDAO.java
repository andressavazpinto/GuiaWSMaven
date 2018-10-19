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
import model.Localization;

/**
 *
 * @author Andressa
 */
public class LocalizationDAO {
    private final ConnectionJDBC connection;
    
    public LocalizationDAO() throws SQLException, ClassNotFoundException {
        this.connection = new ConnectionMySQLJDBC();
    }
    
    public int register(Localization localization) throws SQLException, ClassNotFoundException {
        int id=-1;
        String sqlQuery = "INSERT INTO localization (latitude, longitude, city, uf, country)"
                + " VALUES (?, ?, ?, ?, ?);";
        String sqlQuery2 = "SELECT LAST_INSERT_ID() AS 'aux';"; //retornar o id da localização inserida
                
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setDouble(1, localization.getLatitude());
            stmt.setDouble(2, localization.getLongitude());
            stmt.setString(3, localization.getCity());
            stmt.setString(4, localization.getUf());
            stmt.setString(5, localization.getCountry());            
            
            stmt.executeUpdate();                                    
            PreparedStatement stmt2 = this.connection.getConnection().prepareStatement(sqlQuery2);            
            ResultSet rs = stmt2.executeQuery();            
            
            if(rs.next())
                System.out.println(id+"");           
                id = rs.getInt("aux");    
                System.out.println(id+"");
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
    
    public void update(Localization localization) throws SQLException {        
        String sqlQuery = "UPDATE localization SET latitude = ?, longitude = ?, city = ?, uf = ?,"
                + "country = ? WHERE idLocalization = ?;";
        
        try {
            PreparedStatement stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            stmt.setDouble(1, localization.getLatitude());
            stmt.setDouble(2, localization.getLongitude());
            stmt.setString(3, localization.getCity());
            stmt.setString(4, localization.getUf());
            stmt.setString(5, localization.getCountry());
            stmt.setInt(6, localization.getIdLocalization());
            stmt.executeUpdate();            
            
            this.connection.commit();
            this.connection.rollback();
            
        } catch(SQLException e) {
            throw e;
        }
        finally {            
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public List<Localization> list() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM localization ORDER by idLocalization";       
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = this.connection.getConnection().prepareStatement(sqlQuery);
            rs = stmt.executeQuery();
            
            List<Localization> locs = new ArrayList();
            
            while(rs.next()) {
                locs.add(parser(rs));                
            }
            
            return locs;         
        } catch(SQLException e) {
            throw e;
        }
        finally {
            try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { stmt.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    private Localization parser(ResultSet rs) throws SQLException {
        Localization l = new Localization();
        
        l.setIdLocalization(rs.getInt("idLocalization"));
        l.setLatitude(rs.getDouble("latitude"));
        l.setLongitude(rs.getDouble("longitude"));
        l.setCity(rs.getString("city"));
        l.setUf(rs.getString("uf"));
        l.setCountry(rs.getString("country"));       
        
        return l;
    }
}
