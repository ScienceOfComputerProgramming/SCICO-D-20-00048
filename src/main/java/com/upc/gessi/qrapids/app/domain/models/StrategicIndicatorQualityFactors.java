package com.upc.gessi.qrapids.app.domain.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="strategic_indicator_quality_factors",
        uniqueConstraints = @UniqueConstraint(columnNames = {"strategic_indicator_id", "quality_factors"}))
public class StrategicIndicatorQualityFactors implements Serializable {

    // SerialVersion UID
    private static final long serialVersionUID = 14L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quality_factors")
    private String quality_factor;

    @Column(name = "weights")
    private Float weight;

    @ManyToOne
    private Strategic_Indicator strategic_indicator;

    public StrategicIndicatorQualityFactors() {
    }

    public StrategicIndicatorQualityFactors(String quality_factor, float weight) {
        setQuality_factor(quality_factor);
        setWeight(weight);
    }

    public StrategicIndicatorQualityFactors(String quality_factor, float weight, Strategic_Indicator strategic_indicator) {
        setQuality_factor(quality_factor);
        setWeight(weight);
        setStrategic_indicator(strategic_indicator);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStrategic_indicator(Strategic_Indicator strategic_indicator) {
        this.strategic_indicator = strategic_indicator;
    }

    public Strategic_Indicator getStrategic_indicator() {
        return strategic_indicator;
    }

    public void setQuality_factor(String quality_factor) {
        this.quality_factor = quality_factor;
    }

    public String getQuality_factor() {
        return quality_factor;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public Long getId() { return id; }
}
