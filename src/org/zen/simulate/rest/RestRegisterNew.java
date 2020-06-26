package org.zen.simulate.rest;

import org.zen.json.PunterProfileJson;
import org.zen.rest.services.RestServices;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgrException;

public class RestRegisterNew extends Thread{
	private RestServices rs;
	private SimulateSponsor sponsor;

	public RestRegisterNew(RestServices rs,SimulateSponsor sponsor)
	{
		setRs(rs);
		setSponsor(sponsor);
	}
	
	public void run()
	{
		PunterProfileJson pj = sponsor.registerNewPunter();
		Punter punter;
		try {
			punter = rs.getPunterMgr().getByContact(pj.getContact());
		} catch (PunterMgrException e) {
			e.printStackTrace();
			return;
		}
		SimulatePunter sp = new SimulatePunter(rs,punter);
		long id = sp.submitTransactionDetails();
		sponsor.approvePaymentDetails(id);
	}

	public RestServices getRs() {
		return rs;
	}

	public void setRs(RestServices rs) {
		this.rs = rs;
	}

	public SimulateSponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(SimulateSponsor sponsor) {
		this.sponsor = sponsor;
	}

	
}
