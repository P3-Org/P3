package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RiskLabelBinder implements RiskBinderInterface {
    private final GridPane container;

    public RiskLabelBinder(GridPane container) {
        this.container = container;
    }

    /**
     * Method for looping through all the risks and gather their data and sets the color values
     * @param riskAssessment holds the RiskAssessment interface that the risk objects have been initialized based on
     * @param coordinates corresponding to the property corners
     */
    @Override
    public void applyColors(List<RiskAssessment> riskAssessment, double[][] coordinates) {
        for (RiskAssessment risk : riskAssessment) {
            AnchorPane riskLabel = findLabelForRisk(risk);
            double[] rgb = risk.getRGB();
            String style = String.format(Locale.US, "-fx-background-color: rgb(%f, %f, %f);", rgb[0], rgb[1], rgb[2]);
            riskLabel.setStyle(style);
            System.out.println(risk.getClass().getSimpleName() + " => " + Arrays.toString(rgb));
        }
    }

    /**
     * Helper method for gathering the corresponding labels to the risks in the FXML file.
     * @param risk is sent from {@code applyColors} and corresponds to a specific climate risk
     * @return the label found in the FXML file
     */
    private AnchorPane findLabelForRisk(RiskAssessment risk) {
        String id = risk.getClass().getSimpleName().replace("Risk", "").toLowerCase(Locale.ROOT);
        return (AnchorPane) container.lookup("#" + id);
    }
}