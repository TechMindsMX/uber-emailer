package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.Project
import com.tim.one.model.ProjectFinancialData
import com.tim.one.model.ProjectLog
import com.tim.one.model.ProjectPhoto
import com.tim.one.model.ProjectProvider
import com.tim.one.model.ProjectSoundcloud
import com.tim.one.model.ProjectTag
import com.tim.one.model.ProjectUnitSale
import com.tim.one.model.ProjectVariableCost
import com.tim.one.model.ProjectVideo
import com.tim.one.repository.ProjectRepository

@ContextConfiguration(locations=[
	"classpath:/services-appctx.xml",
	"classpath:/persistence-appctx.xml",
	"classpath:/jms-appctx.xml",
	"classpath:/mail-context.xml",
	"classpath:/transaction-appctx.xml",
	"classpath:/formatter-appctx.xml",
	"classpath:/stp-appctx.xml",
	"classpath:/aop-appctx.xml",
	"classpath:/properties-appctx.xml"])

@Transactional
public class ProjectServiceIntegrationSpec extends Specification {

	@Autowired
	ProjectService projectService
	@Autowired
	ProjectRepository projectRepository;

	def "When we have project variable costs we expect to have costs"() {
		given: "A project"
		Project project = new Project()
		and: "Costs in a map"
		def params = ["rent":"5", "isep":"5", "callback":"callback"]
		and: "We save project"
		projectRepository.save(project)
		params.put("projectId", project.getId().toString());
		when: "We create variable costs to user"
		projectService.createVariableCosts(params)
		then:"We expect project financial data"
		Project result = projectRepository.findOne(project.id)
		result.projectFinancialData != null
		result.projectFinancialData.variableCosts[0].value == 5
		result.projectFinancialData.variableCosts[1].value == 5
		cleanup:"We delete project's data"
		projectRepository.delete(project)
	}

	def "should get a project and financial data whith collections"() {
		given: "A project"
		def project = new Project()
		and: "Project's collections"
		def projectVideos = [new ProjectVideo()] as Set
		def projectSoundclouds = [new ProjectSoundcloud()] as Set
		def projectPhotos = [new ProjectPhoto()] as Set
		def projectTags = [new ProjectTag()] as Set
		def projectLogs = [new ProjectLog()] as Set
		def projectProviders = [new ProjectProvider()] as Set
		project.projectVideos = projectVideos
		project.projectSoundclouds = projectSoundclouds
		project.projectPhotos = projectPhotos
		project.tags = projectTags
		project.logs = projectLogs
		project.providers = projectProviders
		and: "Project Financial Data"
		def projectFinancialData = new ProjectFinancialData()
		project.projectFinancialData = projectFinancialData
		and: "ProjectFinancialData's collections"
		def projectVariableCosts = [new ProjectVariableCost()] as Set
		def projectUnitSales = [new ProjectUnitSale()] as Set
		projectFinancialData.variableCosts = projectVariableCosts
		projectFinancialData.projectUnitSales = projectUnitSales
		when: "We save project"
		projectRepository.save(project)
		then:"We expect project not null"
		Project result = projectRepository.findOne(project.id)
		result.projectFinancialData == projectFinancialData
		result.projectVideos == projectVideos
		result.projectSoundclouds == projectSoundclouds
		result.projectPhotos == projectPhotos
		result.tags == projectTags
		result.logs == projectLogs
		result.providers == projectProviders
		result.projectFinancialData.variableCosts == projectVariableCosts
		result.projectFinancialData.projectUnitSales == projectUnitSales
		cleanup:"We delete project's data"
		projectRepository.delete(project)
	}
	
	def "Should create a project with providers"() {
		given: "A project"
		Project project = new Project()
		and: "UserIds"
		def userId = 1
		and: "Providers"
		def provider = new ProjectProvider()
		provider.providerId = userId
		def providers = [provider] as Set
		and: "We save project"
		projectRepository.save(project)
		when: "We create variable costs to user"
		def operationResult = projectService.createProjectProvider(project.id, providers)
		then:"We expect project financial data"
		operationResult
		Project result = projectRepository.findOne(project.id)
		result.providers.size() == 1
		cleanup:"We delete project's data"
		projectRepository.delete(project)
	}
	
	def "Should replace an existing provider in a project"() {
		given: "A project"
		Project project = new Project()
		and: "UserIds"
		def userId1 = 1
		and: "Providers"
		def provider1 = new ProjectProvider()
		provider1.providerId = userId1
		def providers = [provider1] as Set
		project.providers = providers
		and: "We save project"
		projectRepository.save(project)
		when: "We create another provider"
		def userId2 = 2
		and: "Providers"
		def provider2 = new ProjectProvider()
		provider2.providerId = userId2
		def newProviders = [provider2] as Set
		def operationResult = projectService.createProjectProvider(project.id, newProviders)
		then:"We expect project financial data"
		operationResult
		Project result = projectRepository.findOne(project.id)
		result.providers.size() == 1
		result.providers[0].providerId == userId2
		cleanup:"We delete project's data"
		projectRepository.delete(project)
	}
}

