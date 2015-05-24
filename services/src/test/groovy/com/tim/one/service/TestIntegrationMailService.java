package com.tim.one.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/mail-context.xml", "classpath:/services-test-appctx.xml"})
public class TestIntegrationMailService {

  @Autowired
  private NotificationService notificationService;

  private String id = "1";
  private String email = "test@trama.mx";
  private String name = "josdem";
  private String amount = "100.00";
  private String date = "date";

  @Test
  public void shouldSendAbonoCuentaMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.ABONO_CUENTA);

    notificationService.sendNotification(bean);
  }
  
  private AbonoCuentaBean setAbonoCuentaBeanExpectations() {
    AbonoCuentaBean bean = new AbonoCuentaBean();
    bean.setId(id);
    bean.setName(name);
    bean.setAmount(amount);
    bean.setDate(date);
    bean.setEmail(email);
    return bean;
  }
}
