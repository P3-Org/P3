package com.aau.p3.climatetool.utilities;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Indicator {
    private double[] mockThreshold = {30, 70};

    public void setThresholdsLines(String risk, Pane indicator){
        Line lowerThreshold = new Line();
        Line upperThreshold = new Line();

        lowerThreshold.startYProperty().set(0);
        lowerThreshold.endYProperty().bind(indicator.heightProperty()); // auto adjust height
        upperThreshold.startYProperty().set(0);
        upperThreshold.endYProperty().bind(indicator.heightProperty());

        lowerThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[0])));
        lowerThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[0])));
        upperThreshold.startXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[1])));
        upperThreshold.endXProperty().bind(indicator.widthProperty().multiply(scaleThreshold(mockThreshold[1])));

        lowerThreshold.setStrokeWidth(2);
        upperThreshold.setStrokeWidth(2);

        indicator.getChildren().addAll(lowerThreshold, upperThreshold);
    }

    private double scaleThreshold(double threshold) {
        return threshold/(mockThreshold[0]+mockThreshold[1]);
    }
}
