/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys.DbClasses;

import java.util.Date;

/**
 *
 * @author Leon
 */
public class Repatriation {
    
    public int id;
    public String fromAirport;
    public String toAddress;
    public String transporter;
    public String transporterType;
    public Date date;
    public int statusId;
    public int passengerId;
    public int luggageId;
    public Date updatedAt;
    public Date createdAt;
    public Date deletedAt;
    public boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String FromAirport) {
        this.fromAirport = FromAirport;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String ToAddress) {
        this.toAddress = ToAddress;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String Transporter) {
        this.transporter = Transporter;
    }

    public String getTransporterType() {
        return transporterType;
    }

    public void setTransporterType(String TransporterType) {
        this.transporterType = TransporterType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date Date) {
        this.date = Date;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int StatusId) {
        this.statusId = StatusId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int PassengerId) {
        this.passengerId = PassengerId;
    }

    public int getLuggageId() {
        return luggageId;
    }

    public void setLuggageId(int LuggageId) {
        this.luggageId = LuggageId;
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
