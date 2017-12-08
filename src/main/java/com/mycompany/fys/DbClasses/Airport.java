/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys.DbClasses;

/**
 *
 * @author Leon
 */
public class Airport {
    
    public int id;
    public String iATACode;
    public String name;
    public String country;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getIATACode() {
        return iATACode;
    }

    public void setIATACode(String IATACode) {
        this.iATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String Country) {
        this.country = Country;
    }
}
