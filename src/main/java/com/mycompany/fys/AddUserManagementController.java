/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.fys.DbClasses.Airport;
import com.mycompany.fys.DbClasses.Role;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class AddUserManagementController extends BaseController {

    @FXML
    private AnchorPane basePane;
    
    @FXML
    private JFXTextField usernameField;
    
    @FXML
    private JFXPasswordField passwordField;
    
    @FXML
    private JFXPasswordField repeatPassField;
    
    @FXML
    private JFXTextField emailField;
    
    @FXML
    private JFXComboBox roleField;
    
    @FXML
    private JFXComboBox airportField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String query = "select count(Id) from Airport";
        LinkedList airports = repo.executeCustomSelect(query);
        int limit = Integer.parseInt(airports.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Airport where Id = " + i);
            airportField.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }
        
        String query2 = "select count(Id) from Role";
        LinkedList roles = repo.executeCustomSelect(query);
        int limit1 = Integer.parseInt(roles.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit1; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Role where Id = " + i);
            roleField.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }
    }    

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        super.swapScene(event, "Login.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        super.swapScene(event, "Instellingen.fxml");
    }
    @FXML
    private void handleUserManage(ActionEvent event) throws IOException {
        super.swapScene(event, "userManagement.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }
    
    @FXML
    private void clearTextBox(javafx.scene.input.MouseEvent event) {
        if (usernameField.isFocused()) {
            usernameField.clear();
        }
        if (emailField.isFocused()) {
            emailField.clear();
        }
    }
    
    @FXML
    private void addUserToDB(ActionEvent event) throws IOException {
        if (usernameField.getText().trim().isEmpty()  || usernameField.getText().equals("Voer een gebruikersnaam in...") || passwordField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty() || emailField.getText().equals("Voer een emailadres in...")) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        }
        else {
        LinkedList resultRole = repo.executeSelect("role", new String[]{"Name"}, new String[]{(String)roleField.getValue()});
        Role role = new Role();
        role.fromLinkedList((LinkedList)resultRole.get(0));
        
        LinkedList resultAirport = repo.executeSelect("airport", new String[]{"Name"}, new String[]{(String)airportField.getValue()});
        Airport airport = new Airport();
        airport.fromLinkedList((LinkedList)resultAirport.get(0));
        
        repo.executeInsert("user", new String[]{"Username", "Password", "Email", "RoleId", "AirportId"}, 
               new String[]{usernameField.getText(), passwordField.getText(), emailField.getText(), Integer.toString(role.getId()), Integer.toString(airport.getId())});
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Gebruiker met accountnaam " + usernameField.getText() + " is aangemaakt!");
            alert.showAndWait();
            
            super.swapScene(event, "userManagement.fxml");
        }
    }
}
