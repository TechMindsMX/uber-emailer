package com.tim.one.validator;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.tim.one.model.ProjectProvider;

public class TestProjectValidator {

	private ProjectValidator projectValidator = new ProjectValidator();
	
	private ProjectProvider projectProvider = new ProjectProvider();

	private Integer providerId = 1;

	private Set<ProjectProvider> projectProviders = new HashSet<ProjectProvider>();

	@Test
	public void shouldKnowValidProviders() throws Exception {
		projectProvider.setProviderId(providerId);
		projectProviders.add(projectProvider);
		assertTrue(projectValidator.valid(projectProviders));
	}
	
	@Test
	public void shouldKnowIsNotValidProviders() throws Exception {
		projectProviders.add(projectProvider);
	}

}
