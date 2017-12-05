package com.mycompany.fys;

import com.mycompany.fys.DbClasses.*;
import java.util.List;
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

        Repository repo = new Repository();
        repo.addDummyData();
        // INSERT voorbeeld - repo.executeInsert("status", new String[]{"Name"},new String[]{"Test"});
        // UPDATE voorbeeld - repo.executeUpdate("status", "1", "Id", new String[]{"Name"}, new String[]{"Test 1"});
        // SELECT alles voorbeeld - List<Status> res = (List<Status>) (Object) repo.executeSelect("status");
        // SELECT met multiple of single WHERE - List<Status> res = (List<Status>) (Object) repo.executeSelect("status", new String[]{"Id", "Name"}, new String[]{"1", "Test 1"});
        
        stage.setTitle("Find my Luggage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
