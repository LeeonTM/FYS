/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys.DbClasses;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Leon
 */
public class Passenger {
    
    public int id;
    public String firstname;
    public String insertion;
    public String lastname;
    public String email;
    public long phone;
    public int addressId;
    public Date updatedAt;
    public Date createdAt;
    public Date deletedAt;
    public boolean isDeleted;

    public void fromLinkedList(LinkedList passenger){
        this.setId(Integer.parseInt(passenger.get(0).toString()));
        this.setFirstname(passenger.get(1).toString());
        this.setInsertion(passenger.get(2).toString());
        this.setLastname(passenger.get(3).toString());
        this.setEmail(passenger.get(4).toString());
        this.setPhone(Long.parseLong(passenger.get(5).toString()));
        this.setAddressId(Integer.parseInt(passenger.get(6).toString()));
        this.setUpdatedAt((Date)passenger.get(7));
        this.setCreatedAt((Date)passenger.get(8));
        this.setDeletedAt((Date)passenger.get(9));
        this.setIsDeleted(Boolean.parseBoolean(passenger.get(10).toString()));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String Firstname) {
        this.firstname = Firstname;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String Insertion) {
        this.insertion = Insertion;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String Lastname) {
        this.lastname = Lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long Phone) {
        this.phone = Phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int AddressId) {
        this.addressId = AddressId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.updatedAt = UpdatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.createdAt = CreatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date DeletedAt) {
        this.deletedAt = DeletedAt;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.isDeleted = IsDeleted;
    }
}
