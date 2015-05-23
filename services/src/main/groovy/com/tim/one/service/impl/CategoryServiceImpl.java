package com.tim.one.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.model.ProjectCategory;
import com.tim.one.repository.ProjectCategoryRepository;
import com.tim.one.service.CategoryService;

/**
 * @author josdem
 * @understands A class who knows how create a Categories collection based in
 *              Dao response
 * 
 */

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ProjectCategoryRepository repository;

	public List<ProjectCategory> getCategories() {
		return repository.findCategories();
	}

	public List<ProjectCategory> getSubCategories(Integer categoryId) {
		return repository.findSubCategories(categoryId);
	}

	public List<ProjectCategory> getSubCategories() {
		return repository.findAllSubCategories();
	}
	
}
