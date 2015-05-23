package com.tim.one.repository;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spock.lang.Specification;
import spock.lang.Unroll;

import com.tim.one.model.ProjectCategory;

@ContextConfiguration(locations=["classpath:/persistence-appctx.xml"])
public class ProjectCategoryRepositoryIntegrationSpec extends Specification {

  @Autowired
  ProjectCategoryRepository repository

  def "should get categories and subcategories"() {
    when: "We find categories"
      List<ProjectCategory> categories = repository.findCategories()
      List<ProjectCategory> subcategories = repository.findSubCategories(0)
      List<ProjectCategory> allSubCategories = repository.findAllSubCategories();
    then: "We find them"
      !categories.isEmpty()
      !subcategories.isEmpty()
      !allSubCategories.isEmpty()
  }
  
}
