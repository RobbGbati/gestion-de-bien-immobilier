package com.gracetech.gestionimmoback.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.gracetech.gestionimmoback.exception.GenericException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class EmailService {

	@Value("${app.mail}")
	private String defaultSender;
	
	private final JavaMailSender emailSender;
	
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(defaultSender);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);
		emailSender.send(msg);
	}
	
	
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws GenericException {
		MimeMessage msg = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
			helper.setFrom(defaultSender);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);
			
			emailSender.send(msg);
		} catch (MessagingException e) {
			throw new GenericException(e.getMessage());
		}
	}
	
	
}
