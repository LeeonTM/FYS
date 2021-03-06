/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class EditUserManagementController extends BaseController {

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

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblAirport;

    @FXML
    private JFXButton addUserButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set current username
        usernameField.setEditable(false);
        usernameField.setText(BaseController.changingUser);

        // set current email
        LinkedList<LinkedList> email = repo.executeCustomSelect("SELECT Email FROM User WHERE Username = '" + BaseController.changingUser + "'");
        emailField.setText(email.toString().replace("[", "").replace("]", ""));

        // set current password      
        LinkedList<LinkedList> password = repo.executeCustomSelect("SELECT Password FROM User WHERE Username = '" + BaseController.changingUser + "'");
        passwordField.setText(password.toString().replace("[", "").replace("]", ""));
        repeatPassField.setText(password.toString().replace("[", "").replace("]", ""));

        // Create all inputs automatically from the database for the comboboxes.
        // For the current airports.
        String query = "select count(Id) from Airport";
        LinkedList airports = repo.executeCustomSelect(query);
        int limit = Integer.parseInt(airports.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Airport where Id = " + i);
            airportField.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }
        // For the current roles within the application.
        String query2 = "select count(Id) from Role";
        LinkedList roles = repo.executeCustomSelect(query);
        int limit1 = Integer.parseInt(roles.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit1; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Role where Id = " + i);
            roleField.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }

        if (super.applicatieTaal == null || super.applicatieTaal == "Nederlands") {
            changeNederlands();
        } else {
            changeEnglish();
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
    private void editUserInDB(ActionEvent event) throws IOException {
        if (passwordField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        } else {
            LinkedList resultRole = repo.executeSelect("role", new String[]{"Name"}, new String[]{(String) roleField.getValue()});
            Role role = new Role();
            role.fromLinkedList((LinkedList) resultRole.get(0));

            LinkedList resultAirport = repo.executeSelect("airport", new String[]{"Name"}, new String[]{(String) airportField.getValue()});
            Airport airport = new Airport();
            airport.fromLinkedList((LinkedList) resultAirport.get(0));

            repo.executeUpdate("User", usernameField.getText(), "Username", new String[]{"Password", "Email", "RoleId", "AirportId"}, new String[]{passwordField.getText(), emailField.getText(), Integer.toString(role.getId()), Integer.toString(airport.getId())});

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Gebruiker met accountnaam " + usernameField.getText() + " is aangepast!");
            alert.showAndWait();

            super.swapScene(event, "userManagement.fxml");
        }
    }

    private void changeNederlands() {
        lblUsername.setText("Gebruikersnaam");
        lblPassword.setText("Wachtwoord");
        lblEmail.setText("Emailadres");
        lblRole.setText("Accountrechten");
        lblAirport.setText("Werkzaam op vliegveld");
        addUserButton.setText("Maak account aan!");
    }

    private void changeEnglish() {
        lblUsername.setText("Username");
        lblPassword.setText("Password");
        lblEmail.setText("Email");
        lblRole.setText("Role");
        lblAirport.setText("Airport");
        addUserButton.setText("Add account!");
    }
    
    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }
}
