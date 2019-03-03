package org.zen.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.zen.persistence.home.Home;
import org.zen.user.BaseUser;
import org.zen.user.agent.DefaultAgent;
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
		
	public BaseUser getBaseUser(String email)
	{
		return DefaultAgent.get();
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
