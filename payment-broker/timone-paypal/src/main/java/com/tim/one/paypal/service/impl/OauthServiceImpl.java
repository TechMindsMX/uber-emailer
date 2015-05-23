package com.tim.one.paypal.service.impl;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.paypal.core.ConfigManager;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.tim.one.paypal.service.OauthService;

/**
 * @author josdem
 * @understands A class who knows how authenticate an user with paypal 
 */

@Service
public class OauthServiceImpl implements OauthService {
  
  @Autowired
  @Qualifier("paypalProperties")
  private Properties paypalProperties;
	
	private Log log = LogFactory.getLog(getClass());

	public void init() {
		PayPalResource.initConfig(paypalProperties);
	}

	public String getPaypalToken() throws PayPalRESTException {
		Map<String, String> configurationMap = ConfigManager.getInstance().getConfigurationMap();
		String accessToken = new OAuthTokenCredential(configurationMap.get("clientID"), configurationMap.get("clientSecret")).getAccessToken();
		log.info("accessToken: " + accessToken);
		return accessToken;
	}

}
