package com.aau.p3.climatetool.utilities;

import com.aau.p3.database.StaticThresholdRepository;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Indicator {
    private double[] thresholdValues;

    public void setThresholdsLines(String risk, AnchorPane indicator) {
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        Line lowerThreshold = new Line();
        Line upperThreshold = new Line();

        Label lowerThresholdLabel = new Label();
        Label upperThresholdLabel = new Label();
        thresholdValues = thresholdRepo.getThreshold(risk);

        Platform.runLater(() -> {
        thresholdValues = thresholdRepo.getThreshold(risk);

        /* Sets the height of the threshold lines */
        lowerThreshold.startYProperty().set(19);
        lowerThreshold.endYProperty().set(23);
        upperThreshold.startYProperty().set(19);
        upperThreshold.endYProperty().set(23);

        lowerThresholdLabel.setPrefWidth(Label.USE_COMPUTED_SIZE);
        lowerThresholdLabel.setPrefHeight(Label.USE_COMPUTED_SIZE);
        lowerThresholdLabel.setLayoutY(0);

        upperThresholdLabel.setPrefWidth(Label.USE_COMPUTED_SIZE);
        upperThresholdLabel.setPrefHeight(Label.USE_COMPUTED_SIZE);
        upperThresholdLabel.setLayoutY(0);



        /* Sets the horizontal position of the threshold lines */
        lowerThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[0])));
        lowerThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[0])));
        upperThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[1])));
        upperThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[1])));

        lowerThresholdLabel.layoutXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[0])).subtract(lowerThresholdLabel.widthProperty().divide(2)));
        upperThresholdLabel.layoutXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[1])).subtract(upperThresholdLabel.widthProperty().divide(2)));

        /* Change width of the line */
        lowerThreshold.setStrokeWidth(2);
        upperThreshold.setStrokeWidth(2);


        });

        lowerThresholdLabel.setText(Double.toString(thresholdValues[0])+" m");
        upperThresholdLabel.setText(Double.toString(thresholdValues[1])+" m");

        /* Apply the lines to the FXML UI */
        indicator.getChildren().addAll(lowerThreshold, upperThreshold, lowerThresholdLabel, upperThresholdLabel);


    }

    private double scaleThreshold(double threshold) {
        return threshold/(thresholdValues[0]+thresholdValues[1]);
    }
}
