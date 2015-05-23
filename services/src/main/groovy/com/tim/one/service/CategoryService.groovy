package com.tim.one.service

import com.tim.one.model.ProjectCategory

interface CategoryService {

  public List<ProjectCategory> getCategories()
  public List<ProjectCategory> getSubCategories(Integer categoryId)
  public List<ProjectCategory> getSubCategories()

}
