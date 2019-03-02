package org.zen.user.punter.manager;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.ProfileJson;
import org.zen.model.ZenModelOriginal;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.rating.RatingMgr;
import org.zen.services.Services;
import org.zen.user.account.Account;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.Punter;
import org.zen.user.punter.persistence.PunterDao;
import org.zen.util.EmailValidator;
import org.zen.util.PhoneValidator;

public class PunterMgr {

	private static final Logger log = Logger.getLogger(PunterMgr.class);

	@Autowired
	private Services services;
	private RatingMgr ratingMgr;
	
	public PunterMgr()
	{
		ratingMgr = new RatingMgr();
	}

	public void registerPunter(ProfileJson profile) throws PunterMgrException{
		BaseUserDao bud = services.getHome().getBaseUserDao();
		try
		{
			List<String> contacts = bud.getNearestContactId(profile.getContact());
			if (!contacts.isEmpty())
			{
				String msg = "Zen member name : " + profile.getContact() + " already taken please choose another. Suggested alternatives : ";
				for (String contact : contacts)
					msg += contact + ",";
				throw new PunterMgrException(msg.substring(0,msg.length()-1)+".");
			}
			validateContact(profile.getContact());
		}
		catch (PersistenceRuntimeException e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not register member with contact name - contact support.");
		}
		validateProfileValues(profile);
		validatePassword(profile.getPassword());
		Punter sponsor = validateSponsor(profile.getSponsorContactId());
		Punter parent = getParent(sponsor);
		Punter punter = createPunter(profile,sponsor,parent);
		
		PunterDao pd = services.getHome().getPunterDao();
		try
		{
			pd.store(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not register member error on store - contact support.");
		}
	}
	
	private void validateProfileValues(ProfileJson profile) throws PunterMgrException
	{
		PhoneValidator pv = new PhoneValidator();
		if (pv.validate(profile.getPhone()))
			throw new PunterMgrException("Invalid phone number - please fix.");
		EmailValidator ev = new EmailValidator();
		if (ev.validate(profile.getEmail()))
			throw new PunterMgrException("Invalid email - please fix.");
	}
	
	private Punter createPunter(ProfileJson profile, Punter sponsor, Punter parent) {
		Punter punter = new Punter();
		punter.copyProfileValues(profile);
		punter.setSponsor(sponsor);
		punter.setParent(parent);
		punter.setAccount(new Account());
		punter.setRating(ratingMgr.getRatings().get(1));
		return punter;
	}

	private Punter getParent(Punter root)
	{
		List<Punter> children = root.getChildren();
		if (children.size()<ZenModelOriginal.FULLCHILDREN)
			return root;
		Random r = new Random();
		Punter parent = children.get(r.nextInt(ZenModelOriginal.FULLCHILDREN));
		return getParent(parent);
	}

	private Punter validateSponsor(String sponsorContactId) throws PunterMgrException {
		PunterDao pdo = services.getHome().getPunterDao();
		try
		{
			Punter sponsor = pdo.getByContact(sponsorContactId);
			if (sponsor==null)
			{
				String msg = "Zen sponsor id : " + sponsorContactId + " does not exist. Please check.";
				log.error(msg);
				throw new PunterMgrException(msg);
			}
			return sponsor;
		}
		catch (PersistenceRuntimeException e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not validate sponsor - contact support.");
		}
	}

	private void validateContact(String contact) throws PunterMgrException{
		for (int i=0; i<contact.length(); i++)
			if (!Character.isDigit(contact.charAt(i)) && !Character.isAlphabetic(contact.charAt(i)))
				throw new PunterMgrException("Please make contact up of alpha and digit characters only");
	}

	private void validatePassword(String password) throws PunterMgrException{
		if (password.length()>=8)
		{
			for (int i=0; i<password.length(); i++)
				if (Character.isDigit(password.charAt(i)))
					return;
		}
		throw new PunterMgrException("Password must be at least 8 characters and contain at least 1 digit.");
	}

	
	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	
}
