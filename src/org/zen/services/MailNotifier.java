package org.zen.services;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zen.payment.PaymentInfo;
import org.zen.payment.Xtransaction;
import org.zen.rating.RatingMgr;
import org.zen.user.BaseUser;
import org.zen.user.punter.Punter;

public class MailNotifier {
	private static Logger log = Logger.getLogger(Services.class);
	private Services services;
	
	public MailNotifier(Services services)
	{
		setServices(services);
	}
	
	private String x(String src)
	{
		return services.getTxm().xlate(src);
	}
	
	public void notifyUpgradeFailed(Punter punter,Punter sponsor) {
		final String subject = x("Important news from Zen. Your payment failed.");
				
		final String msg = x("Hi ") + punter.getFullName() 
				+ "<br/>"+x("Your payment to be upgraded to Rank")+"<br/><br/>" 
				+ punter.getRating()
				+ "<br/>"+x("Failed. Please try an alternative method");  
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	public void notifyUpgradeSuccessful(Punter punter) {
		String subject = x("Great news from Zen! You have successfully upgraded and can ");
		if (punter.getRating()==1)
			subject += x("start to earn $$$");
		else
			subject += x("earn even more $$$");
				
		String msg = x("Hi ") + punter.getFullName() 
				+ "<br/>"+x("You have successfully upgraded to Rank")+"<br/><strong>" 
				+ punter.getRating()
				+ "</strong><br/>"+x("Good luck in your recruiting and making money!!");
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	public void notifyPaymentQuery(Punter zen, Punter payer, Punter payee, Xtransaction xt) {
		// SYSTEM MESSAGE DON'T TRANSLATE
		String attachment = null;
		PaymentInfo pi = xt.getPaymentInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String subject = "Zen payment query from payer: " + payer.getContact();
		String msg = "Zen Upgrade Payer : " + payer.getContact()+" has made a query for resolution of payment to : " + payee.getContact()
				+ "<br/>transaction id : " + xt.getId() + " -  payment details are : "
				+ "<br/>Date : " + sdf.format(pi.getTransactionDate()) + "<br/>Details:";
				if (pi.getUploadFileBytes()!=null)
				{
					msg += " In attachment.";
					attachment = storePayinfoAttachment(pi);
				}
				else
					msg += "<br/>" + pi.getTransactionDetails();
				msg += "<br/><br/>Payee Details:"
						+ "<br/>Zen Username: " + payee.getContact()
						+ "<br/>Full Name: " + payee.getFullName()
						+ "<br/>phone: " + payee.getPhone()
						+ "<br/>email: " + payee.getEmail();
				msg += "<br/><br/>Payer Details:"
						+ "<br/>Zen Username: " + payer.getContact()
						+ "<br/>Full Name: " + payer.getFullName()
						+ "<br/>phone: " + payer.getPhone()
						+ "<br/>email: " + payer.getEmail();
				msg += "<br/><br/>Please review with payee ASAP and contact payer when resolved.";
		final List<String> attactments = new  ArrayList<String>();
		if (attachment != null)
			attactments.add(attachment);
		sendMail(zen, subject, msg, attactments, false);	
	}

	private String storePayinfoAttachment(PaymentInfo pi)
	{
		String path = services.getProp().getProperty("scratchPath");
		if (path == null)
		{
			log.fatal("Scratch path must be set in zen.properties - exiting");
			System.exit(9);
		}
		if (!path.endsWith("/"))
			path += "/";
		int pos = pi.getUploadFileName().indexOf(".");
		String ext = "";
		if (pos>0)
			ext = pi.getUploadFileName().substring(pos-1);
		path += pi.getXtransactionId() + ext;
		log.info("storing image to : " + path);
		try
		{
			FileOutputStream stream = new FileOutputStream(path);
			stream.write(pi.getUploadFileBytes());
			stream.close();		
			return path;
		}
		catch (Exception e)
		{
			log.error("Could not store image",e);
		}
		return null;
	}
	
	public void notifyUpgradeRequired(Punter punter,double fee) {
		final String subject = x("Great news from Zen! You are now entitled to upgrade and earn more $$$");
				
		final String msg = x("Hi ") + punter.getFullName() 
				+ "<br/>"+x("You have satisfied the requirements to upgrade from Rank")+"<br/>" 
				+ punter.getRating() + x(" to ")+"<strong>" + punter.getRating()+1
				+ "</strong><br/>"+x("Please login and proceed to upgrade to make your upgrade payment of $")
				+ fee + "."
				+ "<br/>"+x("To continue to recruit and make even more money!!");
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	public void notifyFirstUpgradeRequired(Punter punter) {
		final String subject = x("Please login to pay your fee to join Zen and start to earn $$$");
				
		final String msg = x("Hi ") + punter.getFullName() 
				+ "<br/>"+x("Your Zen Registration has been successfully set up for username :")+"<br/><br/>" 
				+ punter.getContact() 
				+ "<br/><br/>"+x("Please login and proceed to upgrade to make your initial joing payment of $") + RatingMgr.ZENBUYIN
				+ "<br/>"+x("To start to recruit and make money!!");
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}

	public void notifyPasswordReset(Punter punter,String newPw) {
		final String subject = x("Zen - password reset");
		
		final String msg = x("Hi ") + punter.getFullName() 
				+ "<br/>"+x("Your Zen password has been reset for username :")+"<br/><br/>" 
				+ punter.getContact() + "<br/>"+x(" to : ")+"<strong>" + newPw  + "</strong>"
				+ "<br/><br/>"+x("Please login and change at your convenience.");
				
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	private void sendMail(BaseUser baseUser, String subject, String msg, List<String> attactments,boolean simple)
	{
		if (services.getMail().getMailDisabled().equals("true"))
			return;
		
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
						services.getMail().sendMail(baseUser, subject, msg, attactments, simple);			
					} catch (Exception e) {
						log.error(e.getMessage(),e);
						log.error("Couldn't send message : " + e.getMessage());
						try {
							throw e;
						} catch (Exception e1) {
							log.error(e1.getMessage(),e1);
						}
					}
				}

			}).start();			
		}
		catch (Exception e)
		{
			log.error("Couldn't send message : " + e.getMessage(),e);
			throw e;
		}
	}
	
	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
	

	
	
}
