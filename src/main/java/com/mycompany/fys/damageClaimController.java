/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yannick
 */
public class damageClaimController extends BaseController {

    @FXML
    private AnchorPane basePane;
    @FXML
    private JFXButton managerButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSchadeClaim(ActionEvent event) throws IOException {
        super.swapScene(event, "damageOverview.fxml");
    }

    @FXML
    private void handleOverview(ActionEvent event) {
    }

    @FXML
    private void handleSettings(ActionEvent event) {
    }

    @FXML
    private void handleLogout(ActionEvent event) {
    }

    @FXML
    private void handleManagerOverview(ActionEvent event) {
    }
    
}
