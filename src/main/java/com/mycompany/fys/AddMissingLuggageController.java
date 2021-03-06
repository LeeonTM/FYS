/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.mycompany.fys.DbClasses.Airport;
import com.mycompany.fys.DbClasses.Status;
import java.time.format.DateTimeFormatter;

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
    @FXML
    private Label lblGeneral;
    @FXML
    private Label lblOwnerInfo;
    @FXML
    private Label lblLuggageInfo;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblAirport;
    @FXML
    private Label lblFirstname;
    @FXML
    private Label lblLastname;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblPlace;
    @FXML
    private Label lblZipCode;
    @FXML
    private Label lblMobileNumber;
    @FXML
    private Label lblHomeNumber;
    @FXML
    private Label lblLabelNumber;
    @FXML
    private Label lblFlightNumber;
    @FXML
    private Label lblDestination;
    @FXML
    private Label lblType;
    @FXML
    private Label lblBrand;
    @FXML
    private Label lblColor;
    @FXML
    private Label lblRemarks;
    @FXML
    private JFXButton btnAdd;

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
        LinkedList test2 = repo.executeCustomSelect(query2);
        int limit2 = Integer.parseInt(test2.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit2; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT distinct Country FROM address where Id = " + i);
            country.getItems().add(list.toString().replace("[", "").replace("]", ""));
        }

        if (super.applicatieTaal == null || super.applicatieTaal == "Nederlands") {
            changeNederlands();
        } else {
            changeEnglish();
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
    private void handleSchadeClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "damageOverview.fxml");
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

    private String radioStatus;

    private void showInfoBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bevestiging");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("De " + radioStatus + " baggage is toegevoegd!");
        alert.showAndWait();
    }

    @FXML
    private void handleButton(ActionEvent event) throws IOException {

        System.out.println(number.getText().trim());
        System.out.println(date.getValue());

        // Controleer welke button aangevinkt is.
        if (radioGevonden.isSelected()) {
            radioStatus = "Gevonden";
        } else {
            radioStatus = "Vermist";
        }

        // Controleer of alle data is ingevuld.
        if (date.getValue() == null || time.getValue() == null || name.getValue() == null || firstname.getText().trim().isEmpty() || lastname.getText().trim().isEmpty() || email.getText().trim().isEmpty()
                || street.getText().trim().isEmpty() || country.getValue() == null || place.getText().trim().isEmpty() || postalCode.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty() || number.getText().trim().isEmpty() || labelNumber.getText().trim().isEmpty() || flightNumber.getText().trim().isEmpty() || destination.getText().trim().isEmpty()
                || typeOfLuggage.getText().trim().isEmpty() || brand.getText().trim().isEmpty() || colour.getText().trim().isEmpty()) {

            showAlertbox();
        } else {
            addLuggageToDB();
            showInfoBox();
            fromAddMissingLuggage = 1;
            handleOverview(event);
        }
    }

    private void addLuggageToDB() {
        LinkedList status = repo.executeSelect("status", new String[]{"Name"}, new String[]{radioStatus});
        Status stats = new Status();
        stats.fromLinkedList((LinkedList) status.get(0));

        //word gebruikt om id te stoppen in de query van luggage helemaal onderaan
        LinkedList resultAirport = repo.executeSelect("airport", new String[]{"Name"}, new String[]{(String) name.getValue()});
        Airport airport = new Airport();
        airport.fromLinkedList((LinkedList) resultAirport.get(0));

        //address 
        repo.executeInsert("address", new String[]{"Street", "Number", "Place", "PostalCode", "Country"},
                new String[]{street.getText(), number.getText(), place.getText(), postalCode.getText(), (String) country.getValue()});

        LinkedList test0 = repo.executeCustomSelect("select max(Id) from address");
        int idAddress = Integer.parseInt(test0.toString().replace("[", "").replace("]", ""));

        //passenger
        repo.executeInsert("passenger", new String[]{"Firstname", "Lastname", "Email", "Phone", "AddressId"},
                new String[]{firstname.getText(), lastname.getText(), email.getText(), phone.getText(), Integer.toString(idAddress)});

        LinkedList test1 = repo.executeCustomSelect("select max(Id) from passenger");
        int idPassenger = Integer.parseInt(test1.toString().replace("[", "").replace("]", ""));
        
        DateTimeFormatter formatter0 = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss.s");
        String datum = date.getValue().format(formatter0) + " " + time.getValue().format(formatter1);

        //luggage aanmaken
        repo.executeInsert("luggage", new String[]{"Destination", "LabelNumber", "FlightNumber", "WFCode", "TypeOfLuggage", "Brand", "Colour",
            "Remarks", "PassengerId", "AirportId", "StatusId", "UpdatedAt", "CreatedAt"},
                new String[]{destination.getText(), labelNumber.getText(), flightNumber.getText(), "435TEST",
                    typeOfLuggage.getText(), brand.getText(), colour.getText(), remarks.getText(), Integer.toString(idPassenger),
                    Integer.toString(airport.getId()), Integer.toString(stats.getId()), datum, datum});
    }

    public void changeEnglish() {
        lblGeneral.setText("General information");
        lblOwnerInfo.setText("Owner information");
        lblLuggageInfo.setText("Luggage information");
        lblDate.setText("Date");
        lblTime.setText("Time");
        lblAirport.setText("Airport");
        lblFirstname.setText("Firstname");
        lblLastname.setText("Lastname");
        lblEmail.setText("Email");
        lblAddress.setText("Address");
        lblCountry.setText("Country");
        lblPlace.setText("City");
        lblZipCode.setText("Zipcode");
        lblMobileNumber.setText("Phonenumber");
        lblHomeNumber.setText("Home number");
        lblLabelNumber.setText("Labelnumber");
        lblFlightNumber.setText("Flightnumber");
        lblDestination.setText("Destination");
        lblType.setText("Type");
        lblBrand.setText("Brand");
        lblColor.setText("Color");
        lblRemarks.setText("Luggage remarks");
        btnAdd.setText("Add!");
        radioGevonden.setText("Found");
        radioVermist.setText("Lost");
    }

    public void changeNederlands() {
        lblGeneral.setText("Algemene informatie");
        lblOwnerInfo.setText("Reiziger informatie");
        lblLuggageInfo.setText("Koffer informatie");
        lblDate.setText("Datum");
        lblTime.setText("Tijd");
        lblAirport.setText("Luchthaven melding");
        lblFirstname.setText("Voornaam");
        lblLastname.setText("Achternaam");
        lblEmail.setText("Emailadres");
        lblAddress.setText("Verblijfsadres");
        lblCountry.setText("Land");
        lblPlace.setText("Woonplaats");
        lblZipCode.setText("Postcode");
        lblMobileNumber.setText("Telefoonnummer");
        lblHomeNumber.setText("Huisnummer");
        lblLabelNumber.setText("Labelnummer");
        lblFlightNumber.setText("Vluchtnummer");
        lblDestination.setText("Bestemming");
        lblType.setText("Type");
        lblBrand.setText("Merk");
        lblColor.setText("Kleur");
        lblRemarks.setText("Bijzondere kenmerken");
        btnAdd.setText("Voeg toe!");
        radioGevonden.setText("Gevonden");
        radioVermist.setText("Vermist");
    }
}
