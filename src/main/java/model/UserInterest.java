/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andressa
 */
public class UserInterest {
    private int idUserInterest;
    private int idUser;
    private int idInterest; 
    
    public UserInterest() {        
    }
    
    public UserInterest(int idUserInterest, int idUser, int idInterest) {
        this.idUserInterest = idUserInterest;
        this.idUser = idUser;
        this.idInterest = idInterest;
    }

    public int getIdUserInterest() {
        return idUserInterest;
    }

    public void setIdUserInterest(int idUserInterest) {
        this.idUserInterest = idUserInterest;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdInterest() {
        return idInterest;
    }

    public void setIdInterest(int idInterest) {
        this.idInterest = idInterest;
    }

    @Override
    public String toString() {
        return "UserInterest{" + "idUserInterest=" + idUserInterest + ", idUser=" + idUser + ", idInterest=" + idInterest + '}';
    }        
}
