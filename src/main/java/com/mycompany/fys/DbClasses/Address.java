/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys.DbClasses;

import java.util.LinkedList;

/**
 *
 * @author Leon
 */
public class Address {
    
    public int id;
    public String street;
    public int number;
    public String place;
    public String postalCode;
    public String country;

    public void fromLinkedList(LinkedList address){
        this.setId(Integer.parseInt(address.get(0).toString()));
        this.setStreet(address.get(1).toString() == null ? "" : address.get(1).toString());
        this.setNumber(Integer.parseInt(address.get(2).toString()));
        this.setPlace(address.get(3).toString() == null ? "" : address.get(3).toString());
        this.setPostalCode(address.get(4).toString() == null ? "" : address.get(4).toString());
        this.setCountry(address.get(5).toString() == null ? "" : address.get(5).toString());
    }
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String Street) {
        this.street = Street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int Number) {
        this.number = Number;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String Place) {
        this.place = Place;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.postalCode = PostalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String Country) {
        this.country = Country;
    }
}
