package com.aau.p3.climatetool.popUpMessages;

/**
 * Class that will hold up to four strings, each a description of what a climate risk is, or how it works.
 */
public class RiskInfo {
    private String generalInfo;
    private String thresholdInfo;
    private String calculationInfo;
    private String precautionInfo;

    /**
     * Constructor, that enables the use of getters to reach the climate risk information.
     */
    public RiskInfo(String general, String threshold, String calculation, String precaution) {
        this.generalInfo = general;
        this.thresholdInfo = threshold;
        this.calculationInfo = calculation;
        this.precautionInfo = precaution;
    }

    /**
     * This is a getter method.
     * @return GeneralInfo from the RiskInfo Class
     */
    public String getGeneralInfo() { return generalInfo; }
    /**
     * This is a getter method.
     * @return ThresholdInfo from the RiskInfo Class
     */
    public String getThresholdInfo() { return thresholdInfo; }
    /**
     * This is a getter method.
     * @return CalculationInfo from the RiskInfo Class
     */
    public String getCalculationInfo() { return calculationInfo; }
    /**
     * This is a getter method.
     * @return PrecationInfo from the RiskInfo Class
     */
    public String getPrecautionInfo() { return precautionInfo; }

}
