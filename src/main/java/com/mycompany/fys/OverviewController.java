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
import com.jfoenix.controls.JFXTextArea;
import com.mycompany.fys.DbClasses.Luggage;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
    @FXML
    private JFXButton btnMatch;
    @FXML
    private JFXTextArea filterField;
    
    private String matchIdRecord;

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

        if (super.applicatieTaal == null || super.applicatieTaal == "Nederlands") {
            changeNederlands();
        } else {
            changeEnglish();
        }

        if (fromAddMissingLuggage == 1) {
            Luggage luggage = new Luggage();
            LinkedList idVanGemaakteLuggage = super.repo.executeCustomSelect("select max(Id) FROM Luggage");
            int id = Integer.parseInt(idVanGemaakteLuggage.toString().replace("[", "").replace("]", ""));
            LinkedList luggageInfo = super.repo.executeCustomSelect("select * FROM Luggage where id = " + id);    
            for (Object a : luggageInfo) {
                luggage.fromLinkedList((LinkedList) a);
            }
            fromAddMissingLuggage = 0;
            selectedItem = luggage;
            verwerking();
        }
        
            FilteredList<Luggage> filteredData = new FilteredList<>(list, p -> true);
        
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Luggage -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Compare everything with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Luggage.getRemarks().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Description
                } else if (Integer.toString(Luggage.getPassengerId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Passenger IDs
                } else if (Luggage.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Brands
                } else if (Integer.toString(Luggage.getStatusId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Statusses
                } else if (Integer.toString(Luggage.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Luggage ID's
                } else if (Luggage.getColour().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Colours
                } else if (Luggage.getDestination().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Destinations
                } else if (Luggage.getFlightNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Flight numbers
                } else if (Luggage.getLabelNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Label numbers
                } else if (Luggage.getTypeOfLuggage().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Type of Luggages
                } else if (Luggage.getWFCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Luggage ID's
                }
                return false;
            });
        });
            
        SortedList<Luggage> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(overviewtable.comparatorProperty());
        
        overviewtable.setItems(sortedData);
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
            warning();
        } else {
            BaseController.luggageId = Integer.toString(selectedItem.getId());
            handleEditLuggage(event);
        }
    }

    @FXML
    private void delItemFromTable(ActionEvent event) {

        Luggage selectedItem = (Luggage) overviewtable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            warning();
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

    private void warning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informatie");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
        alert.showAndWait();
    }

    @FXML
    private void handleMatchRecord(ActionEvent event) throws IOException {
        selectedItem = (Luggage) overviewtable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            warning();
        } else {
            verwerking();
        }
    }

    private void verwerking() {
        ArrayList matchId = new ArrayList();
        matchIdRecord = Integer.toString(selectedItem.getId());
        matchFunctie(matchId);
        if (matchId.isEmpty()) {
            showAlertbox();
        } else {
            showtextBox(matchId);
        }

    }

    private void matchFunctie(ArrayList matchId) {

        Luggage luggage = new Luggage();
        LinkedList result = super.repo.executeCustomSelect("select * from Luggage where Id = " + matchIdRecord);
        for (Object a : result) {
            luggage.fromLinkedList((LinkedList) a);
        }

        String[] woorden;
        woorden = luggage.getRemarks().split(", ");
        int gevondenOfVermist = luggage.getStatusId();
        if(gevondenOfVermist == 1)
            gevondenOfVermist = 2;
        else
            gevondenOfVermist = 1;

        for (int j = 0; j < woorden.length; j++) {

            ArrayList matchList = new ArrayList();
            ArrayList idList = new ArrayList();

            System.out.println(woorden[j]);
            LinkedList list = repo.executeCustomSelect("select INSTR(Remarks, '" + woorden[j] + "') from luggage where Id <> " + matchIdRecord + " and isDeleted = 0 and StatusId = " + gevondenOfVermist);
            LinkedList list2 = repo.executeCustomSelect("select Id from luggage where Id <> " + matchIdRecord + " and isDeleted = 0 and StatusId = " + gevondenOfVermist);
            System.out.println(list.size());

            for (int q = 0; q < list.size(); q++) {
                idList.add(list2.get(q).toString().replace("[", "").replace("]", ""));
                matchList.add(list.get(q).toString().replace("[", "").replace("]", ""));
                String num = (String) matchList.get(q);
                int value = Integer.parseInt(num);
                if (value >= 1 && !matchId.contains(idList.get(q))) {
                    matchId.add(idList.get(q));
                }
            }
        }

    }

    private void showAlertbox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informatie");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Er zijn geen matches met de geselecteerde koffer!!");
        alert.showAndWait();
    }

    private void showtextBox(ArrayList matchId) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Matchen");
        alert.setHeaderText("Matcheslijst");
        alert.setContentText("Er zijn: " + matchId.size() + " match(es) gevonden!");

        String tekst = "";

        for (int i = 0; i < matchId.size(); i++) {

            System.out.println(matchId.get(i));

            LinkedList list2 = repo.executeCustomSelect("SELECT * FROM Luggage WHERE Id = '" + matchId.get(i) + "'");
            Luggage luggage = new Luggage();
            luggage.fromLinkedList((LinkedList) list2.getFirst());
            tekst += "De koffer met id: " + luggage.getId() + " en kenmerk(en): " + luggage.getRemarks() + "\r\n";

        }

        Label label = new Label("Dit zijn de koffers waarmee de geselecteerde koffer een match heeft: ");

        TextArea textArea = new TextArea(tekst);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();

    }

    public void changeNederlands() {
        btnEdit.setText("Wijzig");
        btnDelete.setText("Verwijderen");
        btnAdd.setText("Nieuwe bagage");

        passengerId.setText("Naam van eigenaar");
        createdAt.setText("Aanmelddatum");
        flightNumber.setText("Vluchtnummer");
        remarks.setText("Bagage kenmerken");
    }

    public void changeEnglish() {
        btnEdit.setText("Edit");
        btnDelete.setText("Delete");
        btnAdd.setText("New luggage");

        passengerId.setText("Owner name");
        createdAt.setText("Create date");
        flightNumber.setText("Flightnumber");
        remarks.setText("Luggage remarks");
    }

    @FXML
    private void askForRepartition(ActionEvent event) throws IOException {

        Luggage selectedItem = (Luggage) overviewtable.getSelectionModel().getSelectedItem();
        BaseController.repartitionId = selectedItem.getId();
        BaseController.passengerId = selectedItem.getPassengerId();

        LinkedList<LinkedList> getRepatriation = repo.executeCustomSelect("SELECT LuggageId FROM Repatriation WHERE LuggageId = '" + BaseController.repartitionId + "' AND isDeleted = 0");

        if (getRepatriation.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Geen repatriatie gevonden!");
            alert.setHeaderText("Repatriatie niet gevonden");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Er is geen repatriatie gevonden voor koffer " + selectedItem.getId() + ". Wilt u er een aanmaken?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                super.swapScene(event, "repartitionAdd.fxml");
            }
        } else {
            super.swapScene(event, "repartitionInfo.fxml");
        }
    }
}
