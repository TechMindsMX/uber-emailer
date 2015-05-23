package com.tim.one.messengine

import static com.tim.one.bean.MessageType.*

import javax.jms.JMSException
import javax.jms.Message
import javax.jms.MessageListener
import javax.jms.ObjectMessage

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class LoggerMessageListener implements MessageListener {

	Log log = LogFactory.getLog(getClass())

	def void onMessage(Message message) {
    def data =  ((ObjectMessage) message).getObject()
    String dataToString = data.collect { k, v -> v }.join(';')
    log.info dataToString
	}

}
