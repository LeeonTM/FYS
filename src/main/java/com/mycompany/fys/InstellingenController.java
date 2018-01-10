/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.mycompany.fys.DbClasses.*;
import java.util.LinkedList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Yannick de Graaff
 */
public class InstellingenController extends BaseController {

    @FXML
    private AnchorPane basePane;
    
    @FXML
    private JFXComboBox jfxCombo;
    
    @FXML
    private JFXButton managerButton;
    
    @FXML
    private JFXPasswordField oldPassField;

    @FXML
    private JFXPasswordField passField;
    
    @FXML
    private JFXPasswordField repeatPassField;
    
    @FXML
    private Label lblChangeTaal;
    
    @FXML
    private JFXButton btnChangeTaal;
    
    @FXML
    private Label lblChangePass;
    
    @FXML
    private JFXButton btnChangePass;
    
    @FXML
    private Label lblExcel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }    
        
        // set old password
        LinkedList<LinkedList> password = repo.executeCustomSelect("SELECT Password FROM User WHERE Username = '" + BaseController.loggedInUser.getUsername() + "'");
        oldPassField.setText(password.toString().replace("[", "").replace("]", ""));
        oldPassField.setEditable(false);
        
        if(super.applicatieTaal == null || super.applicatieTaal == "Nederlands"){
            jfxCombo.getItems().add("Nederlands");
            jfxCombo.getItems().add("English");
            changeNederlands();
        }
        else{
            jfxCombo.getItems().add("English");
            jfxCombo.getItems().add("Nederlands");
            changeEnglish();
        }
        jfxCombo.getSelectionModel().select(0);
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        super.swapScene(event, "Login.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        super.swapScene(event, "Instellingenfxml");
    }

    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }
    
    @FXML
    private void handleSchadeClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "damageOverview.fxml");
    }
    
    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }
    
    @FXML
    private void handleChangeTaal(ActionEvent event) throws IOException{
        super.applicatieTaal = jfxCombo.getValue().toString();
        super.swapScene(event, "Instellingen.fxml");
    }
    
    @FXML
    private void handlePassChange(ActionEvent event) throws IOException {
        if (passField.getText().trim().isEmpty() || repeatPassField.getText().trim().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        }
        else {
            repo.executeUpdate("User", BaseController.loggedInUser.getUsername(), "Username", new String[] {"Password"}, new String[] {passField.getText()});
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Wachtwoord is met succes veranderd!");
            alert.showAndWait();
            
            super.swapScene(event, "Instellingen.fxml");
        }
    }
    
    
    private void changeEnglish(){
        lblChangeTaal.setText("Change language");
        btnChangeTaal.setText("Change language");
        btnChangeTaal.setLayoutX(btnChangeTaal.getLayoutX() - 30);
        
        lblChangePass.setText("Change password");
        btnChangePass.setText("Change password");
        btnChangePass.setLayoutX(btnChangePass.getLayoutX() + 20);
        
        lblExcel.setText("Import/export Excel file");
    }
    
    private void changeNederlands(){
        lblChangeTaal.setText("Taal instellen");
        btnChangeTaal.setText("Taal wijzigen");
        
        lblChangePass.setText("Wachtwoord wijzigen");
        btnChangePass.setText("Wachtwoord wijzigen");
        
        lblExcel.setText("Excel bestand importeren/exporteren");
    }
}
