package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author josdem
 * @understands A class who mapped project's soundcloud URL database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT_SOUNDCLOUD")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ProjectSoundcloud implements Comparable<ProjectSoundcloud> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
  @JoinColumn(name = "projectId")
	@JsonIgnore
  private Project project;

	private String url;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public int compareTo(ProjectSoundcloud that) {
		return this.url.length() > that.getUrl().length() ? -1 : 1;
	}
	
}
