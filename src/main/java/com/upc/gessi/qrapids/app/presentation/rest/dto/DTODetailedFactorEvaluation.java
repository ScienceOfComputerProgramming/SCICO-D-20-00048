package com.upc.gessi.qrapids.app.presentation.rest.dto;

import java.util.List;

/**
 * This class define objects with all the information about a Quality Factor, which includes the Factor id, name and a
 * set of Metrics
 *
 * @author Oriol M./Guillem B.
 */
public class DTODetailedFactorEvaluation {

    //class attributes
    private String id;
    private String name;
    private List<DTOMetricEvaluation> metrics;

    /**
     * Constructor of the DTO of Quality Factors
     *
     * @param id The parameter defines the ID of the Factor
     * @param name The parameter defines the name of the Factor
     * @param metrics The parameter define the set of metrics that compose the Quality Factors
     */
    public DTODetailedFactorEvaluation(String id, String name, List<DTOMetricEvaluation> metrics) {
        this.id = id;
        this.name = name;
        this.metrics = metrics;
    }

    /**
     * All the getters from the class DTOQualityFactor that returns the value of the attribute defined
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

    public List<DTOMetricEvaluation> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<DTOMetricEvaluation> metrics) {
        this.metrics = metrics;
    }
}