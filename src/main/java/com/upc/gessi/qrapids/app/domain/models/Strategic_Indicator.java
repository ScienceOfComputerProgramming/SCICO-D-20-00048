package com.upc.gessi.qrapids.app.domain.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/*
    This class is not following the class naming rules, it should be named as StrategicIndicator, without '_', but
    there is an unsolved issue with the hibernate and we need to add the '_' in order to have de name
    'strategic_indicator_quality_factors name for the table in the database, the table storing the quality_factors list
 */

//TODO: refactor to have the name of the table 'strategic_indicator_quality_factor' independent to the this class name

@Entity
@Table(name="strategic_indicator",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "projectId"}))
public class Strategic_Indicator implements Serializable {

    // SerialVersion UID
    private static final long serialVersionUID = 14L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="external_id")
    private String externalId;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "network")
    private byte[] network;

    @Column(name = "weighted")
    boolean weighted;

    // we need to keep the name of this list as quality_factors, it is the name of the table in the database
    // ToDo: This should be changed, the name of the local variables should be no directly connected to table names in database
    //@ElementCollection(fetch = FetchType.EAGER)
    //private List<String> quality_factors = new ArrayList<String>();

    @OneToMany
    @JoinColumn(name="strategic_indicator_id")
    private List<StrategicIndicatorQualityFactors> quality_factors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="projectId", referencedColumnName = "id")
    private Project project;


    public Strategic_Indicator() {
    }

    public Strategic_Indicator(String name, String description, byte[] network, List<StrategicIndicatorQualityFactors> quality_factors, boolean weighted, Project project) {
        setName(name);
        setDescription(description);
        setNetwork(network);
        setQuality_factors(quality_factors);
        setWeighted(weighted);
        setProject(project);
    }

    // Strategic Indicator without Quality Factors
    public Strategic_Indicator(String name, String description, byte[] network, Project project) {
        setName(name);
        setDescription(description);
        setNetwork(network);
        setProject(project);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExternalID (String externalId)  {
        this.externalId = externalId;
    }

    public String getExternalId () {
        if (this.externalId == null || this.externalId.isEmpty()) {
            this.externalId = name.replaceAll("\\s+","").toLowerCase();
        }
        return this.externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (this.externalId == null || this.externalId.isEmpty()) {
            this.externalId = name.replaceAll("\\s+","").toLowerCase();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getNetwork() {
        return network;
    }

    public void setNetwork(byte[] network) {
        this.network = network;
    }

    public List<String> getQuality_factors() {
        List<String> quality_factors = new ArrayList<>();
        for (int i = 0; i < this.quality_factors.size(); i ++) {
            quality_factors.add(this.quality_factors.get(i).getQuality_factor());
        }
        return quality_factors;
    }

    public List<String> getWeights() {
        List<String> quality_factors_weights = new ArrayList<>();
        for (int i = 0; i < this.quality_factors.size(); i ++) {
            quality_factors_weights.add(this.quality_factors.get(i).getQuality_factor());
            quality_factors_weights.add(String.valueOf(this.quality_factors.get(i).getWeight()));
        }
        return quality_factors_weights;
    }

    public void setQuality_factors(List<StrategicIndicatorQualityFactors> quality_factors) {
        this.quality_factors = quality_factors;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public boolean isWeighted() {
        return weighted;
    }
}
