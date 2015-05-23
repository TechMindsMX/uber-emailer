package com.tim.one.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification

import com.tim.one.model.Project
import com.tim.one.model.User
import com.tim.one.repository.ProjectRepository
import com.tim.one.repository.UserRepository

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
public class ProjectPersisterServiceIntegrationSpec extends Specification {

	@Autowired
	ProjectPersisterService projectPersisterService
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;

	def "Should persist a project with its collections"() {
		given: "A project"
		def project = new Project()
		and: "A user"
		def user = new User()
		userRepository.save(user)
		and: "Project's collections"
		def videoLinks = ["VideoUrl"]
		def soundcloudLinks = ["soundcloudLink"]
		when: "We save project"
		projectPersisterService.save(project, videoLinks, soundcloudLinks, "photo", "tag", user.id)
		then:"We expect project with collections"
		Project result = projectRepository.findOne(project.id)
		result.projectVideos[0].url == "VideoUrl"
		result.projectSoundclouds[0].url == "soundcloudLink"
		result.projectPhotos[0].url == "photo"
		result.tags[0].tag == "tag"
		result.user.id == user.id
		cleanup:"We delete project's data"
		projectRepository.delete(project)
	}
	
}

