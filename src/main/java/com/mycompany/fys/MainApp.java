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
        // INSERT voorbeeld - repo.executeInsert("status", new String[]{"3", "Test"});
        // UPDATE voorbeeld - repo.executeUpdate("status", "3", "statusId", new String[]{"statusId", "statusName"}, new String[]{"3", "Test 1"});
        // SELECT alles voorbeeld - List<Status> res = (List<Status>) (Object) repo.executeSelect("status");
        // SELECT met multiple of single WHERE - List<Status> res = (List<Status>) (Object) repo.executeSelect("status", new String[]{"statusId", "statusName"}, new String[]{"3", "Test"});
        
        stage.setTitle("Find my Luggage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
