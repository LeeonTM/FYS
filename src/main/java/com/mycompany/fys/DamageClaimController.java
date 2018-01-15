/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class DamageClaimController extends BaseController {

    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXButton managerButton;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colInsurance;
    @FXML
    private TableColumn colNumber;
    @FXML
    private TableColumn colCosts;

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
    private void handleAddDamageClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "addDamageClaim.fxml");
    }

    private void changeNederlands() {
        btnAdd.setText("Voeg schadeclaim toe");
        btnEdit.setText("Verander schadeclaim");
        btnDelete.setText("Verwijder schadeclaim");
        colId.setText("Schadeclaimnummer");
        colDescription.setText("Description");
        colInsurance.setText("Verzekeringsmaatschappij");
        colNumber.setText("Bagagenummer");
        colCosts.setText("Geschatte prijs");
    }

    private void changeEnglish() {
        btnAdd.setText("Add damageclaim");
        btnEdit.setText("Edit damageclaim");
        btnDelete.setText("Delete damageclaim");
        colId.setText("Damageclaim number");
        colDescription.setText("Description");
        colInsurance.setText("Insurance company");
        colNumber.setText("LuggageNumber");
        colCosts.setText("Repair costs");
    }
}
