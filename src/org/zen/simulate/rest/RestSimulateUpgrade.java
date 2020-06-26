package org.zen.simulate.rest;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PunterProfileJson;
import org.zen.model.ZenModel;
import org.zen.rest.services.RestServices;
import org.zen.user.punter.Punter;

public class RestSimulateUpgrade {
	private static Logger log = Logger.getLogger(RestSimulateUpgrade.class);
	
	public RestSimulateUpgrade(RestServices rs,Punter sponsor,Punter punter)
	{
		try
		{
			log.info("Simulating Upgrade for punter : " + punter.getContact() + " sponsor : " + sponsor.getContact());
			SimulateSponsor ss = new SimulateSponsor(rs,sponsor);
			SimulatePunter sp = new SimulatePunter(rs,punter);
			long id = sp.submitTransactionDetails();
			ss.approvePaymentDetails(id);
		} catch (Exception e) {
			log.error("RestSimulateUpgrade",e);
			throw new RestSimulateException("RestSimulateUpgrade");
		}
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("zen-service.xml");
		try
		{
			RestServices rs = (RestServices) context.getBean("restServices");
			Punter sponsor = rs.getPunterMgr().getByContact("josue");
			Punter punter = rs.getPunterMgr().getByUUID(UUID.fromString("589c7357-b052-4d7e-9c2b-834767b8c81f"));
			new RestSimulateUpgrade(rs,sponsor,punter);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}

