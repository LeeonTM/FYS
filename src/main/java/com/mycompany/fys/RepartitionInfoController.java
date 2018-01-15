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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class RepartitionInfoController extends BaseController {

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
    private JFXTextField statusField;
                          
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }
        
        // set all data
        LinkedList<LinkedList> label = repo.executeCustomSelect("SELECT LabelNumber FROM Luggage WHERE Id = '" + BaseController.repartitionId + "' ");
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
        
        LinkedList<LinkedList> delivery = repo.executeCustomSelect("SELECT Transporter FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        deliveryField.setText(delivery.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> typeDelivery = repo.executeCustomSelect("SELECT TransporterType FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        typeDeliveryField.setText(typeDelivery.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> airport = repo.executeCustomSelect("SELECT FromAirport FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        airportField.setText(airport.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> address = repo.executeCustomSelect("SELECT ToAddress FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        addressField.setText(address.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> date = repo.executeCustomSelect("SELECT Date FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        dateField.setText(date.toString().replace("[", "").replace("]", ""));
        
        LinkedList<LinkedList> statusId = repo.executeCustomSelect("SELECT StatusId FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        LinkedList<LinkedList> status = repo.executeCustomSelect("SELECT Name FROM Status WHERE Id = '" + statusId.toString().replace("[", "").replace("]", "") + "'");
        statusField.setText(status.toString().replace("[", "").replace("]", ""));
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
    private void deleteRepatriation(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestig actie");
        alert.setHeaderText("Verwijderen van een repatriering");
        alert.initStyle(StageStyle.UNDECORATED);
        LinkedList<LinkedList> repaID = repo.executeCustomSelect("SELECT Id FROM Repatriation WHERE isDeleted = 0 AND LuggageId = '" + BaseController.repartitionId + "'");
        alert.setContentText("Weet je zeker dat je deze repatriering met nummer " + repaID.toString().replace("[", "").replace("]", "") + " wilt verwijderen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            repo.executeUpdateQuery("UPDATE Repatriation SET isDeleted = 1 WHERE Id ='" + repaID.toString().replace("[", "").replace("]", "") + "'");
            super.swapScene(event, "Overview.fxml");
        }
    }
}
