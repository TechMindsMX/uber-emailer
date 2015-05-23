package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectLog
import com.tim.one.model.ProjectProvider
import com.tim.one.model.ProjectTag
import com.tim.one.model.ProjectType
import com.tim.one.model.User

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class ProjectRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectRepository repository
  @Autowired
  UserRepository userRepository

  def "should find projects by type"() {
    given: "A Project"
      Project project = new Project()
    and: "Giving a type"
      ProjectType type = ProjectType.PRODUCT
      project.setType(type);
    and: "Saving a project"
      repository.save(project)
    when: "We find a product w/type and subcategory"
      Integer id = project.getId()
      List<Project> projects = repository.findProjectsByType(type)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects by user"() {
    given: "A Project"
      Project project = new Project()
      User user = new User();
    and: "Giving an user"
      project.setUser(user)
    and: "Saving a project"
      userRepository.save(user)
      repository.save(project)
    when: "We find a product w/user"
      List<Project> projects = repository.findProjectsByUser(user)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects by subcategory"() {
    given: "A Project"
      Project project = new Project()
    and: "Giving a subcategory"
      Integer subcategory = 1
      project.setSubcategory(subcategory)
    and: "Saving a project"
      repository.save(project)
    when: "We find a product w/type and subcategory"
      Integer id = project.getId()
      List<Project> projects = repository.findProjectsBySubcategory(subcategory)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find project by status"() {
    given: "A project"
      Project project = new Project()
    and: "Assigning status"
      def status = 0
      project.setStatus(status)
    and: "Saving projects"
      repository.save(project)
    when: "We find a project w/type and subcategory"
      List<Project> projects = repository.findProjectsByStatus(status)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects by type and subcategoryId"() {
    given: "A Project with a subcategory"
      Project project = new Project()
      Integer subcategoryId = 0
      ProjectType type = ProjectType.PRODUCT;
    and: "Assigning type and subcategory to project"
      project.setType(type)
      project.setSubcategory(subcategoryId)
    and: "Saving a project"
      repository.save(project)
    when: "We find a project w/type and subcategory"
      List<Project> projects = repository.findProjectsByTypeAndSubcategory(type, subcategoryId)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects closest to end funding"() {
    given: "Two Projects and two project financial data"
      Project project1 = new Project()
      ProjectFinancialData projectFinancialData1 = new ProjectFinancialData()
      Project project2 = new Project()
      ProjectFinancialData projectFinancialData2 = new ProjectFinancialData()
    and: "Setting status"
      project1.status = 5  
      project2.status = 5
    and: "Saving a projects and financial datas"
      projectFinancialData1.fundEndDate = 1L
      projectFinancialData2.fundEndDate = 2L
      project1.projectFinancialData = projectFinancialData1
      project2.projectFinancialData = projectFinancialData2
      repository.save(project1)
      repository.save(project2)
    when: "We find a product w/type and subcategory"
      def projects = repository.findProjectsByClosestToEndFunding(3L)
    then: "We expect to find it"
      projects.size() == 2
      projects[0] == project2
      projects[1] == project1
    cleanup:"We delete project"
      repository.delete(project1)
      repository.delete(project2)
  }
  
  def "should find projects by higher balance"() {
    given: "A Project and financial data"
      Project project = new Project()
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "Setting status"
      project.status = 7
    and: "Saving a project and financial data"
      project.projectFinancialData = projectFinancialData
      repository.save(project)
    when: "We find a product w/type and subcategory"
      List<Project> projects = repository.findProjectsByHigherBalance()
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects closest to end presentation"() {
    given: "A Project and financial data"
      Project project = new Project()
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "Setting status"
      project.setStatus(6)
    and: "Saving a project and financial data"
      project.projectFinancialData = projectFinancialData;
      repository.save(project)
    when: "We find a product w/type and subcategory"
      List<Project> projects = repository.findProjectsByClosestToEndPresentation(1L)
    then: "We expect to find it"
      projects.size() == 1
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find projects by statuses"() {
    given: "Two Projects and subcategory and type"
      Project project = new Project()
      Project project1 = new Project()
    and: "Assigning statuses"
      project.setStatus(0)
      project1.setStatus(1)
      List<Integer> statuses = new ArrayList<Integer>()
      statuses.add(0)
      statuses.add(1)
    and: "Saving projects"
      repository.save(project)
      repository.save(project1)
    when: "We find a project w/type and subcategory"
      List<Project> projects = repository.findByStatusIn(statuses)
    then: "We expect to find it"
      projects.size() == 2
    cleanup:"We delete project"
      repository.delete(project)
      repository.delete(project1)
  }
  
  def "should find projects by type and subcategory and statuses"() {
    given: "Two Projects and subcategory and type"
      Project project = new Project()
      Project project1 = new Project()
      Integer subcategoryId = 0
      ProjectType type = ProjectType.PRODUCT;
    and: "Assigning type and subcategory to project"
      project.setType(type)
      project.setSubcategory(subcategoryId)
      project1.setType(type)
      project1.setSubcategory(subcategoryId)
    and: "Assigning statuses"
      project.setStatus(0)
      project1.setStatus(1)
      List<Integer> statuses = new ArrayList<Integer>()
      statuses.add(0)
      statuses.add(1)
    and: "Saving projects"
      repository.save(project)
      repository.save(project1)
    when: "We find a project w/type and subcategory"
      List<Project> projects = repository.findProjectsByTypeAndSubcategoryAndStatuses(type, subcategoryId, statuses)
    then: "We expect to find it"
      projects.size() == 2
    cleanup:"We delete project"
      repository.delete(project)
      repository.delete(project1)
  }
  
  def "should find projects by subcategories"() {
    given: "Two projects"
      Project project = new Project()
      Project project1 = new Project()
      Integer subcategoryId = 0
      Integer otherSubcategoryId = 1
    and: "Setting categories"
      project.setSubcategory(subcategoryId);
      project1.setSubcategory(otherSubcategoryId);
    and: "List of subcategories"
      List<Integer> subcategoryIds = new ArrayList<Integer>();
      subcategoryIds.add(subcategoryId);
      subcategoryIds.add(otherSubcategoryId);
    and: "Saving products"
      repository.save(project)
      repository.save(project1)
    when: "We find products w/subcategories list"
      List<Project> projects = repository.findBySubcategoryIn(subcategoryIds)
    then: "We expect to find it"
      projects.size() == 2
    cleanup:"We delete project"
      repository.delete(project)
      repository.delete(project1)
  }
  
  def "should find projects by subcategories and statuses"() {
    given: "Two projects"
      Project project = new Project()
      Project project1 = new Project()
      Integer subcategoryId = 0
      Integer otherSubcategoryId = 1
    and: "Setting categories and statuses"
      project.setSubcategory(subcategoryId)
      project1.setSubcategory(otherSubcategoryId)
      project.setStatus(0)
      project1.setStatus(1)
    and: "List of subcategories and statuses"
      List<Integer> subcategoryIds = new ArrayList<Integer>();
      subcategoryIds.add(subcategoryId);
      subcategoryIds.add(otherSubcategoryId);
    and: "Setting statuses"
      List<Integer> statuses = new ArrayList<Integer>()
      statuses.add(0)
      statuses.add(1)
    and: "Saving products"
      repository.save(project)
      repository.save(project1)
    when: "We find products w/subcategories list"
      List<Project> projects = repository.findProjectsBySubcategoriesAndStatuses(subcategoryIds, statuses)
    then: "We expect to find it"
      projects.size() == 2
    cleanup:"We delete project"
      repository.delete(project)
      repository.delete(project1)
  }
  
  def "should find project by name using like"() {
    given: "A Project with name"
      Project project = new Project()
      project.setName("josdem")
    and: "Saving project"
      repository.save(project)
    when: "We find a project w/name"
      List<Project> projects = repository.findProjectsLikeByNameOrDescriptionOrShowground("sde")
    then: "We expect to find it"
      projects.size() == 1
    cleanup: "We delete project"
      repository.delete(project)
  }
  
  def "should find project by tag using like"() {
    given: "A Project and ProjectTag"
      Project project = new Project()
      ProjectTag projectTag = new ProjectTag()
      projectTag.setTag("keyword");
      projectTag.project = project
    and: "Assigning projectId to ProjectTag"
      def tags = [projectTag] as Set
      project.setTags(tags)
    and: "Saving project"
      repository.save(project)
    when: "We find a projectTag"
      List<Project> projects = repository.findProjectsByTag("key")
      def tagsFromProject = projects.get(0).getTags()
    then: "We expect to find it"
      projects.size() == 1
      tagsFromProject.size() == 1
      tagsFromProject[0].tag == "keyword" 
    cleanup: "We delete project"
      repository.delete(project)
  }
  
  def "should find projects w/logs"() {
    given: "A Project"
      Project project = new Project()
    and: "A project log"
      ProjectLog projectLog = new ProjectLog()
    and: "Assigning logs"
      def logs = [projectLog] as Set
      project.setLogs(logs) 
    and: "Saving a project"
      repository.save(project)
    when: "We find a product w/type and subcategory"
      Integer id = project.getId()
      Project result = repository.findOne(id)
    then: "We expect to find it"
      result.getId() == id
      result.logs.size() == 1
      result.logs[0] == projectLog
    cleanup:"We delete project"
      repository.delete(project)
  }
  
	//FixMe: Provider should order by advanceDate not id
  def "should find projects w/providers order by advance date"() {
    given: "A Project"
      Project project = new Project()
    and: "Two project providers"
      ProjectProvider provider1 = new ProjectProvider()
      ProjectProvider provider2 = new ProjectProvider()
    and: "Assigning providers"
      def providers = [provider1, provider2] as Set;
      project.setProviders(providers)
    and: "Saving a project"
      repository.save(project)
    when: "We find a product w/messages"
      Integer id = project.getId()
      Project result = repository.findOne(id)
    then: "We expect to find it"
      result.getId() == id
      result.providers.size() == 2
    cleanup:"We delete project"
      repository.delete(project)
  }
  
  def "should find project w/financialData"() {
    given: "A Project"
      Project project = new Project()
    and: "A project financial data"
      ProjectFinancialData projectFinancialData = new ProjectFinancialData()
    and: "Assigning messages"
      project.projectFinancialData = projectFinancialData;
    and: "Saving a project"
      repository.save(project)
    when: "We find a product w/messages"
      Integer id = project.getId()
      Project result = repository.findOne(id)
    then: "We expect to find it"
      result.getId() == id
      result.projectFinancialData == projectFinancialData
    cleanup:"We delete project"
      repository.delete(project)
  }
  
}

