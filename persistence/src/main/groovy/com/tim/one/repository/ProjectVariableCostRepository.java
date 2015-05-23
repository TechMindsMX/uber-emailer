package com.tim.one.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectVariableCost;

public interface ProjectVariableCostRepository extends CrudRepository<ProjectVariableCost, Integer> {
  List<ProjectVariableCost> findByProjectFinancialData(ProjectFinancialData projectFinancialData);
}
