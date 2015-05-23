package com.tim.one.service

import com.tim.one.bean.PaymentType
import com.tim.one.bean.ResultType
import com.tim.one.bean.SectionBean
import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectProvider
import com.tim.one.model.ProjectType

interface ProjectService {

  Integer saveProject(Project project, Integer userId)
  Integer saveProject(Project project)
  Integer saveProject(ProjectFinancialData project)
  Iterable<Project> getAllProjects()
  Project getProjectById(Integer projectId)
  List<Project> getProjectsByTypeSubcategoryId(ProjectType type, Integer subcategoryId, String statuses)
  List<Project> getProjectsByCategoryId(Integer categoryId, String statuses)
  List<Project> getProjectsByType(ProjectType type)
  List<Project> getProjectsBySubcategoryId(Integer subcategoryId)
  List<Project> getProjectsByCategoryId(Integer categoryId)
  ResultType changeStatus(Integer projectId, Integer status, Integer userId, String comment, Integer reason)
  Set<Project> getByTag(String tag)
  ResultType createRate(Integer projectId, Integer userId, Integer score)
  Float getRating(Integer projectId)
  List<Project> getProjectsByUserId(Integer userId)
  Boolean createProjectProvider(Integer projectId, Set<ProjectProvider> projectProviders)
  void createVariableCosts(Map<String, String> params)
  Set<Project> getProjectsByClosestToEndFunding()
  List<Project> getProjectsByClosestToEndPresentation()
  ResultType createProviderPayment(Integer projectId, Integer providerId, PaymentType paymentType)
  List<Project> getProjectsByStatuses(String statuses)
  Set<Project> getProjectsByHighBalance()
  ResultType createProducerPayment(Integer projectId, Integer producerId, BigDecimal amount)
  BigDecimal getTRA(BigDecimal tri, BigDecimal trf, ProjectFinancialData project)
  Integer createConsumingUnits(Map<String, String> params)
  Set<Project> getProjectsByStatusAndUserId(List<Integer> statuses, Integer userId)
  Set<Project> getByKeyword(String keyword)
  ResultType changeProjectUnitSale(Integer projectUnitSaleId)
  List<SectionBean> getSections(Integer projectId)
  void measureBreakevenAndBudget(Integer projectId, Set<ProjectProvider> providers)
  ResultType createBoxOfficeSales(Map<String, String> params)

}
