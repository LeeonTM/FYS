/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    private int gevonden;

    private int vermist;

    private ArrayList<Integer> vermistPerDag = new ArrayList();
    private ArrayList<Integer> gevondenPerDag = new ArrayList();
    private ArrayList<String> data = new ArrayList();

    @FXML
    private LineChart line;

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
        line.setVisible(false);

        System.out.println(luchthaven.getValue());
        System.out.println(date1.getValue());
        System.out.println(date2.getValue());

        if (date2.getValue() == null || date1.getValue() == null || luchthaven.getValue() == null) {
            warning.setVisible(true);

        } else {

            warning.setVisible(false);
            gegevensVerwerkenPiechart();
            setPieChart();
        }

    }

    private void gegevensVerwerkenPiechart() {
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
            System.out.println("Database is leeg");
            noSearchResults.setVisible(true);
            pie.setVisible(false);
        } else {
            noSearchResults.setVisible(false);
            pie.getData().setAll(pieChartData);
            pie.setVisible(true);
        }

    }

    private void gegevensVerwerkenLineChart() {
        int vermistId = 1;
        int gevondenId = 2;

        String stap = "select Id from Airport where Name = '" + luchthaven.getValue() + "'";
        LinkedList stap2 = repo.executeCustomSelect(stap);
        int vliegveldId = Integer.parseInt(stap2.toString().replace("[", "").replace("]", ""));

        LocalDate beginDatum = date1.getValue();
        LocalDate eindDatum = date2.getValue();

        do {

            String query1 = "select count(Id) from luggage where CreatedAt ='"
                    + beginDatum + "' and AirportId = " + vliegveldId + " and StatusId = '" + vermistId + "'";
            String query2 = "select count(Id) from luggage where CreatedAt ='"
                    + beginDatum + "'  and AirportId = " + vliegveldId + " and StatusId = '" + gevondenId + "'";

            LinkedList getal1 = repo.executeCustomSelect(query1);
            LinkedList getal2 = repo.executeCustomSelect(query2);

            vermist = Integer.parseInt(getal1.toString().replace("[", "").replace("]", ""));
            gevonden = Integer.parseInt(getal2.toString().replace("[", "").replace("]", ""));

            vermistPerDag.add(vermist);
            gevondenPerDag.add(gevonden);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
            String LocalDateToString = beginDatum.format(formatter);
            data.add(LocalDateToString);
            beginDatum = beginDatum.plusDays(1);

        } while (!beginDatum.equals(eindDatum.plusDays(1)));

        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }

    }

    private void setLineChart() {
        XYChart.Series series0 = new XYChart.Series();
        XYChart.Series series1 = new XYChart.Series();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 100, 1);
                final LineChart<String, Number> lineChart
                = new LineChart<String, Number>(xAxis, yAxis);
                
        xAxis.setLabel("Dag");
        yAxis.setLabel("Aantal koffers");
        series0.setName("Gevonden");
        series1.setName("Vermist");
        line.setTitle("Vermiste en gevonden bagage van " + date1.getValue() + " tot en met " + date2.getValue());

        line.getData().clear();

        for (int i = 0; i < data.size(); i++) {
            series0.getData().add(new XYChart.Data(data.get(i), gevondenPerDag.get(i)));
            series1.getData().add(new XYChart.Data(data.get(i), vermistPerDag.get(i)));
        }

        line.getData().addAll(series0, series1);
        line.setVisible(true);
        data.clear();
        vermistPerDag.clear();
        gevondenPerDag.clear();

    }

    @FXML
    private void handleButtonAction2(ActionEvent event) {

        pie.setVisible(false);
        System.out.println(luchthaven.getValue());
        System.out.println(date1.getValue());
        System.out.println(date2.getValue());

        if (date2.getValue() == null || date1.getValue() == null || luchthaven.getValue() == null) {
            warning.setVisible(true);

        } else {
            warning.setVisible(false);
            gegevensVerwerkenLineChart();
            setLineChart();
        }
    }

}