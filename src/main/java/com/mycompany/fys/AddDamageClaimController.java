/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.fys.DbClasses.Airport;
import com.mycompany.fys.DbClasses.Role;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
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
public class AddDamageClaimController extends BaseController {

    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXButton managerButton;
    @FXML
    private Label lblCreateClaim;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblInsurence;
    @FXML
    private Label lblCost;
    @FXML
    private Label lblBagageNum;
    @FXML
    private JFXButton addClaimButton;
    @FXML
    private JFXTextArea descriptionField;
    @FXML
    private JFXTextField insuranceField;
    @FXML
    private JFXTextField estimateField;
    @FXML
    private JFXTextField luggageField;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }  
        
        if (super.applicatieTaal == null || super.applicatieTaal == "Nederlands") {
            changeNederlands();
        } else {
            changeEnglish();
        }
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
    private void addDamageToDB(ActionEvent event) throws IOException {
        if (descriptionField.getText().trim().isEmpty() || insuranceField.getText().trim().isEmpty()
                || estimateField.getText().trim().isEmpty() || luggageField.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        } else {

            repo.executeInsert("DamageClaim", new String[]{"Description", "InsuranceCompany", "EstimatePrice", "LuggageId"},
                    new String[]{descriptionField.getText(), insuranceField.getText(), estimateField.getText(), luggageField.getText()});

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Schadeclaim met schadenummer " + repo.executeCustomSelect("SELECT Id FROM DamageClaim WHERE Description = '" + descriptionField.getText() + "'").toString().replace("[", "").replace("]", "") + " is aangemaakt!");
            alert.showAndWait();

            super.swapScene(event, "damageOverview.fxml");
        }
    }    
        
    private void changeNederlands(){
        lblCreateClaim.setText("Schadeclaim aanmaken");
        lblDescription.setText("Schade omschrijving");
        lblInsurence.setText("Verzekeringmaatschappij");
        lblCost.setText("Geschatte kosten schade");
        lblBagageNum.setText("Bagagenummer dat bij de schadeclaim hoort");
        addClaimButton.setText("Maak schadeclaim aan!");
    }
    
    private void changeEnglish(){
        lblCreateClaim.setText("Create damageclaim");
        lblDescription.setText("Damage description");
        lblInsurence.setText("Insurance company");
        lblCost.setText("Repair costs");
        lblBagageNum.setText("Luggagenumber with the damageclaim");
        addClaimButton.setText("Create damageclaim!");
    }
}
