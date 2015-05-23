package com.tim.one.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tim.one.model.Project;
import com.tim.one.model.ProjectType;
import com.tim.one.model.User;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
  List<Project> findProjectsByTypeAndSubcategory(@Param("type") ProjectType type, @Param("subcategory") Integer subcategoryId);
  List<Project> findProjectsByType(@Param("type") ProjectType type);
  List<Project> findProjectsBySubcategory(@Param("subcategory") Integer subcategoryId);
  List<Project> findProjectsByUser(@Param("user") User user);
  List<Project> findProjectsByStatus(@Param("status") Integer status);
  
  List<Project> findBySubcategoryIn(List<Integer> subcategoryIds);
  List<Project> findByStatusIn(List<Integer> statuses);
  @Query("SELECT p FROM Project p where p.type=:type AND p.subcategory=:subcategoryId AND p.status IN :statusesAsList")
  List<Project> findProjectsByTypeAndSubcategoryAndStatuses(@Param("type") ProjectType type, @Param("subcategoryId") Integer subcategoryId, @Param("statusesAsList") List<Integer> statusesAsList);
  @Query("SELECT p FROM Project p where p.subcategory IN :subcategoryIds AND p.status IN :statusesAsList")
  List<Project> findProjectsBySubcategoriesAndStatuses(@Param("subcategoryIds") List<Integer> subcategoryIds, @Param("statusesAsList") List<Integer> statusesAsList);

  //TODO: Agregar los statuses como Enum
  @Query("SELECT pr FROM Project pr, ProjectFinancialData pf WHERE pr.status=5 order by (:now - pf.fundEndDate) desc")
  Set<Project> findProjectsByClosestToEndFunding(@Param("now") long now);
  @Query("SELECT pr FROM Project pr, ProjectFinancialData pf WHERE pr.status=6 OR pr.status=7 OR pr.status=10 AND pf.project=pr order by (:now - pf.fundEndDate) desc")
  List<Project> findProjectsByClosestToEndPresentation(@Param("now") long now);
  @Query("SELECT pr FROM Project pr, ProjectFinancialData pf WHERE pr.status=5 OR pr.status=6 OR pr.status=7 OR pr.status=10 AND pf.project=pr order by pf.balance desc")
  List<Project> findProjectsByHigherBalance();
  
  @Query("SELECT p FROM Project p where p.name like %?1% OR p.description like %?1% OR p.showground like %?1%")
  List<Project> findProjectsLikeByNameOrDescriptionOrShowground(String keyword);
  @Query("SELECT p FROM Project p JOIN p.tags t where t.tag like %?1%")
  List<Project> findProjectsByTag(String tag);
}
