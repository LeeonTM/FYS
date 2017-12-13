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
import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
        @FXML
    private void delItemFromTable(ActionEvent event) {
        
        Object selectedItem = userManagementTableView.getSelectionModel().getSelectedItem();
        
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
            alert.setContentText("Weet je zeker dat je deze rij met gebruikersnaam " + selectedItem + " wilt verwijderen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println("uitgevoerd");
            }
        }
    }
}   
