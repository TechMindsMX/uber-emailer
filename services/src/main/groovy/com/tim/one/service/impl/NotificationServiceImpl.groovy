package com.tim.one.service.impl

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

import com.makingdevs.integration.MailService
import com.tim.one.service.NotificationService
import com.tim.one.bean.mail.MessageBean

@Service
class NotificationServiceImpl implements NotificationService {

  @Autowired
  @Qualifier("emailProperties")
  Properties emailProperties
  @Autowired
  MailService mailService

  private Log log = LogFactory.getLog(getClass())

  @Override
  void sendNotification(MessageBean messageBean) {
		log.info "messageBean: ${messageBean.dump()}" 
    def (subject, templateName) = obtainSubjectAndResourceToSendNotification(messageBean)
    def data = [subject:subject, templateName:templateName, bean:messageBean]
    sendNotificationWithData(data)
  }

  private def obtainSubjectAndResourceToSendNotification(MessageBean messageBean){
    String templateKey = "${messageBean.type.toString()}_PATH"
    String subjectKey = "${messageBean.type.toString()}_SUBJECT"

    String templateName = emailProperties.getProperty(templateKey)
    String subject = emailProperties.getProperty(subjectKey)

    log.info("Sending email with subject: " + subject)

    [subject, templateName]
  }

  private void sendNotificationWithData(emailData){
    String templateName = emailData.templateName
    def bean = emailData.bean
    String subject = emailData.subject
    mailService.sendMailWithEngine(bean.email, bean.properties, subject, templateName);
  }
}
