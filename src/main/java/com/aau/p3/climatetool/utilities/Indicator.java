package com.aau.p3.climatetool.utilities;

import com.aau.p3.database.StaticThresholdRepository;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Indicator {
    private double[] thresholdValues;

    public void setThresholdsLines(String risk, AnchorPane indicator) {
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        thresholdValues = thresholdRepo.getThreshold(risk);

        Line lowerThreshold = new Line();
        Line upperThreshold = new Line();

        lowerThreshold.setStrokeWidth(2);
        upperThreshold.setStrokeWidth(2);

        Label lowerLabel = new Label(thresholdValues[0] + " m");
        Label upperLabel = new Label(thresholdValues[1] + " m");

        /* Set line heights, currently hardcoded because cba*/
        lowerThreshold.setStartY(19);
        lowerThreshold.setEndY(23);
        upperThreshold.setStartY(19);
        upperThreshold.setEndY(23);

        /* Set the line X position - This scales with the length of the AncorPane(indicator) that the lines are contained within.
        example: indicator.width = 100      Threshold = {10,20}
                 100 * scaleThreshold(10) = 100 * 0.33
                 So the  lowerThreshold is positioned at the first 33% or the slider*/
        lowerThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[0])));
        lowerThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[0])));
        upperThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[1])));
        upperThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(thresholdValues[1])));

        /* Bind label positions - This is done using the position of the already positioned lines */
        lowerLabel.translateXProperty().bind(lowerThreshold.startXProperty().subtract(lowerLabel.widthProperty().divide(2)));
        upperLabel.translateXProperty().bind(upperThreshold.startXProperty().subtract(upperLabel.widthProperty().divide(2)));

        /* Add to parent */
        indicator.getChildren().addAll(lowerThreshold, upperThreshold, lowerLabel, upperLabel);



    }

    private double scaleThreshold(double threshold) {
        return threshold/(thresholdValues[0]+thresholdValues[1]);
    }
}
