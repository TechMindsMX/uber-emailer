package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tim.one.helper.STPConsumerHelper;
import com.tim.one.model.StpStatus;
import com.tim.one.stp.exception.InvalidCentroDeCostosException;
import com.tim.one.stp.state.ApplicationState;
import com.tim.one.util.DateUtil;

@Component
public class STPConsumerCollaborator {
  
  @Autowired
  private DateUtil dateUtil;
  @Autowired
  private STPConsumerHelper stpHelper;
  @Autowired
  private Properties stpProperties;

  public StpStatus createOrdenStatus(Integer id, Integer userId, String uuid, BigDecimal amount) {
    StpStatus stpStatus = stpHelper.createSTPStatus();
    
    stpStatus.setId(id);
    stpStatus.setEstado(Integer.parseInt(stpProperties.getProperty(ApplicationState.STP_PENDIENTE)));
    stpStatus.setCreatedTimestamp(dateUtil.createDateAsLong());
    stpStatus.setUserId(userId);
    stpStatus.setAmount(amount);
    
    return stpStatus;
  }

	public String getCentroDeCostos(String clabeBeneficiario) {
		if (clabeBeneficiario.startsWith(stpProperties.getProperty(ApplicationState.STP_FREAKFUND_PREFIX))){
			return ApplicationState.STP_FREAKFUND_PREFIX;
		} 
		
		if (clabeBeneficiario.startsWith(stpProperties.getProperty(ApplicationState.STP_INTEGRA_PREFIX))){
			return ApplicationState.STP_INTEGRA_PREFIX;
		} 
		
		throw new InvalidCentroDeCostosException(clabeBeneficiario);
	}
  
}
