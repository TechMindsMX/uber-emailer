package com.makingdevs.integration;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.FileSystemResource;

public interface MailService {
	void sendMimeMail(String email,String message,String subject);
	void sendMailWithAttach(String email,String message,String subject,FileSystemResource attach) throws MessagingException;
	void sendMailWithInline(String email,String message,String subject,FileSystemResource inline) throws MessagingException;
	void sendMailWithEngine(String email,Map model,String subject,String template);
}
