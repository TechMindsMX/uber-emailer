package com.tim.one.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.ProjectTx;

public interface ProjectTxRepository extends CrudRepository<ProjectTx, Integer> {
  @Query("SELECT p FROM ProjectTx p WHERE p.projectId=:projectId order by p.timestamp")
  List<ProjectTx> getByProjectId(@Param("projectId") Integer projectId);
}
