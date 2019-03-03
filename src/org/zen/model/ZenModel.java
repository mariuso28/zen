package org.zen.model;

import java.util.ArrayList;
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
	
	public void upgrade(Punter punter) throws PunterMgrException
	{
		punter.setUpgradeScheduled(false);
		if (punter.getRating()==Integer.MAX_VALUE)
			return;
		punter.setRating(punter.getRating()+1);
		Punter parentToPay = punter.getParent();
		if (parentToPay.getParent()!=null)					// jump a generation
			parentToPay = parentToPay.getParent();
		// find the parent with equal or above rating (should it be above?)
		while (parentToPay.getRating() != 0 && parentToPay.getRating()<punter.getRating())
		{
			parentToPay = punterMgr.getByUUID(parentToPay.getParentId());
		}
		RatingJson rating = ratingMgr.getRatings().get(punter.getRating());
		double upgrade = rating.getUpgrade();
		punter.getAccount().xfer(-1*upgrade);
		parentToPay.getAccount().xfer(upgrade);
		log.info("Upgraded punter : " + punter.getContact() + " rating : " + punter.getRating()
				+ " xferred : " + upgrade + " to " + parentToPay.getEmail());
		services.updateAccounts(parentToPay,punter);
	}

	public List<Punter> canUpgrade(Punter punter) throws PunterMgrException
	{
		// if this punter can upgrade possible other upstream parents also eligible
		List<Punter> upgradables = new ArrayList<Punter>();
		if (punter.getRating()==0)
			return upgradables;
		RatingJson rating = ratingMgr.getRatings().get(punter.getRating());
		if (countDownstreamChildren(punter,punter.getRating())==rating.getUpgradeThreshold())
		{
			log.info("Punter : " + punter.getContact() + " Rating : " + punter.getRating() + " is upgradable");
			upgradables.add(punter);
			addUpstreamParents(punter,upgradables);
		}
		return upgradables;
	}
	
	private int countDownstreamChildren(Punter punter,int rating) throws PunterMgrException
	{
		int sz = punterMgr.getChidrenCnt(punter);
		if (sz==3)
			log.info("Maybe eligible");
		if (--rating==0)
			return sz;
		for (Punter child : punter.getChildren())
			sz += countDownstreamChildren(child,rating);
		return sz;
	}
	
	private void addUpstreamParents(Punter punter,List<Punter> upgradables) throws PunterMgrException {
		Punter parent = punterMgr.getByUUID(punter.getParentId());
		if (parent.getRating()==0)
			return;
		RatingJson rating = ratingMgr.getRatings().get(parent.getRating());
		if (countDownstreamChildren(parent,parent.getRating())==rating.getUpgradeThreshold())
		{
			log.info("Parent Punter : " + parent.getContact() + " Rating : " + parent.getRating() + " is upgradable");
			upgradables.add(parent);
			addUpstreamParents(parent,upgradables);
		}
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
	
}
