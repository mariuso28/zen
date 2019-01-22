package org.zen.rest.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.AccountJson;
import org.zen.json.ModelJson;
import org.zen.json.PunterDetailJson;
import org.zen.json.PunterJson;
import org.zen.services.Services;
import org.zen.user.account.Account;
import org.zen.user.punter.Punter;

public class RestServices {
private static final Logger log = Logger.getLogger(RestServices.class);
	
	@Autowired
	private Services services;
	
	public RestServices()
	{
	}
	
	public ModelJson getModel()
	{
		ModelJson mj = new ModelJson();
		List<PunterJson> punters = getPunters();
		mj.setPunters(punters);
		mj.setPopulation(services.getZenModel().getPopulation());
		mj.setTopRevenue(punters.get(0).getAccount().getBalance());
		return mj;
	}
	
	private List<PunterJson> getPunters()
	{
		Punter root = services.getZenModel().getRoot();
		PunterJson parent = addPunter(root);	
		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		List<PunterJson> result =  new ArrayList<PunterJson>();
		result.add(parent);
		return result;
	}

	private PunterJson addPunter(Punter punter) {
		PunterJson pj = createPunterJson(punter);
		for (Punter p : punter.getChildren())
		{
			pj.getChildren().add(addPunter(p));
		}
		return pj;
	}
	
	private PunterJson createPunterJson(Punter punter)
	{
		PunterJson pj = new PunterJson();
		pj.setId(punter.getSeqId());
		int imageId = 12;
		if (!punter.getEmail().equals("zen@test.com"))
			imageId = punter.getRating().getRating();
		pj.setImageUrl("../../../img/" + imageId + ".jpeg");
		String text = punter.getEmail();
		NumberFormat formatter = new DecimalFormat("#0.00");
		String bal = " RM" + formatter.format(punter.getAccount().getBalance());
		String href = "<a href=# onclick=\"return getPunterDetails(" + 
					punter.getEmail() + ")\"" 
		+ "<font color='#045023'>"+ text + bal + "</font></a>";
		pj.setText(href);
		pj.setAccount(createAccountJson(punter.getAccount()));
		return pj;
	}

	private AccountJson createAccountJson(Account account) {
		AccountJson aj = new AccountJson();
		aj.setBalance(account.getBalance());
		return aj;
	}

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
		pdj.setLevel(punter.getLevel());
		pdj.setPhone(punter.getPhone());
		pdj.setRating(punter.getRating());
		return pdj;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
}
