/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.StatusSearch;

/**
 *
 * @author Andressa
 */
public class Search {
    private int idSearch;
    private StatusSearch status;
    private int idUser;

    public Search() {        
    }
    
    public Search(int idSearch, StatusSearch status, int idUser) {
        this.idSearch = idSearch;
        this.status = status;
        this.idUser = idUser;
    }

    public int getIdSearch() {
        return idSearch;
    }

    public void setIdSearch(int idSearch) {
        this.idSearch = idSearch;
    }

    public StatusSearch getStatus() {
        return status;
    }

    public void setStatus(StatusSearch status) {
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }    
}
