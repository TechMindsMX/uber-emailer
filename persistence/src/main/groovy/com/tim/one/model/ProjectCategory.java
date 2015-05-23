package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author josdem
 * @understands A class who mapped project's categories database values to the entity model
 * @catalog
 */

@Entity
@Table(name="PROJECT_CATEGORY")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ProjectCategory {
	
	@Id
	private Integer id;
	private Integer father;
	private String name;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFather() {
		return father;
	}
	
	public void setFather(Integer father) {
		this.father = father;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
