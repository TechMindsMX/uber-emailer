package com.tim.one.emailer.helper

import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

import org.springframework.stereotype.Component

@Component
public class EmailHelper {

	public Message createMessage(Session session) {
		return new MimeMessage(session)
	}

	public BodyPart createMessageBodyPart() {
		return new MimeBodyPart()
	}

	public Multipart createMimeMultipart() {
		return new MimeMultipart()
	}

	public void send(Message message) throws MessagingException {
		Transport.send(message)
	}

}
