/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Leon
 */
public class BaseController implements Initializable {

    public Repository repo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public BaseController() {
        repo = new Repository();
    }
}
