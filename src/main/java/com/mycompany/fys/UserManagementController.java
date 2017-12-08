/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class UserManagementController extends BaseController {

    @FXML
    private AnchorPane basePane;
    
    @FXML
    private TableColumn Username;
    
    @FXML        
    private TableColumn Email;
    
    @FXML        
    private TableColumn AirportId;
    
    @FXML        
    private TableColumn RoleId;
    
    @FXML
    private TableView userManagementTableView;
    /**
     * Initializes the controller class.
    */
    
    final ObservableList<UserDetails> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data.add(new UserDetails());
        
        for (int cnr = 0; cnr < userManagementTableView.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn)userManagementTableView.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                System.out.println("Attached column '" + propertyName + "' in tableview to matching attribute");
            }
        }
        
//        Username.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
//        AirportId.setCellValueFactory(new PropertyValueFactory<>("AirportId"));
//        RoleId.setCellValueFactory(new PropertyValueFactory<>("RoleId"));
        
        userManagementTableView.setItems(data);
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
    private void handleUserManage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/userManagement.fxml"));
        
        basePane.getChildren().setAll(pane.getChildren());
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/managerStats.fxml"));
        
        basePane.getChildren().setAll(pane.getChildren());
    }
    
    @FXML
    private void handleAddUser(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane basePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/addUserManagement.fxml"));
        
        basePane.getChildren().setAll(pane.getChildren());
    }
    
}
