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
public class Luggage {

    public int id;
    public String destination;
    public String labelNumber;
    public String wFCode;
    public String typeOfLuggage;
    public String brand;
    public String colour;
    public String remarks;
    public int passengerId;
    public int airportId;
    public int statusId;
    public Date updatedAt;
    public Date createdAt;
    public Date deletedAt;
    public boolean isDeleted;

    public void fromLinkedList(LinkedList luggage) {
        this.setId(Integer.parseInt(luggage.get(0).toString()));
        this.setDestination(luggage.get(1).toString());
        this.setLabelNumber(luggage.get(2).toString());
        this.setWFCode(luggage.get(3).toString());
        this.setTypeOfLuggage(luggage.get(4).toString());
        this.setBrand(luggage.get(5).toString());
        this.setColour(luggage.get(6).toString());
        this.setRemarks(luggage.get(7).toString());
        this.setPassengerId(Integer.parseInt(luggage.get(8).toString()));
        this.setAirportId(Integer.parseInt(luggage.get(9).toString()));
        this.setStatusId(Integer.parseInt(luggage.get(10).toString()));
        this.setUpdatedAt((Date) luggage.get(11));
        this.setCreatedAt((Date) luggage.get(12));
        this.setDeletedAt((Date) luggage.get(13));
        this.setIsDeleted(Boolean.parseBoolean(luggage.get(14).toString()));
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String Destination) {
        this.destination = Destination;
    }

    public String getLabelNumber() {
        return labelNumber;
    }

    public void setLabelNumber(String LabelNumber) {
        this.labelNumber = LabelNumber;
    }

    public String getWFCode() {
        return wFCode;
    }

    public void setWFCode(String WFCode) {
        this.wFCode = WFCode;
    }

    public String getTypeOfLuggage() {
        return typeOfLuggage;
    }

    public void setTypeOfLuggage(String TypeOfLuggage) {
        this.typeOfLuggage = TypeOfLuggage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String Brand) {
        this.brand = Brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String Colour) {
        this.colour = Colour;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String Remarks) {
        this.remarks = Remarks;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int PassengerId) {
        this.passengerId = PassengerId;
    }

    public int getAirportId() {
        return airportId;
    }

    public void setAirportId(int AirportId) {
        this.airportId = AirportId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int StatusId) {
        this.statusId = StatusId;
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
