package com.tim.one.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.model.ProjectCategory;
import com.tim.one.service.CategoryService;
import com.tim.one.command.CategoryCommand;

public class TestCategoryController {

  @InjectMocks
  private CategoryController categoryController = new CategoryController();

  @Mock
  private CategoryService categoryService;
  @Mock
  private List<ProjectCategory> categories;
  @Mock
  private List<ProjectCategory> subCategories;

  private Integer categoryId = 1;

  private CategoryCommand categoryCommand;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    categoryCommand = new CategoryCommand();
    categoryCommand.setCategoryId(categoryId);
  }

  @Test
  public void shouldListCategories() throws Exception {
    when(categoryService.getCategories()).thenReturn(categories);

    List<ProjectCategory> result = categoryController.listCategories();

    assertEquals(categories, result);
  }

  @Test
  public void shouldListSubCategories() throws Exception {
    when(categoryService.getSubCategories(categoryId)).thenReturn(subCategories);

    List<ProjectCategory> result = categoryController.listSubCategories(categoryCommand);

    assertEquals(subCategories, result);
  }

  @Test
  public void shouldListAllSubcategories() throws Exception {
    when(categoryService.getSubCategories()).thenReturn(subCategories);

    List<ProjectCategory> result = categoryController.listSubCategories();

    assertEquals(subCategories, result);
  }

}
