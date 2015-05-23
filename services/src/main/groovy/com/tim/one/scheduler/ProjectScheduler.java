package com.tim.one.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tim.one.exception.NoFinancialDataException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NotTramaAccountFoundException;
import com.tim.one.exception.OperationStatusException;
import com.tim.one.exception.ProjectNotExistException;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.service.ProjectService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.DateUtil;

@Service
public class ProjectScheduler {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;
	
	private Log log = LogFactory.getLog(getClass());
	
	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void cronBreakevenValidation() {
	  log.info("WAKING breakeven timeout vakidator at:" + new Date());
		Integer rechazadoStatus = Integer.parseInt(properties.getProperty(ApplicationState.RECHAZADO_STATUS));
		Integer noBreakevenReachedReason = Integer.parseInt(properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_REASON));
		String noBreakevenReachedMessage = properties.getProperty(ApplicationState.NO_BREAKEVEN_REACHED_MESSAGE);
		
	    List<Project> projects = projectService.getProjectsByStatuses("0,1,2,3,5,10");
	    for (Project project : projects) {
	    	ProjectFinancialData projectFinancialData = projectFinancialDataRepository.findOne(project.getId());
			if (projectFinancialData!= null && projectFinancialData.getFundEndDate()!= null && dateUtil.createDateAsLong() >= projectFinancialData.getFundEndDate()){
				try {
					projectService.changeStatus(projectFinancialData.getId(), rechazadoStatus, null, noBreakevenReachedMessage, noBreakevenReachedReason);
				} catch (OperationStatusException cee) {
					log.warn(cee, cee);
				} catch (ProjectNotExistException pne) {
					log.warn(pne, pne);
				} catch (NonSufficientFundsException nse) {
					log.warn(nse, nse);
				} catch (NotTramaAccountFoundException nte) {
					log.warn(nte, nte);
				} catch (NoFinancialDataException nfde) {
					log.warn(nfde, nfde);
				}
			}
		}
	}

}
