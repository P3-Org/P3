package com.aau.p3.climatetool.utilities;

import com.aau.p3.database.StaticThresholdRepository;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Indicator {
    private double[] mockThreshold;

    public void setThresholdsLines(String risk, AnchorPane indicator) {
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        mockThreshold = thresholdRepo.getThreshold(risk);

        Line lowerThreshold = new Line();
        Line upperThreshold = new Line();

        /* Sets the height of the threshold lines */
        lowerThreshold.startYProperty().set(2);
        lowerThreshold.endYProperty().set(10);
        upperThreshold.startYProperty().set(2);
        upperThreshold.endYProperty().set(10);

        /* Sets the horizontal position of the threshold lines */
        lowerThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[0])));
        lowerThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[0])));
        upperThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[1])));
        upperThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[1])));

        /* Change width of the line */
        lowerThreshold.setStrokeWidth(2);
        upperThreshold.setStrokeWidth(2);

        /* Apply the lines to the FXML UI */
        indicator.getChildren().addAll(lowerThreshold, upperThreshold);
    }

    private double scaleThreshold(double threshold) {
        return threshold/(mockThreshold[0]+mockThreshold[1]);
    }
}
