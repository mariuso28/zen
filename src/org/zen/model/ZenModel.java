package org.zen.model;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.ProfileJson;
import org.zen.rating.RatingMgr;
import org.zen.services.Services;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	private Services services;
	public final static int FULLCHILDREN = 3;
	private Punter root;
	private RatingMgr ratingMgr = new RatingMgr();
	private long population;
	private int maxRating = 0;
	private PunterMgr punterMgr;
	
	public ZenModel(Services services)
	{
		setServices(services);
		punterMgr = new PunterMgr(services);
	}
	
	public void initializeModel(int levels) throws Exception
	{
		punterMgr.deleteByContact("zen");
		ProfileJson rootProfile = makeProfile("zen","zen@test.com","0123456789","88888888",null);
		punterMgr.registerPunter(rootProfile);
		Punter root = punterMgr.getByContact("zen");
		log.info("Got root : " + root);
	}
	
	private ProfileJson makeProfile(String contact,String email,String phone,String password,String sponsorContactId)
	{
		ProfileJson pj = new ProfileJson();
		pj.setContact(contact);
		pj.setEmail(email);
		pj.setPassword(password);
		pj.setPhone(phone);
		pj.setSponsorContactId(sponsorContactId);
		return pj;
	}
	
	public Punter getRoot() {
		return root;
	}

	public void setRoot(Punter root) {
		this.root = root;
	}

	public RatingMgr getRatingMgr() {
		return ratingMgr;
	}

	public void setRatingMgr(RatingMgr ratingMgr) {
		this.ratingMgr = ratingMgr;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public int getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Services services = (Services) context.getBean("services");
			ZenModel zm = new ZenModel(services);
			zm.initializeModel(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log.info("Done");
		System.exit(0);
	}
	
	
}
