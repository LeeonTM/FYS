/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.mycompany.fys.DbClasses.Address;
import com.mycompany.fys.DbClasses.Airport;
import com.mycompany.fys.DbClasses.Luggage;
import com.mycompany.fys.DbClasses.Passenger;
import com.mycompany.fys.DbClasses.Role;
import com.mycompany.fys.DbClasses.Status;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class AddMissingLuggageController extends BaseController {

    @FXML
    private AnchorPane basePane;

    @FXML
    private JFXButton managerButton;

    @FXML
    private JFXRadioButton radioGevonden;

    @FXML
    private JFXRadioButton radioVermist;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private JFXComboBox name;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField street;
    @FXML
    private JFXComboBox country;
    @FXML
    private JFXTextField place;
    @FXML
    private JFXTextField postalCode;
    @FXML
    private JFXTextField number;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField labelNumber;
    @FXML
    private JFXTextField flightNumber;
    @FXML
    private JFXTextField destination;
    @FXML
    private JFXTextField typeOfLuggage;
    @FXML
    private JFXTextField brand;
    @FXML
    private JFXTextField colour;
    @FXML
    private JFXTextArea remarks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (BaseController.loggedInUser.getRoleId() == 2) {
            managerButton.setVisible(true);
        }

        String query = "select count(Id) from Airport";
        LinkedList test = repo.executeCustomSelect(query);
        int limit = Integer.parseInt(test.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Airport where Id = " + i);
            name.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }

        String query2 = "select count(Id) from address";
        LinkedList test2 = repo.executeCustomSelect(query);
        int limit2 = Integer.parseInt(test.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit2; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT distinct Country FROM address where Id = " + i);
            country.getItems().add(list.toString().replace("[", "").replace("]", ""));
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
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }

    @FXML
    private void handleBagageMatch(ActionEvent event) throws IOException {
        super.swapScene(event, "Bagagematchen.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }

    @FXML
    private void radioButton1(ActionEvent event) {
        if (radioGevonden.isSelected()) {
            radioVermist.setSelected(false);
        }

    }

    @FXML
    private void radioButton2(ActionEvent event) {
        if (radioVermist.isSelected()) {
            radioGevonden.setSelected(false);
        }
    }

    private void showAlertbox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informatie");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Vul alle velden in!");
        alert.showAndWait();
    }
    @FXML
    private void addLuggageToDB(ActionEvent event) throws IOException {
        
        System.out.println(number.getText().trim());
        System.out.println(date.getValue());
        String radioStatus;
        
        // Controleer welke button aangevinkt is.
        if (radioGevonden.isSelected()) {
            radioStatus = "Gevonden";
        }
        else {
            radioStatus = "Vermist";
        }
        
        // Controleer of alle data is ingevuld.
        if (date.getValue() == null || time.getValue() == null || name.getValue() == null || firstname.getText().trim().isEmpty() || lastname.getText().trim().isEmpty() || email.getText().trim().isEmpty()
                || street.getText().trim().isEmpty() || country.getValue() == null || place.getText().trim().isEmpty() || postalCode.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty() || number.getText().trim().isEmpty() || labelNumber.getText().trim().isEmpty() || flightNumber.getText().trim().isEmpty() || destination.getText().trim().isEmpty()
                || typeOfLuggage.getText().trim().isEmpty() || brand.getText().trim().isEmpty() || colour.getText().trim().isEmpty()) {

            showAlertbox();
        } 
        else {
            LinkedList status = repo.executeSelect("status", new String[]{"Name"}, new String[]{radioStatus});
            Status stats = new Status();
            stats.fromLinkedList((LinkedList) status.get(0));
            System.out.println(Integer.toString(stats.getId()));
            
            //word gebruikt om id te stoppen in de query van luggage helemaal onderaan
            LinkedList resultAirport = repo.executeSelect("airport", new String[]{"Name"}, new String[]{(String) name.getValue()});
            Airport airport = new Airport();
            airport.fromLinkedList((LinkedList) resultAirport.get(0));
            System.out.println(Integer.toString(airport.getId()));
            
            //address 
            System.out.println(repo.executeInsert("address", new String[]{"Street","Number", "Place", "PostalCode", "Country"}, new String[]{street.getText(), number.getText(), place.getText(), postalCode.getText(), (String) country.getValue()}));
            
            LinkedList resultAddress = repo.executeSelect("address", new String[]{"PostalCode"}, new String[]{(String) postalCode.getText()});
            Address address = new Address();
            address.fromLinkedList((LinkedList) resultAddress.get(0));
            
            //passenger
            repo.executeInsert("passenger", new String[]{"Firstname", "Lastname", "Email", "Phone", "AddressId"}, new String[]{firstname.getText(), lastname.getText(), email.getText(), phone.getText().trim(), Integer.toString(address.getId())});
            
            LinkedList resultPassenger = repo.executeSelect("passenger", new String[]{"Firstname"}, new String[]{firstname.getText()}); 
            Passenger passenger = new Passenger();
            passenger.fromLinkedList((LinkedList) resultPassenger.get(0)); //deze regel is kapot en wilt t niet do en why? dat is de regel die t niet doet
            System.out.println(Integer.toString(passenger.getId()));

            //luggage aanmaken
           repo.executeInsert("luggage", new String[]{"Destination", "LabelNumber", "FlightNumber", "TypeOfLuggage", "Brand", "Colour", "Remarks", "PassengerId", "AirportId", "StatusId"},
                 new String[]{destination.getText(), labelNumber.getText(), flightNumber.getText(), typeOfLuggage.getText(), brand.getText(), colour.getText(), remarks.getText(), Integer.toString(passenger.getId()), Integer.toString(airport.getId()), Integer.toString(stats.getId())});

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bevestiging");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("De " + radioStatus + " baggage is toegevoegd!");
            alert.showAndWait();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Overview.fxml"));

            basePane.getChildren().setAll(pane.getChildren());
        }
    }
}
