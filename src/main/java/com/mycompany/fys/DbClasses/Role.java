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
public class Role {
    
    public int id;
    public String name;

    public void fromLinkedList(LinkedList role){
        this.setId(Integer.parseInt(role.get(0).toString()));
        this.setName(role.get(1) == null ? "" : role.get(1).toString());
    }
    
    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }
}
