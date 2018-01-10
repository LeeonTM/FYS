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

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class ManagerStatsController extends BaseController {

    @FXML
    private AnchorPane basePane;

    @FXML
    private PieChart pie;

    @FXML
    private JFXDatePicker date1;

    @FXML
    private JFXDatePicker date2;

    @FXML
    private JFXComboBox luchthaven;

    @FXML
    private Label warning;

    @FXML
    private Label noSearchResults;

    @FXML
    private int gevonden;

    @FXML
    private int vermist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String query = "select count(Id) from Airport";
        LinkedList test = repo.executeCustomSelect(query);
        int limit = Integer.parseInt(test.toString().replace("[", "").replace("]", ""));

        for (int i = 1; i <= limit; i++) {
            LinkedList list = repo.executeCustomSelect("SELECT Name FROM Airport where Id = " + i);
            luchthaven.getItems().add(list.toString().replace("[", "").replace("]", ""));
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
    private void handleUserManage(ActionEvent event) throws IOException {
        super.swapScene(event, "userManagement.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.out.println(luchthaven.getValue());
        System.out.println(date1.getValue());
        System.out.println(date2.getValue());

        if (date2.getValue() == null || date1.getValue() == null || luchthaven.getValue() == null) {
            //TO DO: Make this not hard coded
            warning.setVisible(true);

        } else {

            warning.setVisible(false);
            gegevensVerwerken();
            setPieChart();
        }

    }

    @FXML
    private void gegevensVerwerken() {
        int vermistId = 1;
        int gevondenId = 2;

        String stap = "select Id from Airport where Name = '" + luchthaven.getValue() + "'";
        LinkedList stap2 = repo.executeCustomSelect(stap);
        int vliegveldId = Integer.parseInt(stap2.toString().replace("[", "").replace("]", ""));

        String query1 = "select count(Id) from luggage where CreatedAt between '"
                + date1.getValue() + "' and '" + date2.getValue()
                + "' and AirportId = " + vliegveldId + " and StatusId = '" + vermistId + "'";
        String query2 = "select count(Id) from luggage where CreatedAt between '"
                + date1.getValue() + "' and '" + date2.getValue()
                + "' and AirportId = " + vliegveldId + " and StatusId = '" + gevondenId + "'";

        LinkedList getal1 = repo.executeCustomSelect(query1);
        LinkedList getal2 = repo.executeCustomSelect(query2);

        vermist = Integer.parseInt(getal1.toString().replace("[", "").replace("]", ""));
        gevonden = Integer.parseInt(getal2.toString().replace("[", "").replace("]", ""));

    }

    private void setPieChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data(vermist + " Vermist", vermist),
                        new PieChart.Data(gevonden + " Gevonden", gevonden));

        pie.setTitle("Vermiste en gevonden bagage van " + date1.getValue() + " tot en met " + date2.getValue());

        if (vermist <= 0 && gevonden <= 0) {
            System.out.println("het is leeg");
            noSearchResults.setVisible(true);
            pie.setVisible(false);
        } else {
            noSearchResults.setVisible(false);
            pie.getData().setAll(pieChartData);
            pie.setVisible(true);
        }

    }

}
