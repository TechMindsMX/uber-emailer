package com.tim.one.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author josdem
 * @understands A class who mapped Trama project database values to the entity model
 * 
 */

@Entity
@Table(name="PROJECT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Comparable<Project> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String showground;
	private String inclosure;
	private String url;
	private String banner;
  private String avatar;

	private Integer subcategory;
	private Integer status;
	private Integer videoPublic;
	private Integer audioPublic;
	private Integer imagePublic;
	private Integer infoPublic;

	private Long timeCreated;
	private ProjectType type;
	
	@OneToOne(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private ProjectFinancialData projectFinancialData;
	
	@ManyToOne
  @JoinColumn(name = "userId")
  @JsonProperty("userId")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
  @JsonIdentityReference(alwaysAsId=true)
  private User user;
	
	@OneToOne(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private ProjectRate projectRate;
	
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	private Set<ProjectVideo> projectVideos;
	
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	private Set<ProjectSoundcloud> projectSoundclouds;
	
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true)
	private Set<ProjectPhoto> projectPhotos;

	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JsonManagedReference
	private Set<ProjectProvider> providers;
	
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Set<ProjectTag> tags;
	
	@OneToMany(mappedBy="project", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private Set<ProjectLog> logs;
	
	@Column(columnDefinition="Text")
  private String description;
  @Column(columnDefinition="Text")
  private String cast;
	
	@Transient
	private BigDecimal tri;
	@Transient
	private BigDecimal cre;
	@Transient
	private BigDecimal trf;
	@Transient
	private BigDecimal tra;
	@Transient
	private BigDecimal fundedAmount;
	@Transient
	private BigDecimal investedAmount;
	@Transient
	private Float rating;
	
	public Project() {
		this.type = ProjectType.PROJECT;
		this.tri = new BigDecimal("0.00");
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getShowground() {
		return showground;
	}
	
	public void setShowground(String showground) {
		this.showground = showground;
	}
	
	public String getInclosure() {
		return inclosure;
	}
	
	
	public void setInclosure(String inclosure) {
		this.inclosure = inclosure;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCast() {
		return cast;
	}
	
	public void setCast(String cast) {
		this.cast = cast;
	}
	
	public Long getTimeCreated() {
		return timeCreated;
	}
	
	public void setTimeCreated(Long timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	public Integer getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Integer subcategory) {
		this.subcategory = subcategory;
	}
	
	
	public Set<ProjectVideo> getProjectVideos() {
		return projectVideos;
	}
	
	
	public void setProjectVideos(Set<ProjectVideo> projectVideos) {
		this.projectVideos = projectVideos;
	}
	
	
	public Set<ProjectPhoto> getProjectPhotos() {
		return projectPhotos;
	}
	
	
	public void setProjectPhotos(Set<ProjectPhoto> projectPhotos) {
		this.projectPhotos = projectPhotos;
	}
	
	public Set<ProjectSoundcloud> getProjectSoundclouds() {
		return projectSoundclouds;
	}
	
	
	public void setProjectSoundclouds(Set<ProjectSoundcloud> projectSoundclouds) {
		this.projectSoundclouds = projectSoundclouds;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setType(ProjectType type) {
		if(type == null) return;
		this.type = type;
	}
	
	public ProjectType getType() {
		return type;
	}
	
	
	public User getUser() {
		return user;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public Set<ProjectTag> getTags() {
		return tags;
	}
	
	
	public void setTags(Set<ProjectTag> tags) {
		this.tags = tags;
	}
	
	
	public ProjectRate getProjectRate() {
    return projectRate;
  }
	
	
	public void setProjectRate(ProjectRate projectRate) {
    this.projectRate = projectRate;
  }
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getVideoPublic() {
		return videoPublic;
	}
	
	public void setVideoPublic(Integer videoPublic) {
		this.videoPublic = videoPublic;
	}
	
	public Integer getAudioPublic() {
		return audioPublic;
	}
	
	public void setAudioPublic(Integer audioPublic) {
		this.audioPublic = audioPublic;
	}

	public Integer getImagePublic() {
		return imagePublic;
	}
	
	public void setImagesPublic(Integer imagesPublic) {
		this.imagePublic = imagesPublic;
	}
	
	public Integer getInfoPublic() {
		return infoPublic;
	}
	
	public void setInfoPublic(Integer infoPublic) {
		this.infoPublic = infoPublic;
	}
	
	public Set<ProjectLog> getLogs() {
		return logs;
	}

	
	public void setLogs(Set<ProjectLog> logs) {
		this.logs = logs;
	}
	
	
	public Set<ProjectProvider> getProviders() {
		return providers;
	}
	
	
	public void setProviders(Set<ProjectProvider> providers) {
		this.providers = providers;
	}

	
	public ProjectFinancialData getProjectFinancialData() {
		return projectFinancialData;
	}
	
	public void setProjectFinancialData(ProjectFinancialData projectFinancialData) {
		this.projectFinancialData = projectFinancialData;
	}
	

	public BigDecimal getTri() {
		return tri;
	}
	
	public void setTri(BigDecimal tri) {
		this.tri = tri;
	}
	
	public BigDecimal getCre() {
		return cre;
	}
	
	public void setCre(BigDecimal cre) {
		this.cre = cre;
	}

	public BigDecimal getTrf() {
		return trf;
	}

	public void setTrf(BigDecimal trf) {
		this.trf = trf;
	}
	
	public BigDecimal getTra() {
		return tra;
	}

	public void setTra(BigDecimal tra) {
		this.tra = tra;
	}
	
	public BigDecimal getFundedAmount() {
		return fundedAmount;
	}

	public void setFundedAmount(BigDecimal fundedAmount) {
		this.fundedAmount = fundedAmount;
	}
	
	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public void setInvestedAmount(BigDecimal investedAmount) {
		this.investedAmount = investedAmount;
	}
	
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public int compareTo(Project that) {
		return that.getTri().compareTo(this.getTri());
	}

	public Float getRating() {
		return rating;
	}
	
	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
