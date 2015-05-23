package com.tim.one.wrapper;

import java.util.List;

import com.tim.one.bean.ProjectProviderBean;

public class ProviderWrapper {
	
private List<ProjectProviderBean> projectProviders;
	
	public List<ProjectProviderBean> getProjectProviders() {
		return projectProviders;
	}
	
	public void setProjectProviders(List<ProjectProviderBean> projectProviders) {
		this.projectProviders = projectProviders;
	}

}
