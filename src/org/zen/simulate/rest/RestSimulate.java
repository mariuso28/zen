package org.zen.simulate.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.rest.services.RestServices;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgrException;
import org.zen.user.punter.upgrade.UpgradeStatus;

public class RestSimulate {
	private static Logger log = Logger.getLogger(RestSimulate.class);
	private RestServices restServices;
	
	public RestSimulate(RestServices rs)
	{
		setRestServices(rs);
	}
	
	public void simulateAcquisitionsAtLevel(int level)
	{
		List<Punter> sponsors = restServices.getServices().getHome().getPunterDao().getPuntersForLevel(level);
		int cnt=0;
		for (Punter s : sponsors)
		{
			log.info("==> simulating acquisitions for : " + s.getContact() + " # : " + ++cnt + " of : " + sponsors.size());
			new RestSimulateAquisitions(restServices,s);
		}
	}
	
	public void sumulateUpgradesFromLevel(int level) throws PunterMgrException
	{
		while (level>4)
		{
			List<UpgradeStatus> uss = restServices.getServices().getHome().getPunterDao().getEligibleUpgrades(level);
			if (uss.isEmpty())
				return;
			log.info("Got : " + uss.size() + " potential upgrades");
			int cnt=0;
			for (UpgradeStatus us : uss)
			{
				Punter payee = restServices.getPunterMgr().getByContact(us.getPayeeContact());
				Punter punter = restServices.getPunterMgr().getByUUID(us.getId());
				log.info("==> simulating upgrade for : " + payee.getContact() + " # : " + ++cnt + " of : " + uss.size());	
				new RestSimulateUpgrade(restServices,payee,punter);
			}
			level--;
		}
	}
	
	public RestServices getRestServices() {
		return restServices;
	}

	public void setRestServices(RestServices restServices) {
		this.restServices = restServices;
	}

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("zen-service.xml");
		try
		{
			RestServices rs = (RestServices) context.getBean("restServices");
			RestSimulate rSim = new RestSimulate(rs);
		//	rs.getServices().getHome().getPunterDao().deletePuntersBetweenLevels(9,10);
			for (int i=0; i<4; i++)
			{
				log.info("#### simulating for level : " + i + "####");
				rSim.simulateAcquisitionsAtLevel(i);
				rSim.sumulateUpgradesFromLevel(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}

