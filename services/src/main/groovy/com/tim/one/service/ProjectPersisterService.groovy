package com.tim.one.service;

import com.tim.one.model.Project

interface ProjectPersisterService {

	public void save(Project project, List<String> videoLinks, List<String> soundcloudLinks, String photos, String tags, Integer userId)
	
}
