package com.aau.p3;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file from the resources folder
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/UI/mainWindow.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Nykredit Platform");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {
    //    launch(args); // Launch the JavaFX application
    //}
}