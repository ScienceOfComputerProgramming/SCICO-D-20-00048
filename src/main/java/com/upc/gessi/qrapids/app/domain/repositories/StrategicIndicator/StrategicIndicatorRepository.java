package com.upc.gessi.qrapids.app.domain.repositories.StrategicIndicator;

import com.upc.gessi.qrapids.app.domain.models.Strategic_Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StrategicIndicatorRepository extends JpaRepository<Strategic_Indicator, Long>, PagingAndSortingRepository<Strategic_Indicator,Long>, CustomStrategicIndicatorRepository {
    List<Strategic_Indicator> findByProject_Id (Long projectId);
}
