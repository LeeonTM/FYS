/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXTreeTableView;
import com.mycompany.fys.DbClasses.Luggage;
import com.mycompany.fys.DbClasses.User;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Leon
 */
public class OverviewController extends BaseController {

    @FXML
    private AnchorPane overviewPane;

    @FXML
    private TableView overviewtable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Luggage> list = FXCollections.observableArrayList();
        LinkedList result = super.repo.executeSelect("luggage");
        for (Object a : result) {
            Luggage luggage = new Luggage();
            System.out.println(a);
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        basePane.getChildren().setAll(pane.getChildren());
    }

    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Instellingen.fxml"));

        basePane.getChildren().setAll(pane.getChildren());
    }

    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Overview.fxml"));

        basePane.getChildren().setAll(pane.getChildren());
    }

    @FXML
    private void handleBagageMatch(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Bagagematchen.fxml"));

        basePane.getChildren().setAll(pane.getChildren());
    }

    @FXML
    private void handleAddLuggage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/addMissingLuggage.fxml"));

        basePane.getChildren().setAll(pane.getChildren());
    }
}
