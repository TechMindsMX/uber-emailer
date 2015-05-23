package com.makingdevs.integration.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.makingdevs.integration.MailService;
import com.makingdevs.vo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/mail-context.xml" })
@Ignore
public class TestMailService {

  @Autowired
  private MailService mailService;

  private String destEmail = "test@trama.mx";
  private String imagePath = "src/test/resources/imagen.jpg";
  private String message = "Notificacion de informe";
  private String attachmentSubject = "Mensaje con documento adjunto...";

  @Test
  public void testMail2() {
    mailService.sendMimeMail(destEmail, "Mensaje MIME desde la aplicacion...", message);
  }

  @Test
  public void testMail3() throws MessagingException {
    FileSystemResource attach = new FileSystemResource(imagePath);
    mailService.sendMailWithAttach(destEmail, attachmentSubject, message, attach);
  }

  @Test
  public void testMail4() throws MessagingException {
    FileSystemResource attach = new FileSystemResource(imagePath);
    mailService.sendMailWithInline(destEmail, attachmentSubject, message, attach);
  }

  @Test
  public void testMail5() throws MessagingException {
    User user = new User();
    user.setUserName("Jose Juan");
    user.setEmail(destEmail);
    Map model = new HashMap();
    model.put("user", user);
    mailService.sendMailWithEngine(destEmail, model, "Mail con Template", "template.ftl");
  }
}
