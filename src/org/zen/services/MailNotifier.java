package org.zen.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
	
	public void notifyUpgradeFailed(Punter punter,Punter sponsor) {
		final String subject = "Important news from Zen. Your payment failed.";
				
		final String msg = "Hi " + punter.getFullName() 
				+ "<br/>Your payment to be upgraded to Rank<br/><br/>" 
				+ punter.getRating()
				+ "<br/>Failed. Please try an alternative method or contact:"
				+ "<br/><br/>" + sponsor.getFullName() + "<br/>Phone:" + sponsor.getPhone() 
				+ "<br/>Email:" + sponsor.getEmail() + "<br/>to resolve.";  
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	public void notifyUpgradeSuccessful(Punter punter) {
		String subject = "Great news from Zen! You have successfully upgraded and can ";
		if (punter.getRating()==1)
			subject += "start to earn $$$";
		else
			subject += "earn even more $$$";
				
		String msg = "Hi " + punter.getFullName() 
				+ "<br/>You have successfully upgraded to Rank<br/><strong>" 
				+ punter.getRating()
				+ "</strong><br/>Good luck in your recruiting and making money!!";
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	
	/*
	public void notifyUpgradeRequired(Punter punter) {
		final String subject = "Great news from Zen! You are now entitled to upgrade and earn more $$$";
				
		final String msg = "Hi " + punter.getFullName() 
				+ "<br/>You have satisfied the requirements to upgrade from Rank<br/>" 
				+ punter.getRating() + " to <strong>" + punter.getRating()+1
				+ "<s/trong><br/>Please login and proceed to upgrade to make your upgrade payment of $" 
				+ services.getPunterMgr().getRatingMgr().getUpgradeFeeForRating(punter.getRating())
				+ "<br/>To continue to recruit and make even more money!!";
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}
	*/
	
	public void notifyFirstUpgradeRequired(Punter punter) {
		final String subject = "Please login to pay your fee to join Zen and start to earn $$$";
				
		final String msg = "Hi " + punter.getFullName() 
				+ "<br/>Your Zen Registration has been successfully set up for username :<br/><br/>" 
				+ punter.getContact() 
				+ "<br/><br/>Please login and proceed to upgrade to make your initial joing payment of $" + RatingMgr.ZENBUYIN
				+ "<br/>To start to recruit and make money!!";
		
		final List<String> attactments = new  ArrayList<String>();
		sendMail(punter, subject, msg, attactments, false);		
	}

	public void notifyPasswordReset(Punter punter,String newPw) {
		final String subject = "Zen - password reset";
		
		final String msg = "Hi " + punter.getFullName() 
				+ "<br/>Your Zen password has been reset for username :<br/><br/>" 
				+ punter.getContact() + "<br/>to : <strong>" + newPw  + "</strong>"
				+ "<br/><br/>Please login and change at your covenience.";
				
		
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
