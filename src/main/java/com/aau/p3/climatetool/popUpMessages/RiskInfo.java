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
     * @param general
     * @param threshold
     * @param calculation
     * @param precaution
     */
    public RiskInfo(String general, String threshold, String calculation, String precaution){
        this.generalInfo = general;
        this.thresholdInfo = threshold;
        this.calculationInfo = calculation;
        this.precautionInfo = precaution;
    }

    // A series of getters, to get the strings that hold the information regarding a climate risk.
    /**
     * Getter method
     * @return GeneralInfo from the RiskInfo Class
     */
    public String getGeneralInfo() {return generalInfo;}
    /**
     * Getter method
     * @return ThresholdInfo from the RiskInfo Class
     */
    public String getThresholdInfo() {return thresholdInfo;}
    /**
     * Getter method
     * @return CalculationInfo from the RiskInfo Class
     */
    public String getCalculationInfo() {return calculationInfo;}
    /**
     * Getter method
     * @return PrecationInfo from the RiskInfo Class
     */
    public String getPrecautionInfo() {return precautionInfo;}

}
