package com.upc.gessi.qrapids.app.domain.services;

import com.upc.gessi.qrapids.app.domain.adapters.Forecast;
import com.upc.gessi.qrapids.app.domain.adapters.QMA.QMADetailedStrategicIndicators;
import com.upc.gessi.qrapids.app.domain.adapters.QMA.QMAFakedata;
import com.upc.gessi.qrapids.app.domain.adapters.QMA.QMAStrategicIndicators;
import com.upc.gessi.qrapids.app.exceptions.CategoriesException;
import com.upc.gessi.qrapids.app.domain.repositories.StrategicIndicator.StrategicIndicatorRepository;
import com.upc.gessi.qrapids.app.database.repositories.Strategic_Indicator.Strategic_IndicatorRepositoryImpl;
import com.upc.gessi.qrapids.app.dto.*;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
public class StrategicIndicators {

    @Autowired
    private Strategic_IndicatorRepositoryImpl kpirep;

    @Autowired
    private QMAStrategicIndicators qmasi;

    @Autowired
    private QMADetailedStrategicIndicators qmadsi;

    @Autowired
    private QMAFakedata qmafake;

    @Autowired
    private StrategicIndicatorRepository siRep;

    @Autowired
    private Forecast qmaf;

    @GetMapping("/api/strategicIndicators/current")
    @ResponseStatus(HttpStatus.OK)
    public List<DTOStrategicIndicatorEvaluation> getStrategicIndicatorsEvaluation(@RequestParam(value = "prj") String prj) {
        if (qmafake.usingFakeData()) {
            return kpirep.CurrentEvaluation();
        } else {
            try {
                return qmasi.CurrentEvaluation(prj);
            } catch (ElasticsearchStatusException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
            } catch (CategoriesException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "The categories do not match");
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
            }

        }
    }

    @GetMapping("/api/strategicIndicators/{id}/current")
    @ResponseStatus(HttpStatus.OK)
    public DTOStrategicIndicatorEvaluation getSingleStrategicIndicatorEvaluation(@RequestParam("prj") String prj, @PathVariable String id) {
        try {
            return qmasi.SingleCurrentEvaluation(prj, id);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        } catch (CategoriesException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The categories do not match");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/strategicIndicators/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<DTOStrategicIndicatorEvaluation> getStrategicIndicatorsHistoricalData(@RequestParam(value = "prj", required=false) String prj, @RequestParam("from") String from, @RequestParam("to") String to) {
        if (qmafake.usingFakeData()) {
            return kpirep.HistoricalData();
        } else {
            try {
                return qmasi.HistoricalData(LocalDate.parse(from), LocalDate.parse(to), prj);
            } catch (ElasticsearchStatusException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
            } catch (CategoriesException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "The categories do not match");
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
            }
        }
    }

    @GetMapping("/api/strategicIndicators/qualityFactors/current")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getDetailedSI(@RequestParam(value = "prj", required=false) String prj) {
        try {
            return qmadsi.CurrentEvaluation(null, prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/strategicIndicators/{id}/qualityFactors/current")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getDetailedSIbyID(@RequestParam(value = "prj", required=false) String prj, @PathVariable String id) {
        try {
            return qmadsi.CurrentEvaluation(id, prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/strategicIndicators/qualityFactors/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getDetailedSIHistorical(@RequestParam(value = "prj", required=false) String prj, @RequestParam("from") String from, @RequestParam("to") String to) {
        try {
            return qmadsi.HistoricalData(null, LocalDate.parse(from), LocalDate.parse(to), prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/strategicIndicators/{id}/qualityFactors/historical")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getDetailedSIHistorical(@RequestParam(value = "prj", required=false) String prj, @PathVariable String id, @RequestParam("from") String from, @RequestParam("to") String to) {
        try {
            return qmadsi.HistoricalData(id, LocalDate.parse(from), LocalDate.parse(to), prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/api/strategicIndicators/{id}/qualityFactors/prediction")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getQualityFactorsPredicitionData(@RequestParam(value = "prj", required=false) String prj, @RequestParam("technique") String technique, @RequestParam("horizon") String horizon, @PathVariable String id) throws IOException {
        try {
            return qmaf.ForecastDSI(qmadsi.CurrentEvaluation(id, prj), technique, "7", horizon, prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        }
    }

    @GetMapping("/api/strategicIndicators/qualityFactors/prediction")
    @ResponseStatus(HttpStatus.OK)
    public List<DTODetailedStrategicIndicator> getQualityFactorsPredicitionData(@RequestParam(value = "prj", required=false) String prj, @RequestParam("technique") String technique, @RequestParam("horizon") String horizon) throws IOException {
        try {
            return qmaf.ForecastDSI(qmadsi.CurrentEvaluation(null, prj), technique, "7", horizon, prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        }
    }

    @GetMapping("/api/strategicIndicators/prediction")
    @ResponseStatus(HttpStatus.OK)
    public List<DTOStrategicIndicatorEvaluation> getStrategicIndicatorsPrediction(@RequestParam(value = "prj", required=false) String prj, @RequestParam("technique") String technique, @RequestParam("horizon") String horizon) throws IOException {
        try {
            return qmaf.ForecastSI(technique, "7", horizon, prj);
        } catch (ElasticsearchStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The project identifier does not exist");
        }
    }

    /*private List<DTOStrategicIndicatorEvaluation> mergeData(List<DTOStrategicIndicatorEvaluation> apiEval, List<Strategic_Indicator> dbEval) {
        boolean found = false;
        String lastSIid = "";
        for (Iterator<DTOStrategicIndicatorEvaluation> itAPI = apiEval.iterator(); itAPI.hasNext();) {
            DTOStrategicIndicatorEvaluation itemAPI = itAPI.next();
            found = false;
            if (lastSIid.equals(itemAPI.getId())) {
                found = true;
            } else {
                lastSIid = itemAPI.getId();
            }
            for (Iterator<Strategic_Indicator> itDB = dbEval.iterator(); itDB.hasNext() && !found;) {
                Strategic_Indicator itemDB = itDB.next();
                if (itemAPI.getId().equals(itemDB.getName().replaceAll("\\s+","").toLowerCase())) {
                    itemAPI.setLowerThreshold(0.33f);
                    itemAPI.setUpperThreshold(0.66f);
                    itemAPI.setTarget(0.5f);
                    itDB.remove();
                    found = true;
                }
            }
        }
        return apiEval;
    }*/
}
