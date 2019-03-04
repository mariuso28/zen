package org.zen.rest.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.AccountJson;
import org.zen.json.ModelJson;
import org.zen.json.ProfileJson;
import org.zen.json.PunterJson;
import org.zen.rating.RatingMgr;
import org.zen.services.Services;
import org.zen.user.account.Account;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;
import org.zen.user.punter.mgr.PunterMgrException;

public class RestServices {
private static final Logger log = Logger.getLogger(RestServices.class);
	
	@Autowired
	private Services services;
	@Autowired
	private PunterMgr punterMgr;
	@SuppressWarnings("unused")
	private RatingMgr ratingMgr;
	
	public RestServices()
	{
	}
	
	public void registerPunter(ProfileJson profile) throws RestServicesException
	{
		try {
			punterMgr.registerPunter(profile);
		} catch (PunterMgrException e) {
			log.error(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}		
	}
	
	public ModelJson getModel(String rootContact) throws Exception
	{
		ModelJson mj = new ModelJson();
		List<PunterJson> punters = getPunters(rootContact,mj);
		mj.setPunters(punters);//		mj.setPopulation(services.getZenModel().getPopulation());
		mj.setTopRevenue(punters.get(0).getAccount().getBalance());
		double systemOwnedRevenue = services.getHome().getPunterDao().getSystemOwnedRevenue();
		mj.setSystemOwnedRevenue(systemOwnedRevenue);
		return mj;
	}
	
	private List<PunterJson> getPunters(String rootContact, ModelJson mj) throws PunterMgrException
	{
		Punter root = punterMgr.getByContact(rootContact);
		PunterJson parent = addPunter(root,mj);	
//		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		List<PunterJson> result =  new ArrayList<PunterJson>();
		result.add(parent);
		return result;
	}

	private PunterJson addPunter(Punter punter, ModelJson mj) throws PunterMgrException {
		PunterJson pj = createPunterJson(punter);
		mj.setPopulation(mj.getPopulation()+1);
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter p : children)
		{
			pj.getChildren().add(addPunter(p,mj));
		}
		return pj;
	}
	
	private PunterJson createPunterJson(Punter punter)
	{
		PunterJson pj = new PunterJson();
		int imageId = 12;
		if (!punter.getEmail().equals("zen@test.com"))
			imageId = punter.getRating();
		pj.setImageUrl("../../../img/" + imageId + ".jpeg");
		String text = punter.getContact();
		NumberFormat formatter = new DecimalFormat("#0.00");
		String bal = " RM" + formatter.format(punter.getAccount().getBalance());
		String lev = " #" + punter.getRating() + " ";
		String line = null;
		
		if (!punter.isSystemOwned())
			line = "<font color='#045023'>"+ text + bal + lev + "</font></a>";
		else
			line = "<font color='Red'>"+ text + bal + lev + "</font></a>";
		
		String href = "<a href=# onclick=\"return getPunterDetails(" + 
					punter.getEmail() + ")\"" + line;
		pj.setText(href);
		pj.setAccount(createAccountJson(punter.getAccount()));
		return pj;
	}

	private AccountJson createAccountJson(Account account) {
		AccountJson aj = new AccountJson();
		aj.setBalance(account.getBalance());
		return aj;
	}

	/*
	public PunterDetailJson getPunterDetails(String email) {
		
		Punter punter = services.getZenModel().getMap().get(email);
		if (punter==null)
			throw new RestServicesException("Punter : " + email + " Not found.");
		
		return createPunterDetailsJson(punter);
	}

	private PunterDetailJson createPunterDetailsJson(Punter punter) {
		PunterDetailJson pdj = new PunterDetailJson();
		pdj.setAccount(createAccountJson(punter.getAccount()));
		pdj.setContact(punter.getContact());
		pdj.setEmail(punter.getEmail());
		pdj.setPhone(punter.getPhone());
		RatingJson rating = ratingMgr.getRatings().get(punter.getRating());
		pdj.setRating(rating);
		pdj.setSystemOwned(punter.getSystemOwned);
		return pdj;
	}
*/
	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
	
}
