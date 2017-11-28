/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Fien Hoornstra
 */
public class InstellingenController implements Initializable {

    @FXML
    private AnchorPane basePane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Leon Test
        //hicham test
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
}
