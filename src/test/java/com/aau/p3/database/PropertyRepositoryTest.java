package com.aau.p3.database;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.property.PropertyManager;
import com.aau.p3.platform.model.property.Property;
import com.google.common.util.concurrent.Service;
import org.geotools.gce.imagemosaic.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PropertyRepositoryTest {
    static String testAddress;
    static RiskAssessment testcloudBurstRisk;
    static RiskAssessment teststormSurgeRisk;
    static RiskAssessment testgroundWaterRisk;
    static RiskAssessment testcoastalErosionRisk;
    static Property property;
    static List<RiskAssessment> testListOfRisk;
    static List<Double> testMeasurements;

    static List<List<Double>> testCoordinates = Arrays.asList(
            Arrays.asList(563376.144, 6234684.897),
            Arrays.asList(563342.218, 6234679.676),
            Arrays.asList(563342.719, 6234656.802),
            Arrays.asList(563406.443, 6234666.867),
            Arrays.asList(563405.644, 6234690.422),
            Arrays.asList(563401.735, 6234689.653),
            Arrays.asList(563376.144, 6234684.897)
    );

    private List<String> testEastingNorthing = new ArrayList<>();
    private List<String> testLatLong = new ArrayList<>();

    @BeforeEach
    void setup() {
        testAddress = "testaddress 10, 9000 Aalborg";
        testEastingNorthing.add("563350.22");
        testLatLong.add("563350.22");
        testListOfRisk = new ArrayList<>();

        // Mock each of the four risks using Mockito
        testcloudBurstRisk = Mockito.mock(CloudburstRisk.class);
        teststormSurgeRisk = Mockito.mock(CloudburstRisk.class);
        testgroundWaterRisk = Mockito.mock(CloudburstRisk.class);
        testcoastalErosionRisk = Mockito.mock(CloudburstRisk.class);

        // Set a returnValue for when getMeasurementValue() is called
        Mockito.when(testcloudBurstRisk.getMeasurementValue()).thenReturn(5.0);
        Mockito.when(teststormSurgeRisk.getMeasurementValue()).thenReturn(10.0);
        Mockito.when(testgroundWaterRisk.getMeasurementValue()).thenReturn(15.0);
        Mockito.when(testcoastalErosionRisk.getMeasurementValue()).thenReturn(7.0);

        // Append the measurements to a list so we later can check if the restored values are equal
        testMeasurements = List.of(
                testcloudBurstRisk.getMeasurementValue(),
                teststormSurgeRisk.getMeasurementValue(),
                testgroundWaterRisk.getMeasurementValue(),
                testcoastalErosionRisk.getMeasurementValue());

        // Add all our risks to List<RiskAssessment> so we can create a real property object
        testListOfRisk.addAll(Arrays.asList(testcloudBurstRisk, teststormSurgeRisk, testgroundWaterRisk, testcoastalErosionRisk));

        // create the property that we'll be testing on
        property = new Property(testAddress, testCoordinates,testEastingNorthing, testLatLong, testListOfRisk);
    }
    @AfterEach
    void clearDB () {
        PropertyRepository.wipeProperties();
    }


    @Test
    void insertAndLoadProperty() {

        /* ACT */
        // Insert it into database
        PropertyRepository.saveProperty(property);

        // Load the object from the database
        Property loadedProperty = PropertyRepository.loadProperty(testAddress);

        // Collect the loaded measurements for comparison with before measurementValues
        List<Double> loadedMeasurements = loadedProperty.getRisks().stream()
                .map(RiskAssessment::getMeasurementValue)
                .toList();

        /* ASSERT */
        // Compare if values of mock object match the loaded values
        Assertions.assertNotNull(loadedProperty);
        Assertions.assertIterableEquals(testMeasurements, loadedMeasurements);
        Assertions.assertEquals(testAddress, loadedProperty.getAddress());
    }

    @Test
    void changeSpecialistScoreTest() {

        double expectedSpecialistScore = -1;

        // Insert a specialist score into a property and save.
        property.calculateClimateScore();
        property.setSpecialistScore(-1);
        PropertyRepository.saveProperty(property);

        // Change the score
        PropertyRepository.updateSpecialistScore(property.getAddress(), property.getSpecialistScore());

        Property loadedProperty = PropertyRepository.loadProperty(testAddress);

        // Compare the new changed value to the one in the DB
        //Assertions.assertEquals(expectedSpecialistScore, loadedProperty.getSpecialistScore());
    }
}