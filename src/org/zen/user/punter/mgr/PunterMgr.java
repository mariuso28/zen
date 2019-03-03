package org.zen.user.punter.mgr;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	private PunterDao punterDao;
	
	public PunterMgr()
	{
		
	}
	
	public int getLevel(Punter punter,Punter upstream) throws PunterMgrException
	{
		int level=0;
		if (punter.getId().equals(upstream.getId()))
			return level;
		Punter parent = punter;
		while (true)
		{
			level++;
			parent = punterDao.getById(parent.getParentId());
			if (parent == null)
				throw new PunterMgrException("Could not get level - contact support");
			if (parent.getId().equals(upstream.getId()))
				return level;
		}
	}
	
	public Punter registerPunter(ProfileJson profile) throws PunterMgrException{
		BaseUserDao bud = services.getHome().getBaseUserDao();
		try
		{
			List<String> contacts = bud.getNearestContactId(profile.getContact());
			if (!contacts.isEmpty())
			{
				String msg = "Member name : " + profile.getContact() + " already taken please choose another. Suggested alternatives : ";
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
		Punter parent = null;
		if (sponsor!=null)
			parent = getParent(sponsor);
		Punter punter = createPunter(profile,sponsor,parent);
		
		try
		{
			punterDao.store(punter);
			return punterDao.getByContact(punter.getContact());
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
		if (!pv.validate(profile.getPhone()))
			throw new PunterMgrException("Invalid phone number : " + profile.getPhone() + " - please fix.");
		EmailValidator ev = new EmailValidator();
		if (!ev.validate(profile.getEmail()))
			throw new PunterMgrException("Invalid email : " + profile.getEmail() + " - please fix.");
	}
	
	private Punter createPunter(ProfileJson profile, Punter sponsor, Punter parent) {
		Punter punter = new Punter();
		punter.copyProfileValues(profile);
		punter.setSponsor(sponsor);
		punter.setParent(parent);
		punter.setAccount(new Account());
		if (sponsor==null)			// root
			punter.setRating(0);
		else
			punter.setRating(1);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		punter.setPassword(encoder.encode(profile.getPassword()));			
		return punter;
	}

	private Punter getParent(Punter root)
	{
		List<Punter> children = punterDao.getChildren(root);
		if (children.size()<ZenModelOriginal.FULLCHILDREN)
			return root;
		Random r = new Random();
		Punter parent = children.get(r.nextInt(ZenModelOriginal.FULLCHILDREN));
		return getParent(parent);
	}

	private Punter validateSponsor(String sponsorContactId) throws PunterMgrException {
		if (sponsorContactId==null)				// root;
			return null;
		try
		{
			Punter sponsor = punterDao.getByContact(sponsorContactId);
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
		EmailValidator ev = new EmailValidator();
		if (!ev.validate(contact+"@test.com"))
		{
			log.info(contact);
			throw new PunterMgrException("Please make contact from alpha and digit and period characters only.");
		}
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

	public void deleteAllContacts() throws PunterMgrException
	{
		try
		{
			punterDao.deleteAllPunters();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not delete all contacts - contact support.");
		}
	}
	
	
	public void deleteByContact(final Punter punter) throws PunterMgrException
	{
		try
		{
			punterDao.deletePunter(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not delete punter by contact - contact support.");
		}
	}
	
	public Punter getByContact(final String contact) throws PunterMgrException
	{
		try
		{
			Punter punter = punterDao.getByContact(contact);
			populatePunter(punter);
			return punter;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not get punter by contact - contact support.");
		}
	}
	
	public List<Punter> getChidren(final Punter punter) throws PunterMgrException
	{
		try
		{
			List<Punter> children = punterDao.getChildren(punter);
			return children;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not get children - contact support.");
		}
	}
	
	public int getChidrenCnt(final Punter punter) throws PunterMgrException
	{
		try
		{
			return punterDao.getChildrenCnt(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not get children - contact support.");
		}
	}
	
	public Punter getByUUID(final UUID id) throws PunterMgrException
	{
		try
		{
			Punter punter = punterDao.getById(id);
			populatePunter(punter);
			return punter;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException("Could not get punter by id - contact support.");
		}
	}

	private void populatePunter(Punter punter) throws Exception{
		punter.setSponsor(punterDao.getById(punter.getSponsorId()));
		punter.setParent(punterDao.getById(punter.getParentId()));
	}

	public RatingMgr getRatingMgr() {
		return ratingMgr;
	}

	public void setRatingMgr(RatingMgr ratingMgr) {
		this.ratingMgr = ratingMgr;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
	
}
