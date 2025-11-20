package com.aau.p3;

import com.aau.p3.platform.model.property.PropertyManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    public static PropertyManager propertyManager = new PropertyManager();

    /* The start method is defined in Application and is used to create a scene with JavaFX */
    @Override
    public void start(Stage primaryStage) {
        LocalProxyServer.startProxy(8080);

        try {
            /* Makes a new FXMLLoader object the parsed parameter,
             * gets the entire path to the fxml file that we wish to display.
             * The purpose of this is to create a link between the fxml and a java object */
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/UI/MainWindow.fxml"));
            /* .load() returns a reference to the outermost tag in the fxml file (<splitPane> in mainWindow), which is needed to display the gui */
            Parent root = loader.load();
            primaryStage.setTitle("Nykredit Platform");

            /* Displays the application at 90% of the screen,
             * with the initial window which is defined in MainWindow.fxml */
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setScene(new Scene(root, bounds.getWidth() * 0.9, bounds.getHeight() * 0.9));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() {
        LocalProxyServer.stopProxy();
    }

}