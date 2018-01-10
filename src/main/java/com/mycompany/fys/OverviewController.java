/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mycompany.fys.DbClasses.Luggage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 *
 * @author Leon
 */
public class OverviewController extends BaseController {

    static Luggage selectedItem;

    @FXML
    private TableView overviewtable;
    @FXML
    private JFXButton managerButton;
    @FXML
    private AnchorPane basePane;
    @FXML
    private TableColumn passengerId;
    @FXML
    private TableColumn createdAt;
    @FXML
    private TableColumn flightNumber;
    @FXML
    private TableColumn remarks;
    @FXML
    private TableColumn statusId;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }
        ObservableList<Luggage> list = FXCollections.observableArrayList();
        LinkedList result = super.repo.executeCustomSelect("select * from luggage where isDeleted = 0");
        for (Object a : result) {
            Luggage luggage = new Luggage();
            luggage.fromLinkedList((LinkedList) a);

            list.add(luggage);
        }

        for (int cnr = 0; cnr < overviewtable.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) overviewtable.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            }
        }
        overviewtable.setItems(list);
        
        if(super.applicatieTaal == null || super.applicatieTaal == "Nederlands"){
            changeNederlands();
        }
        else{
            changeEnglish();
        }
    }

    private void refreshtable() {
        ObservableList<Luggage> list = FXCollections.observableArrayList();
        LinkedList result = super.repo.executeCustomSelect("select * from luggage where isDeleted = 0");
        for (Object a : result) {
            Luggage luggage = new Luggage();
            luggage.fromLinkedList((LinkedList) a);

            list.add(luggage);
        }

        for (int cnr = 0; cnr < overviewtable.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) overviewtable.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            }
        }
        overviewtable.setItems(list);
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
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }

    @FXML
    private void handleSchadeClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "damageOverview.fxml");
    }

    @FXML
    private void handleAddLuggage(ActionEvent event) throws IOException {
        super.swapScene(event, "addMissingLuggage.fxml");
    }
    
    @FXML
    private void handleEditLuggage(ActionEvent event) throws IOException {
        super.swapScene(event, "editLuggage.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }

    @FXML
    private void handleEditRecord(ActionEvent event) throws IOException {

        Luggage selectedItem = (Luggage) overviewtable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        } else {
            BaseController.luggageId = Integer.toString(selectedItem.getId());
            handleEditLuggage(event);
        }
    }

    @FXML
    private void delItemFromTable(ActionEvent event) {

        Luggage selectedItem = (Luggage) overviewtable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bevestig actie");
            alert.setHeaderText("Verwijderen van een rij uit de tabel");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Weet je zeker dat je deze rij met de luggageid " + selectedItem.getId() + " wilt verwijderen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                repo.executeUpdateQuery("UPDATE luggage SET isDeleted = 1 WHERE Id ='" + selectedItem.getId() + "'");
                refreshtable();
            }
        }
    }
    
    public void changeNederlands(){
        btnEdit.setText("Wijzig");
        btnDelete.setText("Verwijderen");
        btnAdd.setText("Nieuwe bagage");
        
        passengerId.setText("Naam van eigenaar");
        createdAt.setText("Aanmelddatum");
        flightNumber.setText("Vluchtnummer");
        remarks.setText("Bagage kenmerken");
    }
    
    public void changeEnglish(){
        btnEdit.setText("Edit");
        btnDelete.setText("Delete");
        btnAdd.setText("New luggage");
        
        passengerId.setText("Owner name");
        createdAt.setText("Create date");
        flightNumber.setText("Flightnumber");
        remarks.setText("Luggage remarks");
    }
}
