package com.tim.one.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.ProjectFinancialData;

public interface ProjectFinancialDataRepository extends CrudRepository<ProjectFinancialData, Integer> {
    ProjectFinancialData findProjectFinancialDataByAccount(@Param("account") String account);
}
