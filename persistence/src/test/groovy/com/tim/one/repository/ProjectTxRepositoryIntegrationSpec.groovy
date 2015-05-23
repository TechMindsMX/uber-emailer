package com.tim.one.repository;

import static org.junit.Assert.assertFalse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.tim.one.model.ProjectTx

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class ProjectTxRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectTxRepository repository

  def "should find a project tx by project"() {
    given: "A project tx"
      ProjectTx projectTx = new ProjectTx()
    and: "Assigning a project to project tx"
      Integer projectId = 1
      projectTx.projectId = projectId
    and: "Saving a project tx"
      repository.save(projectTx)
    when: "We find a project tx by projectId"
      def results = repository.getByProjectId(projectId)
    then: "We expect to find it"
      results.size == 1
    cleanup:"We delete user id"
      repository.delete(projectTx)
  }
  
  def "should find a project tx by project order by timestamp"() {
    given: "A project tx"
      ProjectTx projectTx = new ProjectTx()
      ProjectTx projectTx1 = new ProjectTx()
    and: "Assigning a project to project tx"
      Integer projectId = 1
      projectTx.projectId = projectId
      projectTx1.projectId = projectId
    and: "Assigning timestamp to project tx"
      projectTx.timestamp = 2L
      projectTx1.timestamp = 1L
    and: "Saving a projects"
      repository.save(projectTx)
      repository.save(projectTx1)
    when: "We find an user tx by id"
      def results = repository.getByProjectId(projectId)
    then: "We expect to find it"
      results.get(0).timestamp == 1L
      results.get(1).timestamp == 2L
    cleanup:"We delete user id"
      repository.delete(projectTx)
      repository.delete(projectTx1)
  }
  
}
