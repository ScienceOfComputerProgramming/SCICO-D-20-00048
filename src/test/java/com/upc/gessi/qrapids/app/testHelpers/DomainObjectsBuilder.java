package com.upc.gessi.qrapids.app.testHelpers;

import com.upc.gessi.qrapids.app.domain.models.*;
import com.upc.gessi.qrapids.app.dto.*;
import org.springframework.data.util.Pair;
import qr.models.FixedPart;
import qr.models.Form;
import qr.models.QualityRequirementPattern;

import java.time.LocalDate;
import java.util.*;

public class DomainObjectsBuilder {

    public Project buildProject() {
        Long projectId = 1L;
        String projectExternalId = "test";
        String projectName = "Test";
        String projectDescription = "Test project";
        String projectBacklogId = "prj-1";
        Project project = new Project(projectExternalId, projectName, projectDescription, null, true);
        project.setId(projectId);
        project.setBacklogId(projectBacklogId);
        return project;
    }

    public Alert buildAlert(Project project) {
        long alertId = 2L;
        String idElement = "id";
        String name = "Duplication";
        AlertType alertType = AlertType.METRIC;
        float value = 0.4f;
        float threshold = 0.5f;
        String category = "category";
        Date date = new Date();
        AlertStatus alertStatus = AlertStatus.NEW;
        Alert alert = new Alert(idElement, name, alertType, value, threshold, category, date, alertStatus, true, project);
        alert.setId(alertId);
        return alert;
    }

    public Strategic_Indicator buildStrategicIndicator (Project project) {
        Long strategicIndicatorId = 1L;
        String strategicIndicatorName = "Product Quality";
        String strategicIndicatorDescription = "Quality of the product built";
        List<String> qualityFactors = new ArrayList<>();
        String factor1 = "codequality";
        qualityFactors.add(factor1);
        String factor2 = "softwarestability";
        qualityFactors.add(factor2);
        String factor3 = "testingstatus";
        qualityFactors.add(factor3);
        Strategic_Indicator strategicIndicator = new Strategic_Indicator(strategicIndicatorName, strategicIndicatorDescription, null, qualityFactors, project);
        strategicIndicator.setId(strategicIndicatorId);
        return strategicIndicator;
    }

    public DTOStrategicIndicatorEvaluation buildDtoStrategicIndicatorEvaluation (Strategic_Indicator strategicIndicator) {
        List<DTOSIAssesment> dtoSIAssessmentList = new ArrayList<>();

        Long assessment1Id = 10L;
        String assessment1Label = "Good";
        Float assessment1Value = null;
        String assessment1Color = "#00ff00";
        Float assessment1UpperThreshold = 0.66f;
        DTOSIAssesment dtoSIAssesment1 = new DTOSIAssesment(assessment1Id, assessment1Label, assessment1Value, assessment1Color, assessment1UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssesment1);

        Long assessment2Id = 11L;
        String assessment2Label = "Neutral";
        Float assessment2Value = null;
        String assessment2Color = "#ff8000";
        Float assessment2UpperThreshold = 0.33f;
        DTOSIAssesment dtoSIAssessment2 = new DTOSIAssesment(assessment2Id, assessment2Label, assessment2Value, assessment2Color, assessment2UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssessment2);

        Long assessment3Id = 11L;
        String assessment3Label = "Bad";
        Float assessment3Value = null;
        String assessment3Color = "#ff0000";
        Float assessment3UpperThreshold = 0f;
        DTOSIAssesment dtoSIAssessment3 = new DTOSIAssesment(assessment3Id, assessment3Label, assessment3Value, assessment3Color, assessment3UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssessment3);

        Float strategicIndicatorValue = 0.7f;
        String strategicIndicatorCategory = "Good";
        Pair<Float, String> strategicIndicatorValuePair = Pair.of(strategicIndicatorValue, strategicIndicatorCategory);
        String datasource = "Q-Rapdis Dashboard";
        String categoriesDescription = "[Good (0,67), Neutral (0,33), Bad (0,00)]";
        DTOStrategicIndicatorEvaluation dtoStrategicIndicatorEvaluation = new DTOStrategicIndicatorEvaluation(strategicIndicator.getExternalId(), strategicIndicator.getName(), strategicIndicator.getDescription(), strategicIndicatorValuePair, dtoSIAssessmentList, LocalDate.now(), datasource, strategicIndicator.getId(), categoriesDescription, false);
        dtoStrategicIndicatorEvaluation.setHasFeedback(false);
        dtoStrategicIndicatorEvaluation.setForecastingError(null);

        return dtoStrategicIndicatorEvaluation;
    }

    public QualityRequirementPattern buildQualityRequirementPattern () {
        String formText = "The ratio of files without duplications should be at least %value%";
        FixedPart fixedPart = new FixedPart(formText);
        String formName = "Duplications";
        String formDescription = "The ratio of files without duplications should be at least the given value";
        String formComments = "No comments";
        Form form = new Form(formName, formDescription, formComments, fixedPart);
        List<Form> formList = new ArrayList<>();
        formList.add(form);
        Integer requirementId = 100;
        String requirementName = "Duplications";
        String requirementComments = "No comments";
        String requirementDescription = "No description";
        String requirementGoal = "Improve the quality of the source code";
        String requirementCostFunction = "No cost function";
        QualityRequirementPattern qualityRequirementPattern = new QualityRequirementPattern(requirementId, requirementName, requirementComments, requirementDescription, requirementGoal, formList, requirementCostFunction);

        return qualityRequirementPattern;
    }

    public Decision buildDecision (Project project, DecisionType type) {
        Long decisionId = 2L;
        DecisionType decisionType = type;
        Date date = new Date();
        String rationale = "User comments";
        int patternId = 100;
        Decision decision = new Decision(decisionType, date, null, rationale, patternId, project);
        decision.setId(decisionId);
        return decision;
    }

    public QualityRequirement buildQualityRequirement (Alert alert, Decision decision, Project project) {
        Long requirementId = 3L;
        String requirement = "The ratio of files without duplications should be at least 0.8";
        String description = "The ratio of files without duplications should be at least the given value";
        String goal = "Improve the quality of the source code";
        String qrBacklogUrl =  "https://backlog.example/issue/999";
        String qrBacklogId = "ID-999";
        QualityRequirement qualityRequirement = new QualityRequirement(requirement, description, goal, alert, decision, project);
        qualityRequirement.setId(requirementId);
        qualityRequirement.setBacklogUrl(qrBacklogUrl);
        qualityRequirement.setBacklogId(qrBacklogId);
        return qualityRequirement;
    }

    public DTODecisionQualityRequirement buildDecisionWithQualityRequirement (QualityRequirement qualityRequirement) {
        return new DTODecisionQualityRequirement(qualityRequirement.getDecision().getId(),
                qualityRequirement.getDecision().getType(),
                qualityRequirement.getDecision().getDate(),
                null,
                qualityRequirement.getDecision().getRationale(),
                qualityRequirement.getDecision().getPatternId(),
                qualityRequirement.getRequirement(),
                qualityRequirement.getDescription(),
                qualityRequirement.getGoal(),
                qualityRequirement.getBacklogId(),
                qualityRequirement.getBacklogUrl());
    }

    public DTODecisionQualityRequirement buildDecisionWithoutQualityRequirement (Decision decision) {
        return new DTODecisionQualityRequirement(decision.getId(),
                decision.getType(),
                decision.getDate(),
                null,
                decision.getRationale(),
                decision.getPatternId(),
                null,
                null,
                null,
                null,
                null);
    }

    public DTOQualityFactor buildDTOQualityFactor () {
        String metricId = "fasttests";
        String metricName = "Fast Tests";
        String metricDescription = "Percentage of tests under the testing duration threshold";
        float metricValue = 0.8f;
        LocalDate evaluationDate = LocalDate.now();
        String metricRationale = "parameters: {...}, formula: ...";
        DTOMetric dtoMetric = new DTOMetric(metricId, metricName, metricDescription, null, metricRationale, evaluationDate, metricValue);
        List<DTOMetric> dtoMetricList = new ArrayList<>();
        dtoMetricList.add(dtoMetric);

        String factorId = "testingperformance";
        String factorName = "Testing Performance";
        return new DTOQualityFactor(factorId, factorName, dtoMetricList);
    }

    public DTOQualityFactor buildDTOQualityFactorForPrediction () {
        String metricId = "fasttests";
        String metricName = "Fast Tests";
        String metricDescription = "Percentage of tests under the testing duration threshold";
        String metricDataSource = "Forecast";
        Double metricValue = 0.8;
        LocalDate evaluationDate = LocalDate.now();
        String metricRationale = "Forecast";
        DTOMetric dtoMetric = new DTOMetric(metricId, metricName, metricDescription, metricDataSource, metricRationale, evaluationDate, metricValue.floatValue());
        Double first80 = 0.97473043;
        Double second80 = 0.9745246;
        Pair<Float, Float> confidence80 = Pair.of(first80.floatValue(), second80.floatValue());
        dtoMetric.setConfidence80(confidence80);
        Double first95 = 0.9747849;
        Double second95 = 0.97447014;
        Pair<Float, Float> confidence95 = Pair.of(first95.floatValue(), second95.floatValue());
        dtoMetric.setConfidence95(confidence95);
        List<DTOMetric> dtoMetricList = new ArrayList<>();
        dtoMetricList.add(dtoMetric);

        String factorId = "testingperformance";
        String factorName = "Testing Performance";
        return new DTOQualityFactor(factorId, factorName, dtoMetricList);
    }

    public DTOFactor buildDTOFactor () {
        String factorId = "testingperformance";
        String factorName = "Testing Performance";
        String factorDescription = "Performance of the tests";
        float factorValue = 0.8f;
        LocalDate evaluationDate = LocalDate.now();
        String factorRationale = "parameters: {...}, formula: ...";
        String strategicIndicator = "processperformance";
        List<String> strategicIndicatorsList = new ArrayList<>();
        strategicIndicatorsList.add(strategicIndicator);
        return new DTOFactor(factorId, factorName, factorDescription, factorValue, evaluationDate, null, factorRationale, strategicIndicatorsList);
    }

    public DTOMetric buildDTOMetric () {
        String metricId = "fasttests";
        String metricName = "Fast Tests";
        String metricDescription = "Percentage of tests under the testing duration threshold";
        float metricValue = 0.8f;
        LocalDate evaluationDate = LocalDate.now();
        String metricRationale = "parameters: {...}, formula: ...";
        return new DTOMetric(metricId, metricName, metricDescription, null, metricRationale, evaluationDate, metricValue);
    }

    public DTOStrategicIndicatorEvaluation buildDTOStrategicIndicatorEvaluation () {
        List<DTOSIAssesment> dtoSIAssessmentList = new ArrayList<>();

        Long assessment1Id = 10L;
        String assessment1Label = "Good";
        Float assessment1Value = null;
        String assessment1Color = "#00ff00";
        Float assessment1UpperThreshold = 0.66f;
        DTOSIAssesment dtoSIAssesment1 = new DTOSIAssesment(assessment1Id, assessment1Label, assessment1Value, assessment1Color, assessment1UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssesment1);

        Long assessment2Id = 11L;
        String assessment2Label = "Neutral";
        Float assessment2Value = null;
        String assessment2Color = "#ff8000";
        Float assessment2UpperThreshold = 0.33f;
        DTOSIAssesment dtoSIAssesment2 = new DTOSIAssesment(assessment2Id, assessment2Label, assessment2Value, assessment2Color, assessment2UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssesment2);

        Long assessment3Id = 11L;
        String assessment3Label = "Bad";
        Float assessment3Value = null;
        String assessment3Color = "#ff0000";
        Float assessment3UpperThreshold = 0f;
        DTOSIAssesment dtoSIAssesment3 = new DTOSIAssesment(assessment3Id, assessment3Label, assessment3Value, assessment3Color, assessment3UpperThreshold);
        dtoSIAssessmentList.add(dtoSIAssesment3);

        String strategicIndicatorId = "processperformance";
        Long strategicIndicatorDbId = 1L;
        String strategicIndicatorName = "Process Performance";
        String strategicIndicatorDescription = "Performance of the processes involved in the development";
        Float strategicIndicatorValue = 0.8f;
        String strategicIndicatorCategory = "Good";
        Pair<Float, String> strategicIndicatorValuePair = Pair.of(strategicIndicatorValue, strategicIndicatorCategory);
        String dateString = "2019-07-07";
        LocalDate date = LocalDate.parse(dateString);
        String datasource = "Q-Rapdis Dashboard";
        String categoriesDescription = "[Good (0,67), Neutral (0,33), Bad (0,00)]";
        DTOStrategicIndicatorEvaluation dtoStrategicIndicatorEvaluation = new DTOStrategicIndicatorEvaluation(strategicIndicatorId, strategicIndicatorName, strategicIndicatorDescription, strategicIndicatorValuePair, dtoSIAssessmentList, date, datasource, strategicIndicatorDbId, categoriesDescription, false);
        dtoStrategicIndicatorEvaluation.setHasFeedback(false);
        dtoStrategicIndicatorEvaluation.setForecastingError(null);
        return dtoStrategicIndicatorEvaluation;
    }

    public List<SICategory> buildSICategoryList () {
        Long strategicIndicatorGoodCategoryId = 10L;
        String strategicIndicatorGoodCategoryName = "Good";
        String strategicIndicatorGoodCategoryColor = "#00ff00";
        SICategory siGoodCategory = new SICategory(strategicIndicatorGoodCategoryName, strategicIndicatorGoodCategoryColor);
        siGoodCategory.setId(strategicIndicatorGoodCategoryId);

        Long strategicIndicatorNeutralCategoryId = 11L;
        String strategicIndicatorNeutralCategoryName = "Neutral";
        String strategicIndicatorNeutralCategoryColor = "#ff8000";
        SICategory siNeutralCategory = new SICategory(strategicIndicatorNeutralCategoryName, strategicIndicatorNeutralCategoryColor);
        siNeutralCategory.setId(strategicIndicatorNeutralCategoryId);

        Long strategicIndicatorBadCategoryId = 12L;
        String strategicIndicatorBadCategoryName = "Bad";
        String strategicIndicatorBadCategoryColor = "#ff0000";
        SICategory siBadCategory = new SICategory(strategicIndicatorBadCategoryName, strategicIndicatorBadCategoryColor);
        siBadCategory.setId(strategicIndicatorBadCategoryId);

        List<SICategory> siCategoryList = new ArrayList<>();
        siCategoryList.add(siGoodCategory);
        siCategoryList.add(siNeutralCategory);
        siCategoryList.add(siBadCategory);

        return siCategoryList;
    }

    public List<Map<String, String>> buildRawSICategoryList () {
        String strategicIndicatorGoodCategoryName = "Good";
        String strategicIndicatorGoodCategoryColor = "#00ff00";
        Map<String, String> strategicIndicatorGoodCategory = new HashMap<>();
        strategicIndicatorGoodCategory.put("name", strategicIndicatorGoodCategoryName);
        strategicIndicatorGoodCategory.put("color", strategicIndicatorGoodCategoryColor);

        String strategicIndicatorNeutralCategoryName = "Neutral";
        String strategicIndicatorNeutralCategoryColor = "#ff8000";
        Map<String, String> strategicIndicatorNeutralCategory = new HashMap<>();
        strategicIndicatorNeutralCategory.put("name", strategicIndicatorNeutralCategoryName);
        strategicIndicatorNeutralCategory.put("color", strategicIndicatorNeutralCategoryColor);

        String strategicIndicatorBadCategoryName = "Bad";
        String strategicIndicatorBadCategoryColor = "#ff0000";
        Map<String, String> strategicIndicatorBadCategory = new HashMap<>();
        strategicIndicatorBadCategory.put("name", strategicIndicatorBadCategoryName);
        strategicIndicatorBadCategory.put("color", strategicIndicatorBadCategoryColor);

        List<Map<String, String>> strategicIndicatorCategoriesList = new ArrayList<>();
        strategicIndicatorCategoriesList.add(strategicIndicatorGoodCategory);
        strategicIndicatorCategoriesList.add(strategicIndicatorNeutralCategory);
        strategicIndicatorCategoriesList.add(strategicIndicatorBadCategory);

        return strategicIndicatorCategoriesList;
    }

    public List<QFCategory> buildFactorCategoryList () {
        Long factorGoodCategoryId = 10L;
        String factorGoodCategoryName = "Good";
        String factorGoodCategoryColor = "#00ff00";
        float factorGoodCategoryUpperThreshold = 1f;
        QFCategory factorGoodCategory = new QFCategory(factorGoodCategoryName, factorGoodCategoryColor, factorGoodCategoryUpperThreshold);
        factorGoodCategory.setId(factorGoodCategoryId);

        Long factorNeutralCategoryId = 11L;
        String factorNeutralCategoryName = "Neutral";
        String factorNeutralCategoryColor = "#ff8000";
        float factorNeutralCategoryUpperThreshold = 0.67f;
        QFCategory factorNeutralCategory = new QFCategory(factorNeutralCategoryName, factorNeutralCategoryColor, factorNeutralCategoryUpperThreshold);
        factorNeutralCategory.setId(factorNeutralCategoryId);

        Long factorBadCategoryId = 12L;
        String factorBadCategoryName = "Bad";
        String factorBadCategoryColor = "#ff0000";
        float factorBadCategoryUpperThreshold = 0.33f;
        QFCategory factorBadCategory = new QFCategory(factorBadCategoryName, factorBadCategoryColor, factorBadCategoryUpperThreshold);
        factorBadCategory.setId(factorBadCategoryId);

        List<QFCategory> factorCategoryList = new ArrayList<>();
        factorCategoryList.add(factorGoodCategory);
        factorCategoryList.add(factorNeutralCategory);
        factorCategoryList.add(factorBadCategory);

        return factorCategoryList;
    }

    public List<Map<String,String>> buildRawFactorCategoryList () {
        String factorGoodCategoryName = "Good";
        String factorGoodCategoryColor = "#00ff00";
        float factorGoodCategoryUpperThreshold = 1.0f;
        Map<String, String> factorGoodCategory = new HashMap<>();
        factorGoodCategory.put("name", factorGoodCategoryName);
        factorGoodCategory.put("color", factorGoodCategoryColor);
        factorGoodCategory.put("upperThreshold", Float.toString(factorGoodCategoryUpperThreshold));

        String factorNeutralCategoryName = "Neutral";
        String factorNeutralCategoryColor = "#ff8000";
        float factorNeutralCategoryUpperThreshold = 0.67f;
        Map<String, String> factorNeutralCategory = new HashMap<>();
        factorNeutralCategory.put("name", factorNeutralCategoryName);
        factorNeutralCategory.put("color", factorNeutralCategoryColor);
        factorNeutralCategory.put("upperThreshold", Float.toString(factorNeutralCategoryUpperThreshold));

        String factorBadCategoryName = "Bad";
        String factorBadCategoryColor = "#ff0000";
        float factorBadCategoryUpperThreshold = 0.33f;
        Map<String, String> factorBadCategory = new HashMap<>();
        factorBadCategory.put("name", factorBadCategoryName);
        factorBadCategory.put("color", factorBadCategoryColor);
        factorBadCategory.put("upperThreshold", Float.toString(factorBadCategoryUpperThreshold));

        List<Map<String, String>> factorCategoriesList = new ArrayList<>();
        factorCategoriesList.add(factorGoodCategory);
        factorCategoriesList.add(factorNeutralCategory);
        factorCategoriesList.add(factorBadCategory);

        return factorCategoriesList;
    }

    public List<MetricCategory> buildMetricCategoryList () {
        Long metricGoodCategoryId = 10L;
        String metricGoodCategoryName = "Good";
        String metricGoodCategoryColor = "#00ff00";
        float metricGoodCategoryUpperThreshold = 1f;
        MetricCategory metricGoodCategory = new MetricCategory(metricGoodCategoryName, metricGoodCategoryColor, metricGoodCategoryUpperThreshold);
        metricGoodCategory.setId(metricGoodCategoryId);

        Long metricNeutralCategoryId = 11L;
        String metricNeutralCategoryName = "Neutral";
        String metricNeutralCategoryColor = "#ff8000";
        float metricNeutralCategoryUpperThreshold = 0.67f;
        MetricCategory metricNeutralCategory = new MetricCategory(metricNeutralCategoryName, metricNeutralCategoryColor, metricNeutralCategoryUpperThreshold);
        metricNeutralCategory.setId(metricNeutralCategoryId);

        Long metricBadCategoryId = 12L;
        String metricBadCategoryName = "Bad";
        String metricBadCategoryColor = "#ff0000";
        float metricBadCategoryUpperThreshold = 0.33f;
        MetricCategory metricBadCategory = new MetricCategory(metricBadCategoryName, metricBadCategoryColor, metricBadCategoryUpperThreshold);
        metricBadCategory.setId(metricBadCategoryId);

        List<MetricCategory> metricCategoryList = new ArrayList<>();
        metricCategoryList.add(metricGoodCategory);
        metricCategoryList.add(metricNeutralCategory);
        metricCategoryList.add(metricBadCategory);

        return metricCategoryList;
    }
}
