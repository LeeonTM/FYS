/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.fys.DbClasses.Status;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
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
public class RepartitionAddController extends BaseController {

    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXButton managerButton;
    @FXML
    private JFXTextArea descriptionField;
    @FXML
    private JFXTextField labelField;
    @FXML
    private JFXTextField flightField;
    @FXML
    private JFXTextField destinationField;
    @FXML
    private JFXTextField typeField;
    @FXML
    private JFXTextField brandField;
    @FXML
    private JFXTextField colourField;
    @FXML
    private JFXTextField deliveryField;
    @FXML
    private JFXTextField typeDeliveryField;
    @FXML
    private JFXTextField airportField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField dateField;
    @FXML
    private JFXComboBox statusField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }
        
        // set all data
        LinkedList<LinkedList> label = repo.executeCustomSelect("SELECT LabelNumber FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        labelField.setText(label.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> flight = repo.executeCustomSelect("SELECT FlightNumber FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        flightField.setText(flight.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> destination = repo.executeCustomSelect("SELECT Destination FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        destinationField.setText(destination.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> type = repo.executeCustomSelect("SELECT TypeOfLuggage FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        typeField.setText(type.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> brand = repo.executeCustomSelect("SELECT Brand FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        brandField.setText(brand.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> colour = repo.executeCustomSelect("SELECT Colour FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        colourField.setText(colour.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> description = repo.executeCustomSelect("SELECT Remarks FROM Luggage WHERE Id = '" + BaseController.repartitionId + "'");
        descriptionField.setText(description.toString().replace("[", "").replace("]", ""));
        
        LinkedList statusses = repo.executeCustomSelect("SELECT count(Id) FROM Status");
        int limit1 = Integer.parseInt(statusses.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit1; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Status where Id = " + i);
            statusField.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }
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
    private void addRepaToDB(ActionEvent event) throws IOException {
        if (deliveryField.getText().trim().isEmpty() || typeDeliveryField.getText().trim().isEmpty() || airportField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Vul alle velden in!");
            alert.showAndWait();
        } else {
            
            LinkedList resultStatus = repo.executeSelect("Status", new String[]{"Name"}, new String[]{(String) statusField.getValue()});
            Status status = new Status();
            status.fromLinkedList((LinkedList) resultStatus.get(0));
            
            repo.executeInsert("Repatriation", new String[]{"FromAirport", "toAddress", "Transporter", "TransporterType", "StatusId", "PassengerId", "LuggageId"},
                    new String[]{airportField.getText(), addressField.getText(), deliveryField.getText(), typeDeliveryField.getText(), Integer.toString(status.getId()), Integer.toString(BaseController.passengerId), Integer.toString(BaseController.repartitionId)});
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Repatriering voor koffer met ID " + BaseController.repartitionId + " is aangemaakt!");
            alert.showAndWait();

            super.swapScene(event, "Overview.fxml");

        }
    }
}
