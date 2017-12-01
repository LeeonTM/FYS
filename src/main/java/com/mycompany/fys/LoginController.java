/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
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
 * FXML Controller class
 *
 * @author Leon
 */
public class LoginController extends BaseController {

    @FXML
    private AnchorPane basePane;
    
    @FXML
    private JFXTextField userName;
    
    @FXML
    private JFXPasswordField passWord;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        
        //super.repo.executeSelect("user", new String[]{"username", "password"}, new String[]{userName.getText(), passWord.getText()});
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane baseePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Bagagematchen.fxml"));
        
        baseePane.getChildren().setAll(pane.getChildren());
    }
}
