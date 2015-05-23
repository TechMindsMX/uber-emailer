package com.tim.one.paypal.service;

import com.paypal.core.rest.PayPalRESTException;

public interface OauthService {
  
  void init();

  public String getPaypalToken() throws PayPalRESTException;
  
}
