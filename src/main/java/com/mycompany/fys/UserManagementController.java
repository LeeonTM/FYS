/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.mycompany.fys.DbClasses.User;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
    private TableView userManagementTableView;
    /**
     * Initializes the controller class.
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        ObservableList<User> list = FXCollections.observableArrayList();
        LinkedList result = super.repo.executeSelect("user");
        for(Object a : result){
            User user = new User();
            user.fromLinkedList((LinkedList)a);
            list.add(user);
        }
        
        for (int cnr = 0; cnr < userManagementTableView.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn)userManagementTableView.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            }
        }
        userManagementTableView.setItems(list);
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
