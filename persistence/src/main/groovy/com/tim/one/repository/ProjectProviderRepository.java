package com.tim.one.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.Project;
import com.tim.one.model.ProjectProvider;

public interface ProjectProviderRepository extends CrudRepository<ProjectProvider, Integer> {
  List<ProjectProvider> findByProject(@Param("project") Project project);
  ProjectProvider findByProjectAndProviderId(@Param("project") Project project, @Param("providerId") Integer providerId);
}
