package ma.ensa.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class JobListener extends JobExecutionListenerSupport {

	@Autowired
	private JavaMailSender mailSender;
	
	private String notificationEmail;
	private String receivermail;

	public JobListener(String notificationEmail,String receivermail) {
		this.notificationEmail = notificationEmail;
		this.receivermail=receivermail;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("Job completion");
		message.setFrom(notificationEmail);
		message.setTo(receivermail);
		message.setText("Job completed with " + jobExecution.getExitStatus());

		mailSender.send(message);
		System.out.println("Job Finit with success");
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("Job started");
		message.setFrom(notificationEmail);
		message.setTo(receivermail);
		message.setText("Job started");

		mailSender.send(message);

	}

}
