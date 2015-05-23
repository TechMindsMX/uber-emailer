package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.command.CategoryCommand;
import com.tim.one.model.ProjectCategory;
import com.tim.one.service.CategoryService;

/**
 * @author josdem
 * @understands A class who knows how manage entity project model categories
 *
 */

@Controller
@RequestMapping("/category/*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "categories")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ProjectCategory> listCategories() {
		log.info("LISTING Project's categories");
		return categoryService.getCategories();
	}

	@RequestMapping(method = GET, value = "/subcategories/{categoryId}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ProjectCategory> listSubCategories(CategoryCommand command) {
		log.info("LISTING Project's subcategories:  " + command.getCategoryId());
		return categoryService.getSubCategories(command.getCategoryId());
	}

	@RequestMapping(method = GET, value = "/subcategories/all")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ProjectCategory> listSubCategories() {
		log.info("LISTING Project's all subcategories");
		return categoryService.getSubCategories();
	}

}
