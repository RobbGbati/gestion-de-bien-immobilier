package ma.ensa.service;

import java.util.List;

//import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;
	
	//@Autowired
	//private VelocityEngine engine;
	
	private String sender;
	
	//@SuppressWarnings("unused")
	private String messag;
	
	/*public EmailService(String sender,String messag) {
		this.sender = sender;
		this.messag=messag;
	}*/

	public int  sendMessage(String receiver, String msg) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("Biens Sugérés");
		message.setFrom("user1@gmail.com");
		message.setTo(receiver);
		 
		message.setText(msg);

		mailSender.send(message);
		log.info("message envoyé");
		return 1; 
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessag() {
		return messag;
	}

	public void setMessag(String messag) {
		this.messag = messag;
	}
	
	public void addMessage(String caract ,String messag) {
		this.messag = caract + messag;
	}

	
}
