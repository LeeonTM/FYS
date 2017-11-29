package com.mycompany.fys;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        //Repository repo = new Repository();
        //repo.executeInsert("status", new String[]{"3", "Test"});
        //repo.executeUpdate("status", "3", "statusId", new String[]{"statusId", "statusName"}, new String[]{"3", "Test 1"});
        
        stage.setTitle("Find my Luggage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
