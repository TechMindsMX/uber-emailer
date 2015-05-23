package com.tim.one.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.repository.ProjectCategoryRepository;
import com.tim.one.service.impl.CategoryServiceImpl;

public class TestCategoryService {

	@InjectMocks
	private CategoryService categoryService = new CategoryServiceImpl();
	
	@Mock
  private ProjectCategoryRepository repository;

	private Integer categoryId = 1;
	
	@Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shoulListCategories() throws Exception {
		categoryService.getCategories();
		verify(repository).findCategories();
	}
	
	@Test
	public void shoulListSubCategories() throws Exception {
		categoryService.getSubCategories(categoryId);
		verify(repository).findSubCategories(categoryId);
	}
	
	@Test
	public void shoulListAllSubCategories() throws Exception {
		categoryService.getSubCategories();
		verify(repository).findAllSubCategories();
	}

}
