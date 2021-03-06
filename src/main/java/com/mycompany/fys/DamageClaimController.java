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
import com.jfoenix.controls.JFXTextField;
import com.mycompany.fys.DbClasses.DamageClaim;
import java.util.LinkedList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

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
    private TableColumn Id;
    @FXML
    private TableColumn Description;
    @FXML
    private TableColumn InsuranceCompany;
    @FXML
    private TableColumn LuggageId;
    @FXML
    private TableColumn EstimatePrice;
    @FXML
    private TableView damageClaimTable;
    @FXML
    private JFXTextField filterField;

    private ObservableList<DamageClaim> list = FXCollections.observableArrayList();
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
        
        refreshDamageData();
        
        FilteredList<DamageClaim> filteredData = new FilteredList<>(list, p -> true);
        
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(DamageClaim -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare everything with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (DamageClaim.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Schade Description
                } else if (Integer.toString(DamageClaim.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Schade ID
                } else if (Double.toString(DamageClaim.getEstimatePrice()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Estimated price
                } else if (DamageClaim.getInsuranceCompany().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches verzekeringsmaatschappij
                }
                return false;
            });
        });
            
        SortedList<DamageClaim> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(damageClaimTable.comparatorProperty());
        
        damageClaimTable.setItems(sortedData);
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
    
    private void refreshDamageData () {
        //ObservableList<DamageClaim> list = FXCollections.observableArrayList();
        LinkedList result = repo.executeCustomSelect("SELECT * FROM DamageClaim WHERE isDeleted = 0");
        for(Object a : result){
            DamageClaim damageclaim = new DamageClaim();
            damageclaim.fromLinkedList((LinkedList)a);
            list.add(damageclaim);
        }
        
        for (int cnr = 0; cnr < damageClaimTable.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn)damageClaimTable.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            }
        }
        damageClaimTable.setItems(list);
    }
    
    @FXML
    private void handleEditDamage(ActionEvent event) throws IOException {
        DamageClaim selectedItem = (DamageClaim) damageClaimTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        }
        else {
        BaseController.changingDamage = selectedItem.getId();
        super.swapScene(event, "editDamageClaim.fxml");
        }
    }
    
    @FXML
    private void delDamageFromTable(ActionEvent event) {
        
        DamageClaim selectedItem = (DamageClaim) damageClaimTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bevestig actie");
            alert.setHeaderText("Verwijderen van een rij uit de tabel");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Weet je zeker dat je deze rij met schadenummer " + selectedItem.getId() + " wilt verwijderen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                repo.executeUpdateQuery("UPDATE DamageClaim SET isDeleted = 1 WHERE Id ='" + selectedItem.getId() + "'");
                refreshDamageData();
            }
        }
    }

    private void changeNederlands() {
        btnAdd.setText("Voeg schadeclaim toe");
        btnEdit.setText("Verander schadeclaim");
        btnDelete.setText("Verwijder schadeclaim");
        Id.setText("Schadeclaimnummer");
        Description.setText("Description");
        InsuranceCompany.setText("Verzekeringsmaatschappij");
        LuggageId.setText("Bagagenummer");
        EstimatePrice.setText("Geschatte prijs");
    }

    private void changeEnglish() {
        btnAdd.setText("Add damageclaim");
        btnEdit.setText("Edit damageclaim");
        btnDelete.setText("Delete damageclaim");
        Id.setText("Damageclaim number");
        Description.setText("Description");
        InsuranceCompany.setText("Insurance company");
        LuggageId.setText("Luggage Number");
        EstimatePrice.setText("Repair costs");
    }
}
