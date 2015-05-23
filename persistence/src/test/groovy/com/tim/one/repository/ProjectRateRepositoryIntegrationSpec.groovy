package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.Project
import com.tim.one.model.ProjectRate

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class ProjectRateRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectRateRepository projectRaterepository
  @Autowired
  ProjectRepository projectRepository

  def "should find project rate by project"() {
    given: "A Project rate"
      Project project = new Project()
      ProjectRate projectRate = new ProjectRate()
      projectRate.score = 5
    and: "Assigning project to projectRate"
      project.projectRate = projectRate
      projectRate.project = project
    and: "Saving a project rate"
      projectRepository.save(project)
    when: "We find a project rate by project"
      List<ProjectRate> projects = projectRaterepository.findByProject(project)
    then: "We expect to find it"
      projects.size() == 1
      projects[0].score == projectRate.score
    cleanup:"We delete project rate"
      projectRepository.delete(project)
  }

}
