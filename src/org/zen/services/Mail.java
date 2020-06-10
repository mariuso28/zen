package org.zen.services;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.zen.user.BaseUser;

public class Mail 
{
	private static Logger log = Logger.getLogger(Mail.class);
	public static String SITEHTMLLINK =  "<a href='http://zenwing.net'>zen</a>";
	
	private JavaMailSender mailSender;
	private String mailCcNotifications;
	private String mailSendFilter;
	private String mailDisabled;
	private String mailFrom;
	
	
	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public static String disclaimer()
	{
		return "\n\nIMPORTANT: This e-mail (including any attachment hereto) is intended solely "
			 + "for the addressee and is confidential and privileged. If you are not an "
			 + "intended recipient or you have received this email in error, you are "
			 + "to immediately notify the sender by a reply email and to delete the "
			 + "transmission including all attachment. In such instances you are further "
			 + "prohibited from reproducing, disclosing, distributing or taking any "
			 + "action in reliance on it. Please be cautioned that URBAN PARADIGM SDN BHD "
			 + "will not be responsible for any viruses or other interfering or damaging "
			 + "elements which may be contained in this e-mail (including any attachments hereto).";
	}
	
	
	private void sendSimpleMail(String dear,String content, List<String> attactments,SimpleMailMessage simpleMailMessage) throws MailException {

		simpleMailMessage.setFrom(mailFrom);
		final MimeMessage message = mailSender.createMimeMessage();
		
		log.info("creating message");
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(simpleMailMessage.getFrom());
			helper.setTo(simpleMailMessage.getTo());
			helper.setSubject(simpleMailMessage.getSubject());
			
			content += "\n\n" + Mail.disclaimer();
			helper.setText(content);		
			
			for (String cc : simpleMailMessage.getCc())
			{
				log.info("adding cc: " + cc);
				helper.addCc(cc);
			}
			
			for (String attach : attactments)
			{
				FileSystemResource file = new FileSystemResource(attach);
				helper.addAttachment(file.getFilename(), file);
			}
			log.info("sending message to : " + simpleMailMessage.getTo()[0] + " from : " + simpleMailMessage.getFrom());
			mailSender.send(message);
			log.info("send message complete");
			
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new MailException(e.getMessage(),e);
		}
	}
	
	
	private void sendMail(String dear,String content, List<String> attactments,SimpleMailMessage simpleMailMessage) throws MailException {

		simpleMailMessage.setFrom(mailFrom);
		final MimeMessage message = mailSender.createMimeMessage();
		
		log.info("creating message");
		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

			helper.setFrom(simpleMailMessage.getFrom());
			helper.setTo(simpleMailMessage.getTo());
			helper.setSubject(simpleMailMessage.getSubject());
			
			content += "\n\n" + Mail.disclaimer();
			
			content = content.replaceAll("\n","<br/>");
	//		helper.setText(content);		
			
			log.info("creating message");
			MimeMultipart multipart = new MimeMultipart();
			
			final MimeBodyPart  messageBodyPart = new MimeBodyPart();
			// HTML Content
			messageBodyPart.setContent(content, "text/html;charset=UTF-8");

			// add it
			multipart.addBodyPart(messageBodyPart);
					
			for (String attach : attactments)
			{
		//		FileSystemResource file = new FileSystemResource(attach);
		//		helper.addAttachment(file.getFilename(), file);
				
				 MimeBodyPart bodyPart = new MimeBodyPart();
			     DataSource source = new FileDataSource(attach);
			     bodyPart.setDataHandler(new DataHandler(source));
			     bodyPart.setFileName(attach);
			     multipart.addBodyPart(bodyPart);
			}
			
			// don't forget to add the content to your message.
			message.setContent(multipart);
			
			for (String cc : simpleMailMessage.getCc())
			{
				log.info("adding cc: " + cc);
				helper.addCc(cc);
			}
			
			log.info("sending message to : " + simpleMailMessage.getTo()[0] + " from : " + simpleMailMessage.getFrom());
			mailSender.send(message);
			log.info("send message complete");
			
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new MailException(e.getMessage(),e);
		}
	}
	
	public String getMailCcNotifications() {
		return mailCcNotifications;
	}

	public void setMailCcNotifications(String mailCcNotifications) {
		this.mailCcNotifications = mailCcNotifications;
	}
	
	public void sendMail(BaseUser user, String subject, String msg, List<String> attactments, boolean simple) throws MailException 
	{
		sendMail(user.getEmail(),user.getContact(),subject,msg,attactments,simple);
	}

	public void sendMail(String email,String name, String subject, String msg, List<String> attactments,boolean simple) throws MailException 
	{
		if (mailDisabled.equals("true"))
		{
			log.info("Message to : " + email + " not sent - mail disabled..");
			return;
		}
		
		if (mailSendFilter!=null && !mailSendFilter.isEmpty())
		{
			String[] filters = mailSendFilter.split(";");
			for (String filter : filters)
			{
				if (email.equals(filter) || email.endsWith(filter))
				{
					log.info("Message to : " + email + " filtered..");
					sendFilteredEmail(email,name,subject,msg,attactments,simple);
					return;
				}
			}
		}
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setCc(getMailCcNotifications().split(";"));
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		if (simple)
			sendSimpleMail(name,msg,attactments,simpleMailMessage);
		else
			sendMail(name,msg,attactments,simpleMailMessage);
	}
	
	private void sendFilteredEmail(String email, String name, String subject, String msg, List<String> attactments, boolean simple) throws MailException 
	{
		String ccs[] = getMailCcNotifications().split(";");
		msg = "Message to : " + email + " was Filtered:\n" + msg;
		for (String cc : ccs)
		{
			log.info("Sending Filtered Message to : " + email + " to cc: " + cc);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setCc(new String[0]);
			simpleMailMessage.setTo(cc);
			simpleMailMessage.setSubject(subject);
			if (simple)
				sendSimpleMail(name,msg,attactments,simpleMailMessage);
			else
				sendMail(name,msg,attactments,simpleMailMessage);
		}
	}
	
	public String getMailSendFilter() {
		return mailSendFilter;
	}

	public void setMailSendFilter(String mailSendFilter) {
		this.mailSendFilter = mailSendFilter;
	}
	
	public String getMailDisabled() {
		return mailDisabled;
	}

	public void setMailDisabled(String mailDisabled) {
		this.mailDisabled = mailDisabled;
	}

	public void notifyPasswordReset(final BaseUser baseUser,String password) throws Exception {
		
		final String subject = "Zen Password Reset";
		final String msg = "Hi " + baseUser.getFullName() + "\nYour password has been reset to\n" +
				password + "\n - please logon with your email and change at your convenience.\nKind regards - " 
					+  SITEHTMLLINK +".";
	
		final List<String> attactments = new  ArrayList<String>();
		
		try
		{
			log.info("#### sending email to " + baseUser.getEmail() + "#######");
			log.info("subject : " + subject); 
			log.info("message : " + msg);
			log.info("#####################################################");
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						sendMail(baseUser, subject, msg, attactments,false);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Couldn't send message : " + e.getMessage());
						try {
							throw e;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

			}).start();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Couldn't send message : " + e.getMessage());
			throw e;
		}
	}
	
	public void notifyEmailVerification(final BaseUser baseUser, String domainTarget) throws Exception {
		final String subject = "Please Verify Your Registration";
		
		// @RequestMapping(value = "/verify", params="code", method = RequestMethod.GET)
		String link = "http://" + domainTarget+"/pkfz/px4/logon/verify?code&id=" + baseUser.getId().toString();
		
		final String msg = "Hi " + baseUser.getFullName() + "\nYour Urban Paradigm Registration is set to your email : " + baseUser.getEmail() 
				+ ".\nPlease click on the link below to activate your registration.\n\n"
				+ link
				+"\n\nKind regards - Zen support team.";
		
		final List<String> attactments = new  ArrayList<String>();
		
		try
		{
			log.info("#### sending email to " + baseUser.getEmail() + "#######");
			log.info("subject : " + subject); 
			log.info("message : " + msg);
			log.info("#####################################################");
				
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						sendMail(baseUser, subject, msg, attactments, true);			// use simple or HTML messes up
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Couldn't send message : " + e.getMessage());
						try {
							throw e;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

			}).start();			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Couldn't send message : " + e.getMessage());
			throw e;
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			@SuppressWarnings("resource")
			ApplicationContext context = 
			new ClassPathXmlApplicationContext("zen-mail.xml");

			JavaMailSender mailSender = (JavaMailSender) context.getBean("mailSender2");
			
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo("mariuso.pk@gmail.com");
			simpleMailMessage.setSubject("XXXX");
			
			simpleMailMessage.setFrom("info@homeland.my");
		
			MimeMultipart multipart = new MimeMultipart();
			
			final MimeMessage message = mailSender.createMimeMessage();
			log.info("creating message");

			String content = "Meow " + SITEHTMLLINK;
			// add HTML content here
			final MimeBodyPart  messageBodyPart = new MimeBodyPart();
			// HTML Content
			messageBodyPart.setContent(content, "text/html;charset=UTF-8");

			// add it
			multipart.addBodyPart(messageBodyPart);
			
			String filePath = "/home/pmk/Downloads/GoldenCircle_02.png";
		     MimeBodyPart bodyPart = new MimeBodyPart();
		     DataSource source = new FileDataSource(filePath);
		     bodyPart.setDataHandler(new DataHandler(source));
		     bodyPart.setFileName(filePath);
		     multipart.addBodyPart(bodyPart);
			
			// don't forget to add the content to your message.
			message.setContent(multipart);
					
			
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			
			
	//		MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(simpleMailMessage.getFrom());
			helper.setTo(simpleMailMessage.getTo());
			helper.setSubject(simpleMailMessage.getSubject());
			
			
			log.info("sending message to : " + simpleMailMessage.getTo()[0] + " from : " + simpleMailMessage.getFrom());
			mailSender.send(message);
			log.info("send message complete");
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		log.info("DONE");
	}


	
	
	
}