package com.tim.one.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tim.one.bean.RatingBean;
import com.tim.one.bean.SectionBean;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectBusinessCase;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectLog;
import com.tim.one.model.ProjectPhoto;
import com.tim.one.model.ProjectRate;
import com.tim.one.model.ProjectSoundcloud;
import com.tim.one.model.ProjectTag;
import com.tim.one.model.ProjectVariableCost;
import com.tim.one.model.ProjectVideo;

@Component
public class ProjectHelper {
	
	public Project createProject() {
		return new Project();
	}

	public ProjectPhoto createProjectPhoto() {
		return new ProjectPhoto();
	}

	public ProjectBusinessCase createProjectBusinessCase() {
		return new ProjectBusinessCase();
	}

	public ProjectVideo createProjectYoutube() {
		return new ProjectVideo();
	}

	public ProjectSoundcloud createProjectSoundcloud() {
		return new ProjectSoundcloud();
	}

	public ProjectLog createProjectLog() {
		return new ProjectLog();
	}

	public ProjectTag createProjectTag() {
		return new ProjectTag();
	}

	public ProjectRate createProjectRate() {
		return new ProjectRate();
	}

	public RatingBean createRatingBean() {
		return new RatingBean();
	}

	public String toJson(RatingBean bean) {
		return new Gson().toJson(bean);
	}

	public ProjectVariableCost createProjectVariableCost() {
		return new ProjectVariableCost();
	}

	public List<String> createArrayListString() {
		return new ArrayList<String>();
	}

	public List<Integer> createArrayListInteger() {
		return new ArrayList<Integer>();
	}

	public Set<ProjectPhoto> createProjectPhotos() {
		return new HashSet<ProjectPhoto>();
	}

	public SectionBean createSectionBean() {
		return new SectionBean();
	}

  public ProjectFinancialData createProjectFinancialData() {
    return new ProjectFinancialData();
  }

}
