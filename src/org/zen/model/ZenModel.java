package org.zen.model;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
