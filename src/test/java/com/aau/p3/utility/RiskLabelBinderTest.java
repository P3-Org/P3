package com.aau.p3.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskLabelBinderTest {
//    @Test
//    void applyColors() {
//        /* Arrange */
//
//        // Sets up the containers and labels needed for test (in JavaFX)
//        GridPane labelContainer = new GridPane();
//        AnchorPane cloudBurstLabel = new AnchorPane();
//
//        // Initializes needed binder and risk list
//        RiskBinderInterface riskLabelBinder = new RiskLabelBinder(labelContainer);
//        List<RiskAssessment> riskAssessments = new ArrayList<>();
//
//        // Mocks the received data from Cloudburst so a call to the database and reading tiff files isn't needed
//        RiskAssessment mockRisk = Mockito.mock(CloudburstRisk.class);
//        //Mockito.when(mockRisk.gatherData(Mockito.any())).thenReturn(new double[] {255, 0, 0});
//        riskAssessments.add(mockRisk);
//
//        // Sets the label id and places the label inside the container
//        cloudBurstLabel.setId("cloudburst");
//        labelContainer.getChildren().add(cloudBurstLabel);
//
//        double[][] coordinates = {
//                {550900.0, 6320500.0},
//                {551100.0, 6320500.0},
//                {551100.0, 6320600.0},
//                {550900.0, 6320600.0}};
//
//        /* Act */
//        riskLabelBinder.applyColors(riskAssessments, coordinates);
//
//        /* Assert */
//        String style = cloudBurstLabel.getStyle();
//        assertEquals("-fx-background-color: rgb(255.000000, 0.000000, 0.000000);", style);
//    }
}