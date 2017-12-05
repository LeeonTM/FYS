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
public class User {
    
    public int id;
    public String username;
    public String password;
    public String email;
    public Date lastLoginDate;
    public int roleId;
    public int airportId;
    public Date updatedAt;
    public Date createdAt;
    public Date deletedAt;
    public boolean isDeleted;

    public void fromLinkedList(LinkedList user){
        this.setId(Integer.parseInt(user.get(0).toString()));
        this.setUsername(user.get(1).toString());
        this.setPassword(user.get(2).toString());
        this.setEmail(user.get(3).toString());
        this.setLastLoginDate((Date) user.get(4));
        this.setRoleId(Integer.parseInt(user.get(5).toString()));
        this.setAirportId(Integer.parseInt(user.get(6).toString()));
        this.setUpdatedAt((Date) user.get(7));
        this.setCreatedAt((Date) user.get(8));
        this.setDeletedAt((Date) user.get(9));
        this.setIsDeleted(Boolean.parseBoolean(user.get(10).toString()));
    }
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date LastLoginDate) {
        this.lastLoginDate = LastLoginDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int RoleId) {
        this.roleId = RoleId;
    }

    public int getAirportId() {
        return airportId;
    }

    public void setAirportId(int AirportId) {
        this.airportId = AirportId;
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
