package com.tim.one.messengine

import static com.tim.one.bean.MessageType.*
import groovy.transform.AutoClone

import javax.jms.JMSException
import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.ObjectMessage

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.makingdevs.integration.MailService
import com.tim.one.bean.mail.MessageBean
import com.tim.one.service.NotificationService

@Service
class JmsMessageListener implements MessageListener {

  @Autowired
  NotificationService notificationService

	Log log = LogFactory.getLog(getClass())

	def void onMessage(Message message) {
		log.info("MESSAGE RECEIVED")
		try {
      Object messageBean = ((ObjectMessage) message).getObject()
      notificationService.sendNotification(messageBean)
		} catch (JMSException jmse) {
			log.error(jmse, jmse)
		}
	}

}
