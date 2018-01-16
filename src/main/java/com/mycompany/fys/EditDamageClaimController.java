/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class EditDamageClaimController extends BaseController {

    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXButton editClaimButton;
    @FXML
    private JFXTextArea descriptionField;
    @FXML
    private JFXTextField insuranceField;
    @FXML
    private JFXTextField estiPriceField;
    @FXML
    private JFXTextField luggageField;
    @FXML
    private JFXButton managerButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }  
        // set Description text
        LinkedList<LinkedList> description = repo.executeCustomSelect("SELECT Description FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'");
        descriptionField.setText(description.toString().replace("[", "").replace("]", ""));
        
       // set Insurance company text
        LinkedList<LinkedList> insuranceCompany = repo.executeCustomSelect("SELECT InsuranceCompany FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'");
        insuranceField.setText(insuranceCompany.toString().replace("[", "").replace("]", ""));
        
        // set Estimated Price
        LinkedList<LinkedList> estimatedPrice = repo.executeCustomSelect("SELECT EstimatePrice FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'");
        estiPriceField.setText(estimatedPrice.toString().replace("[", "").replace("]", ""));
        
        // set Luggage ID
        LinkedList<LinkedList> luggageNumber = repo.executeCustomSelect("SELECT LuggageId FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'");
        luggageField.setText(luggageNumber.toString().replace("[", "").replace("]", ""));
    }    

@FXML
    private void handleSchadeClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "damageOverview.fxml");
    }

    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        super.swapScene(event, "Instellingen.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        super.swapScene(event, "Login.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }
    
    @FXML
    private void editDamageClaim(ActionEvent event) throws IOException {
        if (descriptionField.getText().trim().isEmpty()  || insuranceField.getText().trim().isEmpty() || estiPriceField.getText().trim().isEmpty()
                || luggageField.getText().trim().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        }
        else {
            LinkedList<LinkedList> schadeId = repo.executeCustomSelect("SELECT Id FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'");
            repo.executeUpdate("DamageClaim", schadeId.toString().replace("[", "").replace("]", ""), "Id", new String[] {"Description", "EstimatePrice", "InsuranceCompany", "LuggageId"}, new String[] {descriptionField.getText(), estiPriceField.getText(), insuranceField.getText(), luggageField.getText()});
       
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Schadeclaim voor koffer met schadenummer " + repo.executeCustomSelect("SELECT Id FROM DamageClaim WHERE Id = '" + BaseController.changingDamage + "'").toString().replace("[", "").replace("]", "") + " is bijgewerkt!");
            alert.showAndWait();
            
            super.swapScene(event, "damageOverview.fxml");
            
        }
    }
    
    @FXML
    private void handleHelp(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informatie");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Deze functie is nog in ontwikkeling!");
        alert.showAndWait();
    }
}
