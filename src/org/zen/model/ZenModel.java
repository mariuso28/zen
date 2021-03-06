package org.zen.model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterProfileJson;
import org.zen.json.RatingJson;
import org.zen.rating.RatingMgr;
import org.zen.rest.services.RestServices;
import org.zen.rest.services.RestServicesException;
import org.zen.services.Services;
import org.zen.simulate.rest.RestSimulate;
import org.zen.simulate.rest.RestSimulateAquisitions;
import org.zen.user.faker.FakeContact;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;
import org.zen.user.punter.mgr.PunterMgrException;
import org.zen.user.punter.persistence.PunterDao;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	@Autowired
	private Services services;
	@Autowired
	private RestServices restServices;
	@Autowired
	private PunterMgr punterMgr;
	@Autowired
	private PunterDao punterDao;
	private RatingMgr ratingMgr;
	private ZenModelFake zenModelFake = new ZenModelFake();
	public final static int FULLCHILDREN = 3;
	
	public ZenModel()
	{
		ratingMgr = new RatingMgr();
	}
	
	private void recruitPunters(ZenModelInitialize zmi,int initLevel, int level) throws Exception
	{
		Punter root;
		if (initLevel==0)
		{
			root = zmi.initializeModel();
			addPaymentMethod(root.getContact(),0);
			log.info("Populating levels " + 0 + " - " + level);
			recruitPuntersToLevel(root,0,level);
		}
		else
		{
			log.info("Populating levels " + initLevel + " - " + level);
			recruitPuntersBetweenLevels(initLevel,level);
		}
	}

	void recruitPuntersBetweenLevels(int initialLevel,int level)
	{	
		List<Punter> lev = punterDao.getPuntersForLevel(initialLevel+1);
		for (Punter p : lev)
			punterDao.deletePunter(p);
		for (int l=initialLevel;  l< level; l++)
		{
			lev = punterDao.getPuntersForLevel(l);
			for (Punter p : lev)
			{
				for (int i=0; i<FULLCHILDREN; i++)
				{
					recruitNewPunter(p);
				}
			}
		}
	}
	
	private void upgradePunter(Punter p) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String ds = sdf.format((new GregorianCalendar()).getTime());
		long paymentId = restServices.submitTransactionDetails(p.getContact(),null,ds,
				"Pay from " + p.getContact() + " to " + p.getSponsorContact());
		restServices.approvePayment(p.getSponsorContact(),Long.toString(paymentId));
	}

	private void recruitPuntersToLevel(Punter root,int initialLevel,int level)
	{
		if (level==initialLevel)
			return;
		for (int i=0; i<FULLCHILDREN; i++)
			recruitNewPunter(root);
		level--;
		if (level==initialLevel)
			return;
		List<Punter> children = punterDao.getChildren(root);
		for (Punter c : children)
			recruitPuntersToLevel(c,initialLevel,level);
	}
	
	private void performUpgrades(Punter punter) {
		
		while (punter.getRating()!=-1)
		{
			if (punter.isUpgradeScheduled())
				upgradePunter(punter);
			else
				break;
			punter = punterDao.getByContact(punter.getParentContact());
		}
	}
	
	public void recruitNewPunter(Punter sponsor)
	{
		try
		{
			PunterProfileJson np = getNewPunter(sponsor);
			Punter punter = punterDao.getByContact(np.getContact());
			addPaymentMethod(np.getContact(),punter.getLevel());
			punter = punterDao.getByContact(np.getContact());
			// performUpgrades(zenRoot,punter.getLevel());
			performUpgrades(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw e;
		}
	}
	
	public PunterProfileJson getNewPunter(Punter sponsor)
	{
	    log.info("Creating new punter");
		while (true)
		{
			try
			{
				FakeContact fc = punterMgr.getFakeContact(sponsor.getLevel()<ZenModelOriginal.SYSTEMLEVELS);
				PunterProfileJson np = zenModelFake.createProfile(sponsor,fc);
				restServices.register(sponsor.getContact(),np);
				return np;
			}
			catch (RestServicesException e)
			{
				log.warn("Create punter failed : " + e.getMessage() + " recreating punter");
			}
		}
	}
	
	private void addPaymentMethod(String npContact,int level) {
		String acNum = "";
		for (int i=0; i<8; i++)
			acNum += level;
		PaymentMethodJson pm = restServices.getServices().getHome().getPaymentDao().getPaymentMethodByMethod("Touch n Go");
		restServices.addPunterPaymentMethod(npContact,Integer.toString(pm.getId()),acNum);
	}
	
	// COMBINED CHECK AND UPGRADE FOR MODEL INITIALIZATION - 
	// OTHER SHOULD BE SEPARATE - 
	// FOR SIMULATION CHECK ELIGIBLE THEN SCHEDULE WHEN
	// FOR REAL CHECK THEN PUNTER WILL EXECUTE WHEN WANTS
	public void tryUpgrade(Punter punter) throws PunterMgrException
	{
		int newRating = canUpgrade2(punter);
		if (newRating<=0)
			return;
		upgrade(punter,newRating);
		while (true)
		{
			Punter parent = punterMgr.getByUUID(punter.getParentId());
			newRating = canUpgrade2(parent);
			if (newRating<=0)
				return;
			upgrade(parent,newRating);
			punter = parent;
		}
	}
	
	// COMBINED CHECK AND UPGRADE FOR MODEL INITIALIZATION WITH 
	public int canUpgrade2(Punter punter) throws PunterMgrException
	{
		if (punter.getRating()==0)
			return -1;
		List<Punter> children = punterMgr.getChidren(punter);
		if (children.size()<ZenModel.FULLCHILDREN)
			return -1;
		for (Punter c : children)
		{
			if (c.getRating()<punter.getRating())
				return -1;
		}
		int newRating = Integer.MAX_VALUE;
		for (Punter c : children)
		{
			if (c.getRating()<newRating)
				newRating = c.getRating();
		}
		return newRating+1;
	}
	
	public void upgrade(Punter punter, int newRating) throws PunterMgrException
	{
		if (punter.getRating()==Integer.MAX_VALUE)
			return;
		if (newRating == punter.getRating())
			return;
		punter.setRating(newRating);
		Punter parentToPay = punterMgr.getByUUID(punter.getParentId());
		if (parentToPay.getRating()!=0)					// jump a generation
		{
			parentToPay = punterMgr.getByUUID(parentToPay.getParentId());
		}
		// find the parent above rating
		while (parentToPay.getRating() != 0 && parentToPay.getRating()<punter.getRating())
		{
			parentToPay = punterMgr.getByUUID(parentToPay.getParentId());
		}
		RatingJson rating = ratingMgr.getRatings().get(punter.getRating());
		double upgrade = rating.getUpgrade();
		punter.getAccount().xfer(-1*upgrade);
		parentToPay.getAccount().xfer(upgrade);
		log.info("Upgraded punter : " + punter.getContact() + " rating : " + punter.getRating()
				+ " xferred : " + upgrade + " to " + parentToPay.getEmail() + " rating : " + parentToPay.getRating());
		services.updateAccountsAndRating(parentToPay,punter);
	}

	@SuppressWarnings("unused")
	private int canUpgrade(Punter punter) throws PunterMgrException
	{
		if (punter.getRating()==0)
			return -1;
		RatingJson rating = ratingMgr.getRatings().get(punter.getRating());
		if (countDownstreamChildren(punter,punter.getRating())==rating.getUpgradeThreshold())
			return punter.getRating() + 1;
		return -1;
	}
	
	private int countDownstreamChildren(Punter punter,int rating) throws PunterMgrException
	{
		int sz = punterMgr.getChidrenCnt(punter);
		if (sz==3)
			log.info("Maybe eligible");
		if (--rating==0)
			return sz;
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter child : children)
			sz += countDownstreamChildren(child,rating);
		return sz;
	}
	
	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public PunterMgr getPunterMgr() {
		return punterMgr;
	}

	public void setPunterMgr(PunterMgr punterMgr) {
		this.punterMgr = punterMgr;
	}


	public ZenModelFake getZenModelFake() {
		return zenModelFake;
	}


	public void setZenModelFake(ZenModelFake zenModelFake) {
		this.zenModelFake = zenModelFake;
	}
	
	private static void altCreateModel(RestServices rs,ZenModel zm, ZenModelInitialize zmi) throws Exception
	{
		Punter root = zmi.initializeModel();
		zm.addPaymentMethod(root.getContact(),0);
		new RestSimulateAquisitions(rs,root);
		
		RestSimulate rSim = new RestSimulate(rs);
		for (int i=1; i<4; i++)
		{
			rSim.simulateAcquisitionsAtLevel(i);
//			rSim.sumulateUpgradesFromLevel(i);
		}
		
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			RestServices rs = (RestServices) context.getBean("restServices");
			
			ZenModelInitialize zmi = (ZenModelInitialize) context.getBean("zenModelInitialize");
			ZenModel zm = (ZenModel) context.getBean("zenModel");
		
			altCreateModel(rs,zm,zmi);

			
		//	zm.recruitPunters(zmi, 0, 4);
//			zmi.printModel(root);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log.info("Done");
		System.exit(0);
	}
	
}
