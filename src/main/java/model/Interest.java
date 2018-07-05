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
public class Interest {
    private int idInterest;
    private String name;
    
    public Interest() {        
    }
    
    public Interest(int idInterest, String name) {
        this.idInterest = idInterest;
        this.name = name;
    }

    public int getIdInterest() {
        return idInterest;
    }

    public void setIdInterest(int idInterest) {
        this.idInterest = idInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Interest{" + "idInterest=" + idInterest + ", name=" + name + '}';
    }        
}
