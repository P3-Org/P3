package com.aau.p3.climatetool.utilities;

import com.aau.p3.database.StaticThresholdRepository;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;


/**
 * Indicator class.
 * Class that sets the threshold lines for a given risk.
 */
public class Indicator {
    private double[] thresholdValues;

    /**
     * Method for setting the Threshold line for a given indicator.
     * @param indicator the anchor pane where the indicator is to be set.
     * @param risk which risk we are creating an indicator for.
     * @param siPrefix is either in millimeters mm or meters m.
     */
    public void setThresholdsLines(AnchorPane indicator, String risk, String siPrefix) {
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        thresholdValues = thresholdRepo.getThreshold(risk);

        Line lowerThreshold = new Line();
        Line upperThreshold = new Line();
        Label lowerLabel;
        Label upperLabel;

        lowerThreshold.setStrokeWidth(2);
        upperThreshold.setStrokeWidth(2);

        if (risk.equals("coastalerosion")) {
            lowerLabel = new Label("Stor erosion");
            upperLabel = new Label("Lille erosion");
        }
        else {
            lowerLabel = new Label(thresholdValues[0] + " " + siPrefix);
            upperLabel = new Label(thresholdValues[1] + " " + siPrefix);
        }

        lowerThreshold.setStartY(14);
        lowerThreshold.setEndY(19);
        upperThreshold.setStartY(14);
        upperThreshold.setEndY(19);

        lowerLabel.setLayoutY(-2);
        upperLabel.setLayoutY(-2);

        /* Set the line X position - This scales with the length of the AnchorPane(indicator) that the lines are contained within.
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

    /**
     * Scales a threshold
     * @param threshold to be scaled
     * @return the scaled Threshold
     */
    private double scaleThreshold(double threshold) {
        return threshold/(thresholdValues[0]+thresholdValues[1]);
    }
}
