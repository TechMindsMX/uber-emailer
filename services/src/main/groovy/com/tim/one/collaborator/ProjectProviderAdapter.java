package com.tim.one.collaborator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.bean.ProjectProviderBean;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectProvider;
import com.tim.one.model.User;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.UserRepository;

@Component
public class ProjectProviderAdapter {
  
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private UserRepository userRepository;
  
	
	public Set<ProjectProvider> adapt(List<ProjectProviderBean> projectProvidersBeans) {
		Set<ProjectProvider> projectProviders = new HashSet<ProjectProvider>();
		for (ProjectProviderBean projectProviderBean : projectProvidersBeans) {
			ProjectProvider projectProvider = new ProjectProvider();
			
			Project project = projectRepository.findOne(projectProviderBean.getProjectId());
			if(project == null){
			  throw new ProjectNotExistException(projectProviderBean.getProjectId());
			}
			
			User provider = userRepository.findOne(projectProviderBean.getProviderId());
      if(provider == null){
        throw new UserNotFoundException(projectProviderBean.getProviderId());
      }
			
			projectProvider.setProject(project);
			projectProvider.setProviderId(provider.getId());
			projectProvider.setAdvanceDate(projectProviderBean.getAdvanceDate().getTime());
			projectProvider.setAdvanceQuantity(projectProviderBean.getAdvanceQuantity());
			projectProvider.setSettlementDate(projectProviderBean.getSettlementDate().getTime());
			projectProvider.setSettlementQuantity(projectProviderBean.getSettlementQuantity());
			projectProviders.add(projectProvider);
		}
		return projectProviders;
	}

}
