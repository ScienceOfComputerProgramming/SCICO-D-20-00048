package com.upc.gessi.qrapids.app.dto;

import java.time.LocalDate;
import java.util.List;

/**
*This class define objects with all the information about a DetailedStrategicIndicator, which includes the SI id and name
*and a set of its factors
*
* @author: Oriol M/Guillem B
* */
public class DTODetailedStrategicIndicator {

    //class atributes
    private String id;
    private String name;
    private LocalDate date;
    private int mismatchDays;
    private List<String> missingFactors;
    private List<DTOFactor> factors;

    /**
     * Constructor of the DTO of Detailed Strategic Indicators
     *
     * @param id The parameter define the ID of the Strategic Indicator
     * @param name The parameter define the name of the Strategic Indicator
     * @param factors The parameter define the set of factors that compose the Strategic Indicator
     * */
    public DTODetailedStrategicIndicator(String id, String name, List<DTOFactor> factors) {
        this.id = id;
        this.name = name;
        this.factors = factors;
    }

    /**
     * All the getters from the class DTODetailedStrategicIndicators that returns the value of the attribute defined
     * in the header of the getter
     *
     * @return the value of the attribute defined in the header of the getter
     */

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name.isEmpty() ? this.id : this.name;
    }

    public List<DTOFactor> getFactors() {
        return factors;
    }

    public void setFactors(List<DTOFactor> factors) {
        this.factors = factors;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMismatchDays() {
        return mismatchDays;
    }

    public void setMismatchDays(int mismatchDays) {
        this.mismatchDays = mismatchDays;
    }

    public List<String> getMissingFactors() {
        return missingFactors;
    }

    public void setMissingFactors(List<String> missingFactors) {
        this.missingFactors = missingFactors;
    }
}