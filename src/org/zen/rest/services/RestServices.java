package org.zen.rest.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.AccountJson;
import org.zen.json.ChangePasswordJson;
import org.zen.json.ModelJson;
import org.zen.json.PunterProfileJson;
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
	
	public void registerPunter(PunterProfileJson profile) throws RestServicesException
	{
		try {
			punterMgr.registerPunter(profile);
		} catch (PunterMgrException e) {
			log.error(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}		
	}
	
	public String getRandomUsername() {
		try {
			return punterMgr.getRandomUsername();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}		
	}
	
	
	public ModelJson getModel(String rootContact) throws Exception
	{
		ModelJson mj = new ModelJson();
		List<PunterJson> punters = getPunters(rootContact,mj);
		mj.setPunters(punters);
		double systemOwnedRevenue = services.getHome().getPunterDao().getRevenue('S');
		mj.setSystemOwnedRevenue(systemOwnedRevenue);
		double punterOwnedRevenue = services.getHome().getPunterDao().getRevenue('P');
		mj.setPunterOwnedRevenue(punterOwnedRevenue);
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
		if (punter.isSystemOwned())
			mj.setPopulationInside(mj.getPopulationInside()+1);
		else
			mj.setPopulationOutside(mj.getPopulationOutside()+1);
		
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
	
	public void changePassword(String contact, ChangePasswordJson changePassword) throws RestServicesException{
		Punter punter = getPunter(contact);
		try {
			punterMgr.changePassword(punter,changePassword);
		} catch (PunterMgrException e) {
			log.info(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}
	}
	
	public void register(String contact, PunterProfileJson profile) throws RestServicesException{
		try
		{
			profile.setSponsorContact(contact);
			punterMgr.registerPunter(profile);
		}
		catch (PunterMgrException e)
		{
			throw new RestServicesException(e.getMessage());
		}
	}

	public void updatePunterProfile(String contact,PunterProfileJson profile) throws RestServicesException{
		Punter punter = getPunter(contact);
	
		try {
			punterMgr.validateProfileValues(profile);
		} catch (PunterMgrException e) {
			log.info(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}
		
		punter.copyProfileValues(profile);
		try
		{
			services.getHome().getPunterDao().update(punter);
		}
		catch (Exception e)
		{
			log.error("updatePunterProfile",e);
			throw new RestServicesException("Zen Member : " + contact + " could not be updated - contact support");
		}
	}

	public PunterProfileJson getPunterProfile(String contact) throws RestServicesException{
	
		Punter punter = getPunter(contact);
		return createPunterProfileJson(punter);
	}

	private Punter getPunter(String contact) throws RestServicesException{
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			if (punter == null)
				throw new RestServicesException("Zen Member : " + contact + " not found - contact support");
		}
		catch (Exception e)
		{
			log.error("updatePunterProfile",e);
			throw new RestServicesException("Zen Member : " + contact + " not found - contact support");
		}
		return punter;
	}
	
	public List<PunterProfileJson> getDownstreamPunters(String contact) {
		List<PunterProfileJson> dsp = new ArrayList<PunterProfileJson>();
		Set<UUID> got = new HashSet<UUID>();
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			if (punter == null)
				throw new RestServicesException("Zen Member : " + contact + " not found - contact support");
			List<Punter> punters = services.getHome().getPunterDao().getChildren(punter);
			for (Punter p : punters)
			{
				dsp.add(createPunterProfileJson(p));
				got.add(p.getId());
			}
			punters = services.getHome().getPunterDao().getSponsoredChildren(punter);
			for (Punter p : punters)
			{
				if (got.contains(p.getId()))
					continue;
				dsp.add(createPunterProfileJson(p));
			}
			return dsp;
		}
		catch (Exception e)
		{
			log.error("getDownstreamPunters",e);
			throw new RestServicesException("Zen downstream members for : " + contact + " not found - contact support");
		}
	}
	
	private PunterProfileJson createPunterProfileJson(Punter punter) {
		PunterProfileJson pj = new PunterProfileJson();	
		pj.setId(punter.getId());
		pj.setContact(punter.getContact());
		pj.setEmail(punter.getEmail());
		pj.setPhone(punter.getPhone());
		pj.setSystemOwned(punter.isSystemOwned());
		pj.setFullName(punter.getFullName());
		pj.setGender(punter.getGender());
		pj.setPassportIc(punter.getPassportIc());
		pj.setAddress(punter.getAddress());
		pj.setState(punter.getState());
		pj.setPostcode(punter.getPostcode());
		pj.setCountry(punter.getCountry());
		pj.setUpstreamContact(punter.getParentContact());
		pj.setSponsorContact(pj.getSponsorContact());
		return pj;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	

	
	
	
}
