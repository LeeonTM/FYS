/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mycompany.fys.DbClasses.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

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
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private Label lblUsermanagement;
    
    @FXML
    private TableView userManagementTableView;
    /**
     * Initializes the controller class.
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        refreshUserData();
        
        if(super.applicatieTaal == null || super.applicatieTaal == "Nederlands"){
            changeNederlands();
        }
        else{
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
    private void handleUserManage(ActionEvent event) throws IOException {
        super.swapScene(event, "userManagement.fxml");
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "managerStats.fxml");
    }
    
    @FXML
    private void handleAddUser(ActionEvent event) throws IOException {
        super.swapScene(event, "addUserManagement.fxml");
    }
    
    private void refreshUserData () {
        ObservableList<User> list = FXCollections.observableArrayList();
        LinkedList result = repo.executeCustomSelect("SELECT * FROM user WHERE isDeleted = 0");
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
    private void handleEditUser(ActionEvent event) throws IOException {
        User selectedItem = (User) userManagementTableView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        }
        else {
        BaseController.changingUser = selectedItem.getUsername();
        super.swapScene(event, "editUserManagement.fxml");
        }
    }
    
    @FXML
    private void delItemFromTable(ActionEvent event) {
        
        User selectedItem = (User) userManagementTableView.getSelectionModel().getSelectedItem();
        
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informatie");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Kies eerst een rij voordat je op de knop drukt!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bevestig actie");
            alert.setHeaderText("Verwijderen van een rij uit de tabel");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Weet je zeker dat je deze rij met gebruikersnaam " + selectedItem.getUsername() + " wilt verwijderen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                repo.executeUpdateQuery("UPDATE user SET isDeleted = 1 WHERE Username ='" + selectedItem.getUsername() + "'");
                refreshUserData();
            }
        }
    }
    
    private void changeEnglish(){
        lblUsermanagement.setText("User management");
        btnAdd.setText("New user");
        btnEdit.setText("Edit user");
        btnDelete.setText("Delete user");
        
        Username.setText("Username");
        Email.setText("Email");
        AirportId.setText("Airport");
        RoleId.setText("Role");
    }
    
    private void changeNederlands(){
        lblUsermanagement.setText("Gebruikerbeheer");
        btnAdd.setText("Voeg gebruiker toe");
        btnEdit.setText("Verander gebruiker");
        btnDelete.setText("Verwijder gebruiker");
        
        Username.setText("Gebruikersnaam");
        Email.setText("Emailadres");
        AirportId.setText("Vliegveld");
        RoleId.setText("Gebruikersrechten");
    }
    
    @FXML
    private void handleHelp(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informatie");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Deze functie is nog in ontwikkeling!");
        alert.showAndWait();
    }
    
    @FXML
    private void handleOverview(ActionEvent event) throws IOException {
        super.swapScene(event, "Overview.fxml");
    }
}   
