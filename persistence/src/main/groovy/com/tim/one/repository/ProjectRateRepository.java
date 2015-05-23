package com.tim.one.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tim.one.model.Project;
import com.tim.one.model.ProjectRate;

public interface ProjectRateRepository extends CrudRepository<ProjectRate, Integer> {
  List<ProjectRate> findByProject(Project project);
}
