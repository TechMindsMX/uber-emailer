package com.tim.one.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.ForgotPasswordBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/mail-context.xml", "classpath:/services-test-appctx.xml"})
public class TestIntegrationMailService {

  @Autowired
  private NotificationService notificationService;

  private String email = "test@trama.mx";
  private String token = "token";

  @Test
  public void shouldSendAbonoCuentaMessage() throws Exception {
    ForgotPasswordBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.FORGOT_PASSWORD);

    notificationService.sendNotification(bean);
  }
  
  private ForgotPasswordBean setAbonoCuentaBeanExpectations() {
  	ForgotPasswordBean bean = new ForgotPasswordBean();
    bean.setEmail(email);
    bean.setToken(token);
    return bean;
  }
}
