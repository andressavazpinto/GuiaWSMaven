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
public class UserLocalization {
    private int idUserLocalization;
    private int idUser;
    private int idLocalization;

    public UserLocalization() {        
    }
    
    public UserLocalization(int idUserLocalization, int idUser, int idLocalization) {
        this.idUserLocalization = idUserLocalization;
        this.idUser = idUser;
        this.idLocalization = idLocalization;
    }

    public int getIdUserLocalization() {
        return idUserLocalization;
    }

    public void setIdUserLocalization(int idUserLocalization) {
        this.idUserLocalization = idUserLocalization;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(int idLocalization) {
        this.idLocalization = idLocalization;
    }

    @Override
    public String toString() {
        return "UserLocalization{" + "idUserLocalization=" + idUserLocalization + ", idUser=" + idUser + ", idLocalization=" + idLocalization + '}';
    }       
}
