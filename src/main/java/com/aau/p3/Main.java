package com.aau.p3;


import com.aau.p3.dawa.DawaAutocomplete;
import com.aau.p3.dawa.DawaPolygonForAddress;
import com.aau.p3.utility.UrlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    /* The start method is defined in Application and is used to create a scene with JavaFX */
    @Override
    public void start(Stage primaryStage) {
        try {
            /* Makes a new FXMLLoader object the parsed parameter,
             * gets the entire path to the fxml file that we wish to display */
            /* The purpose of this is to create a link between the fxml and a java object */
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/UI/MainWindow.fxml"));

            /* .load() returns a reference to the outermost tag in the fxml file (<splitPane> in mainWindow), which is needed to display the gui */
            Parent root = loader.load();
            primaryStage.setTitle("Nykredit Platform");

            /* displays the initial window which is defined in MainWindow.fxml */
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

