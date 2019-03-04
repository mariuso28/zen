package org.zen.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.RatingJson;
import org.zen.rating.RatingMgr;
import org.zen.services.Services;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;
import org.zen.user.punter.mgr.PunterMgrException;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	@Autowired
	private Services services;
	@Autowired
	private PunterMgr punterMgr;
	private RatingMgr ratingMgr;
	private ZenModelFake zenModelFake = new ZenModelFake();
	
	public final static int FULLCHILDREN = 3;
	
	public ZenModel()
	{
		ratingMgr = new RatingMgr();
	}
	

	public void payJoinFee(Punter punter) throws PunterMgrException
	{
		double buyIn = ratingMgr.getBuyIn();
		Punter sponsor = punterMgr.getByUUID(punter.getSponsorId());
		log.info("Punter : " + punter.getContact() + " paying join fee : " + buyIn + " to : " + sponsor.getContact());
		
		punter.getAccount().xfer(-1*buyIn);
		sponsor.getAccount().xfer(buyIn);
		services.updateAccounts(sponsor,punter);
	}
	
	// COMBINED CHECK AND UPGRADE FOR MODEL INITIALIZATION - 
	// OTHER SHOULD BE SEPARATE - FOR SIMULATION CHECK ELIGIBLE THEN SCHEDULE WHEN
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
	private int canUpgrade2(Punter punter) throws PunterMgrException
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
		punter.setUpgradeScheduled(false);
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
	
}
