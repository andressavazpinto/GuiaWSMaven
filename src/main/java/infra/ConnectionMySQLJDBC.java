/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andressa
 */
public class ConnectionMySQLJDBC implements ConnectionJDBC {
    
    Connection connection = null;
    String host = "jdbc:mysql://localhost:3306/Guia";
    String user = "root";
    String password = "9114115693532601";
         
    public ConnectionMySQLJDBC() throws SQLException, ClassNotFoundException {    
         Class.forName("com.mysql.jdbc.Driver"); 
         //this.connection = DriverManager.getConnection("jdbc:mysql://guia.cdua7jybmdpy.us-east-2.rds.amazonaws.com:3306/Guia", "Dessa201405159", "201405159");         
         this.connection = DriverManager.getConnection(host, user, password);
         this.connection.setAutoCommit(false);
    }

    @Override
    public Connection getConnection() {        
        return this.connection;
    }

    @Override
    public void close() {
        if(this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionMySQLJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         
    }

    @Override
    public void commit() throws SQLException {
        this.connection.commit();
        this.close();
    }

    @Override
    public void rollback() {
        if(this.connection != null) {
            try {
                this.connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionMySQLJDBC.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                this.close();
            }
        }  
    }        
}
