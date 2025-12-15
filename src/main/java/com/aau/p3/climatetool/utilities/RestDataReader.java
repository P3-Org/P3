package com.aau.p3.climatetool.utilities;

import java.util.List;

/**
 * Interface for fetching risk data and extracting values
 */
public interface RestDataReader {
    void riskFetch(String query);
    List<Double> extractValues(StringBuilder response);
}
