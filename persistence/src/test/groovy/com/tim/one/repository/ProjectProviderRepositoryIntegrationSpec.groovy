package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.Project
import com.tim.one.model.ProjectProvider
import com.tim.one.model.User

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
@Transactional
public class ProjectProviderRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectRepository projectRepository
  @Autowired
  UserRepository userRepository
  @Autowired
  ProjectProviderRepository projectProviderRepository
	
	def "should get an empty collection when no providers"() {
		given: "A project"
			Project project = new Project()
		and: "Saving project"
			projectRepository.save(project)
		when: "We find a project provider by project"
			List<ProjectProvider> projects = projectProviderRepository.findByProject(project)
		then: "We expect to find it"
			projects != null
			projects.isEmpty()
		cleanup:"We delete project rate"
			projectRepository.delete(project)
	}

  def "should find project provider by project and ordering by id"() {
    given: "Two Project providers"
      ProjectProvider projectProvider = new ProjectProvider()
      ProjectProvider projectProvider1 = new ProjectProvider()
    and: "A project"
      Project project = new Project()
    and: "Assigning project to projectProviders"
      project.providers = [projectProvider, projectProvider1] as Set
      projectProvider.project = project
      projectProvider1.project = project
    and: "Saving a project provider"
      projectRepository.save(project)
    when: "We find a project provider by project"
      List<ProjectProvider> projects = projectProviderRepository.findByProject(project)
    then: "We expect to find it"
      projects.size() == 2
    cleanup:"We delete project rate"
      projectRepository.delete(project)
  }
  
  def "should find project provider by project and provider"() {
    given: "A Project provider"
      ProjectProvider projectProvider = new ProjectProvider()
    and: "Assignig a provider"
			def providerId = 1
			projectProvider.providerId = providerId
    and: "A project"
      Project project = new Project()
    and: "Assigning project to projectProvider"
      project.providers = [projectProvider] as Set
			projectProvider.project = project
    and: "Saving a project provider"
       projectRepository.save(project)
    when: "We find a project provider by project"
      println "providerId: " + projectProvider.id
      ProjectProvider result = projectProviderRepository.findByProjectAndProviderId(project, providerId)
      println "result: " + result
    then: "We expect to find it"
      result.id == projectProvider.id
    cleanup:"We delete project rate"
      projectRepository.delete(project)
  }
  
}
