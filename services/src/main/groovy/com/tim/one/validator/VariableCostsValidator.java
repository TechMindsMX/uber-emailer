package com.tim.one.validator;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.state.ApplicationState;
import com.tim.one.util.NumericUtil;

/**
 * @author josdem
 * @understands A class who knows how to validate costs
 * 
 */

@Service
public class VariableCostsValidator {
	
	@Autowired
	private NumericUtil numericUtil;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	
	private Map<String, String> params;
	
	public Boolean isValid(Map<String, String> params) {
		this.params = params;
		return !params.isEmpty() && (isRentValid() && isIsepValid() && isSacmValid() && isSogemValid() && isTicketServiceValid()) ? true : false;
	}

	private boolean isRentValid() {
		Float rentMin = new Float(properties.getProperty(ApplicationState.RENT_MIN));
		Float rentMax = new Float(properties.getProperty(ApplicationState.RENT_MAX));
		if (params.get("rent") == null) return true;
		if (!numericUtil.isNumeric(params.get("rent"))) return false;
		return Float.parseFloat(params.get("rent")) >= rentMin &&  Float.parseFloat(params.get("rent")) <= rentMax ? true : false;
	}
	
	private boolean isIsepValid() {
		Float isepMin = new Float(properties.getProperty(ApplicationState.ISEP_MIN));
		Float isepMax = new Float(properties.getProperty(ApplicationState.ISEP_MAX));
		if (params.get("isep") == null)  return true;
		if (!numericUtil.isNumeric(params.get("isep"))) return false;
		return Float.parseFloat(params.get("isep")) >= isepMin &&  Float.parseFloat(params.get("isep")) <= isepMax ? true : false;
	}
	
	private boolean isSacmValid() {
		Float sacmMin = new Float(properties.getProperty(ApplicationState.SACM_MIN));
		Float sacmMax = new Float(properties.getProperty(ApplicationState.SACM_MAX));
		if (params.get("sacm") == null)  return true;
		if (!numericUtil.isNumeric(params.get("sacm"))) return false;
		return Float.parseFloat(params.get("sacm")) >= sacmMin &&  Float.parseFloat(params.get("sacm")) <= sacmMax ? true : false;
	}
	
	private boolean isSogemValid() {
		Float sogemMin = new Float(properties.getProperty(ApplicationState.SOGEM_MIN));
		Float sogemMax = new Float(properties.getProperty(ApplicationState.SOGEM_MAX));
		if (params.get("sogem") == null)  return true;
		if (!numericUtil.isNumeric(params.get("sogem"))) return false;
		return Float.parseFloat(params.get("sogem")) >= sogemMin &&  Float.parseFloat(params.get("sogem")) <= sogemMax ? true : false;
	}
	
	private boolean isTicketServiceValid() {
		Float ticketServiceMin = new Float(properties.getProperty(ApplicationState.TICKET_SERVICE_MIN));
		Float ticketServiceMax = new Float(properties.getProperty(ApplicationState.TICKET_SERVICE_MAX));
		if (params.get("ticketService") == null)  return true;
		if (!numericUtil.isNumeric(params.get("ticketService"))) return false;
		return Float.parseFloat(params.get("ticketService")) >= ticketServiceMin &&  Float.parseFloat(params.get("ticketService")) <= ticketServiceMax ? true : false;
	}

}
