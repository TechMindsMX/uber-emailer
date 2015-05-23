package com.tim.one.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;

public interface ProjectUnitSaleRepository extends CrudRepository<ProjectUnitSale, Integer> {
	List<ProjectUnitSale> findByProjectFinancialData(ProjectFinancialData projectFinancialData);
}
