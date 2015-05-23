package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author josdem
 * @understands A class who mapped project's rate database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_RATE")
public class ProjectRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer score;
	
	@OneToOne
  @JoinColumn(name = "projectId")
	@JsonIgnore
	private Project project;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Project getProject() {
    return project;
  }
	
	public void setProject(Project project) {
    this.project = project;
  }
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
