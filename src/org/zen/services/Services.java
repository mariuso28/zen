package org.zen.services;

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
	
	public void initServices()
	{
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
	
	public synchronized void updatePayment(Xtransaction xt,final Punter sponsor,final Punter punter) {
		new TransactionTemplate(getTransactionManager()).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				doUpdatePayment(xt,sponsor,punter);
			}
		});
	}
		
	public void doUpdatePayment(Xtransaction xt,Punter sponsor,Punter punter) {
		// update the paymentstatus xtransaction, upgradestatus, rating of punter, email the punter
		home.getPunterDao().updateRating(punter);
		home.getPunterDao().updateUpgradeStatus(punter);
		home.getPaymentDao().updateXtransaction(xt.getId(), xt.getPaymentStatus());
		sponsor.getAccount().setBalance(sponsor.getAccount().getBalance()+xt.getAmount());
		punter.getAccount().setBalance(punter.getAccount().getBalance()-xt.getAmount());
		doPerformUpdateAccounts(sponsor,punter);
		
		// EMAIL PUNTER
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
	

}
