/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fys;

import com.mycompany.fys.DbClasses.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Leon
 */
public class BaseController implements Initializable {

    public Repository repo;
    public static User loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public BaseController() {
        if (!Repository.dbExists()) {
            Repository.createDummy();
            Repository.addDummyData();
        }

        repo = new Repository();
    }

    // Swap current scene to given scene
    public void swapScene(ActionEvent event, String sceneName) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane baseePane = (AnchorPane) stage.getScene().getRoot();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/" + sceneName));

        baseePane.getChildren().setAll(pane.getChildren());
    }
}
