package org.zen.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.zen.payment.Xtransaction;
import org.zen.persistence.home.Home;
import org.zen.user.BaseUser;
import org.zen.user.punter.Punter;
import org.zen.user.punter.persistence.PunterDao;

public class Services {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Services.class);
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	private Home home;
	private Mail mail;
	private String propertiesPath;
	private Properties prop;
	private MailNotifier mailNotifier;
	
	public void initServices()
	{	
		prop = new Properties();
		try {
			prop.load(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(5);
		} 
		mail.setMailCcNotifications(prop.getProperty("mailCcNotifications"));
		mail.setMailSendFilter(prop.getProperty("mailSendFilter"));
		mail.setMailDisabled(prop.getProperty("mailDisabled"));
	
		setMailNotifier(new MailNotifier(this));
		
	}
	
	
	public synchronized void updateAccountsAndRating(Punter parentToPay, Punter punter) {
		new TransactionTemplate(getTransactionManager()).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				doPerformUpdateAccounts(parentToPay,punter);
				home.getPunterDao().updateRating(punter);
			}
		});
	}
	
	public synchronized void updateAccounts(Punter sponsor, Punter punter) {
	//	log.info("%%%updateAccounts:");
		new TransactionTemplate(getTransactionManager()).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				doPerformUpdateAccounts(sponsor,punter);
			}
		});
	}
	
	private void doPerformUpdateAccounts(final Punter sponsor, final Punter punter) {
		PunterDao pd = home.getPunterDao();
		pd.updateAccount(sponsor.getAccount());
		pd.updateAccount(punter.getAccount());
	}
	
	public synchronized void updatePaymentFail(Xtransaction xt,final Punter sponsor,final Punter punter) {
		new TransactionTemplate(getTransactionManager()).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				doUpdatePaymentFail(xt,sponsor,punter);
			}
		});
	}
		
	private void doUpdatePaymentFail(Xtransaction xt,Punter sponsor,Punter punter) {
		// update the paymentstatus xtransaction, upgradestatus, rating of punter, email the punter
		home.getPunterDao().updateUpgradeStatus(punter);
		home.getPaymentDao().updateXtransaction(xt.getId(), xt.getPaymentStatus());
	}
	
	public synchronized void updatePaymentSuccess(Xtransaction xt,final Punter sponsor,final Punter punter) {
		new TransactionTemplate(getTransactionManager()).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				doUpdatePaymentSuccess(xt,sponsor,punter);
			}
		});
	}
		
	private void doUpdatePaymentSuccess(Xtransaction xt,Punter sponsor,Punter punter) {
		// update the paymentstatus xtransaction, upgradestatus, rating of punter, email the punter
		home.getPunterDao().updateRating(punter);
		home.getPunterDao().updateUpgradeStatus(punter);
		home.getPaymentDao().updateXtransaction(xt.getId(), xt.getPaymentStatus());
		sponsor.getAccount().xfer(xt.getAmount());
		punter.getAccount().xfer(-1*xt.getAmount());
		doPerformUpdateAccounts(sponsor,punter);
	}

	public BaseUser getBaseUser(String contact)
	{
		return home.getPunterDao().getByContact(contact);
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public String getPropertiesPath() {
		return propertiesPath;
	}
	
	public void setPropertiesPath(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}
	
	public Properties getProp() {
		return prop;
	}
	
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	public Mail getMail() {
		return mail;
	}
	
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	public MailNotifier getMailNotifier() {
		return mailNotifier;
	}
	public void setMailNotifier(MailNotifier mailNotifier) {
		this.mailNotifier = mailNotifier;
	}
	
	

}
