package com.aau.p3;

import com.aau.p3.platform.model.property.PropertyManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * The class Main is responsible for initiating the GUI and is where the application starting point is located.
 * It extends Application (an abstract class defined in JavaFX),
 * this class has the abstract method start - and defines launch behavior.
 */
public class Main extends Application {
    /* The main propertyManager instance which is responsible for making sure we always have the same object,
     * for the current property everywhere in the program. Can be called from anywhere as it is static. */
    public static PropertyManager propertyManager = new PropertyManager();

    /**
     * The start method is defined in Application and is used to create a scene with JavaFX.
     * @param primaryStage defines the primary GUI window. It gets its value assigned upon launch,
     * which is defined in Application (which Main extends)
     */
    @Override
    public void start(Stage primaryStage) {
        LocalProxyServer.startProxy(8080);
        try {
            /* Makes a new FXMLLoader object the parsed parameter,
             * gets the entire path to the FXML file that we wish to display.
             * The purpose of this is to create a link between the FXML and a java object */
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/ui/fxml/MainWindow.fxml"));

            /* .load() returns a reference to the outermost tag in the FXML file (<splitPane> in mainWindow), which is needed to display the GUI. */
            Parent root = loader.load();

            primaryStage.setTitle("Nykredit Platform");

            /* Adds the nykredit logo to the application */
            primaryStage.getIcons().add(
                    new javafx.scene.image.Image(
                            Main.class.getResourceAsStream("/ui/icons/nykredit_icon.png")
                    )
            );

            /* Displays the application at 90% of the screen,
             * with the initial window which is defined in MainWindow.fxml */
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, bounds.getWidth() * 0.9, bounds.getHeight() * 0.9);

            /* Apply's the stylesheets we defined for the GUI */
            scene.getStylesheets().add(getClass().getResource("/ui/css/settings.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/ui/css/nav.css").toExternalForm());

            /* Display the scene */
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for stopping the proxy server from the abstract class Application (JavaFX)
     */
    @Override
    public void stop() {
        LocalProxyServer.stopProxy();
    }
}