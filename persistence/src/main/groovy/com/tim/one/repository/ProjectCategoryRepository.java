package com.tim.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.ProjectCategory;

public interface ProjectCategoryRepository extends CrudRepository<ProjectCategory, Integer> {
  @Query("SELECT c FROM ProjectCategory c WHERE c.father IS NULL")
  List<ProjectCategory> findCategories();
  @Query("SELECT c FROM ProjectCategory c WHERE c.father = :categoryId")
  List<ProjectCategory> findSubCategories(@Param("categoryId") Integer categoryId);
  @Query("SELECT c FROM ProjectCategory c WHERE c.father IS NOT NULL")
  List<ProjectCategory> findAllSubCategories();
}
