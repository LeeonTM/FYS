/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import com.mycompany.fys.DbClasses.*;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Leon
 */
public class LoginController extends BaseController {

    @FXML
    private AnchorPane basePane;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField passWord;
    
    @FXML
    private Label loginError;
    
    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblPassword;
    
    @FXML 
    private JFXButton btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(super.applicatieTaal == null || super.applicatieTaal == "Nederlands"){
            changeNederlands();
        }
        else{
            changeEnglish();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {

        LinkedList result = super.repo.executeSelect("user", new String[]{"Username", "Password"}, new String[]{userName.getText(), passWord.getText()});
        
        if (!result.isEmpty()) {
            User user = new User();
            user.fromLinkedList((LinkedList)result.get(0));
            BaseController.loggedInUser = user;
            
            super.swapScene(event, "Overview.fxml");
        }
        else{
            passWord.setText("");
            loginError.setText("Gebruikersnaam en/of wachtwoord is incorrect.");
        }
    }
    
    private void changeEnglish(){
        lblUsername.setText("Username");
        lblPassword.setText("Password");
        btnLogin.setText("Login");
    }
    
    private void changeNederlands(){
        lblUsername.setText("Gebruikersnaam");
        lblPassword.setText("Wachtwoord");
        btnLogin.setText("Inloggen");
    }
}
