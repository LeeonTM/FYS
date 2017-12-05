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
public class DamageClaim {
    
    public int id;
    public String description;
    public double estimatePrice;
    public String insuranceCompany;
    public int luggageId;
    public Date createdAt;
    public Date updatedAt;
    public Date deletedAt;
    public boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public double getEstimatePrice() {
        return estimatePrice;
    }

    public void setEstimatePrice(double EstimatePrice) {
        this.estimatePrice = EstimatePrice;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String InsuranceCompany) {
        this.insuranceCompany = InsuranceCompany;
    }

    public int getLuggageId() {
        return luggageId;
    }

    public void setLuggageId(int LuggageId) {
        this.luggageId = LuggageId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date CreatedAt) {
        this.createdAt = CreatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date UpdatedAt) {
        this.updatedAt = UpdatedAt;
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
