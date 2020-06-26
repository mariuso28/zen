package org.zen.simulate.rest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PunterProfileJson;
import org.zen.model.ZenModel;
import org.zen.rest.services.RestServices;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgrException;

public class RestSimulateAquisitions {
	private static Logger log = Logger.getLogger(RestSimulateAquisitions.class);
	
	public RestSimulateAquisitions(RestServices rs,Punter sponsor)
	{
		try
		{
			SimulateSponsor ss = new SimulateSponsor(rs,sponsor);
			for (int i=0; i<ZenModel.FULLCHILDREN; i++)
			{
				RestRegisterNew rrn = new RestRegisterNew(rs,ss);
				rrn.run();
			/*	PunterProfileJson pj = ss.registerNewPunter();
				Punter punter;
				try {
					punter = rs.getPunterMgr().getByContact(pj.getContact());
				} catch (PunterMgrException e) {
					e.printStackTrace();
					return;
				}
				SimulatePunter sp = new SimulatePunter(rs,punter);
				long id = sp.submitTransactionDetails();
				ss.approvePaymentDetails(id);
				*/
			}
		
		} catch (Exception e) {
			log.error("RestSimulateAquisitions",e);
			throw new RestSimulateException("RestSimulateAquisitions");
		}
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("zen-service.xml");
		try
		{
			RestServices rs = (RestServices) context.getBean("restServices");
			rs.getServices().getHome().getPunterDao().deletePuntersBetweenLevels(5,6);
			Punter sponsor = rs.getPunterMgr().getByContact("cora");
			new RestSimulateAquisitions(rs,sponsor);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}

