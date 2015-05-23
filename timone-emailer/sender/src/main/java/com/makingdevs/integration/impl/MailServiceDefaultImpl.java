package com.makingdevs.integration.impl;

import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.makingdevs.integration.MailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("mailService")
public class MailServiceDefaultImpl implements MailService {

  @SuppressWarnings("unused")
  private static final Log log = LogFactory.getLog(MailServiceDefaultImpl.class);

  @Autowired
  private Configuration configuration;
  @Autowired
  private JavaMailSender javaMailSender;
  
  private String sender = "noreply@trama.mx";

  public void sendMimeMail(final String email, final String message, final String subject) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setFrom(new InternetAddress(sender));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);
      }
    };

    this.javaMailSender.send(preparator);

  }

  public void sendMailWithAttach(String email, String message, String subject, FileSystemResource attach)
      throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setTo(email);
    helper.setFrom(sender);
    helper.setText(message);
    helper.setSubject(subject);
    helper.addAttachment(attach.getFilename(), attach);
    javaMailSender.send(mimeMessage);
  }

  public void sendMailWithInline(String email, String message, String subject, FileSystemResource inline)
      throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setTo(email);
    helper.setFrom(sender);
    helper.setSubject(subject);
    helper.setText(message, true);
    helper.addInline("identifier1", inline);
    javaMailSender.send(mimeMessage);

  }

  public void sendMailWithEngine(final String email, final Map model, final String subject, final String template) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        Template myTemplate = configuration.getTemplate(template);
        message.setTo(email);
        message.setFrom(sender);
        message.setSubject(subject);
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(myTemplate, model);
        message.setText(text, true);
      }
    };
    this.javaMailSender.send(preparator);
  }

}
