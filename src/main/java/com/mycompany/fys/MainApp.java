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
        
        MyJDBC db = new MyJDBC();
        db.executeUpdateQuery("INSERT INTO status VALUES ("
                + "1, 'Onderweg naar huis!')");
        System.out.println(db.executeStringQuery("SELECT statusID, statusName FROM status"));
        
        stage.setTitle("Find my Luggage");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
