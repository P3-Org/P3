package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Locale;

/**
 * Class that implements the RiskBinderInterface to apply colors and finding the corresponding label for a risk
 */
public class RiskLabelBinder implements RiskBinderInterface {
    private final GridPane container;

    /**
     * Constructor
     * @param container The FXML GridPane container with the corresponding tag, containing the risks and their values
     */
    public RiskLabelBinder(GridPane container) {
        this.container = container;
    }

    /**
     * Method for looping through all the risks, gathering their data, and setting the color values
     * @param riskAssessment holds a list of RiskAssement objects
     * @param coordinates corresponding to the property corners
     */
    @Override
    public void applyColors(List<RiskAssessment> riskAssessment, double[][] coordinates) {
        for (RiskAssessment risk : riskAssessment) {
            AnchorPane riskLabel = findLabelForRisk(risk);
            double[] rgb = risk.getRGB();
            double alpha = 0.65; // Constant opacity value for RGBA
            // Formats the RGBA value as a string
            String style = String.format(Locale.US, "-fx-background-color: rgba(%f, %f, %f, %.3f);", rgb[0], rgb[1], rgb[2], alpha);
            riskLabel.setStyle(style);
        }
    }

    /**
     * Helper method for gathering the corresponding labels to the risks in the FXML file.
     * @param risk is sent from {@code applyColors} and corresponds to a specific climate risk
     * @return the label found in the FXML file
     */
    private AnchorPane findLabelForRisk(RiskAssessment risk) {
        String id = risk.getRiskType();
        return (AnchorPane) container.lookup("#" + id);
    }
}