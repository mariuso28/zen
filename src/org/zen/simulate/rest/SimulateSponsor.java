package org.zen.simulate.rest;

import java.util.Random;

import org.apache.log4j.Logger;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterProfileJson;
import org.zen.model.ZenModelFake;
import org.zen.model.ZenModelOriginal;
import org.zen.rest.services.RestServices;
import org.zen.rest.services.RestServicesException;
import org.zen.user.faker.FakeContact;
import org.zen.user.punter.Punter;

public class SimulateSponsor {
	private static Logger log = Logger.getLogger(SimulateSponsor.class);
	private RestServices restServices;
	private Punter sponsor;
	private ZenModelFake zenModelFake = new ZenModelFake();
	
	public SimulateSponsor(RestServices restServices,Punter sponsor)
	{
		setRestServices(restServices);
		setSponsor(sponsor);
	}

	public PunterProfileJson registerNewPunter()
	{
		Random r = new Random();
		try
		{
			PunterProfileJson np;
			while (true)
			{
				try
				{
					FakeContact fc = restServices.getPunterMgr().getFakeContact(sponsor.getLevel()<ZenModelOriginal.SYSTEMLEVELS);
					String contact = fc.getContact() + r.nextInt(1000);
					fc.setContact(contact);
					np = zenModelFake.createProfile(sponsor,fc);
					restServices.register(sponsor.getContact(),np);
					break;
				}
				catch (RestServicesException e)
				{
					log.warn("Could not create punter : " + e.getMessage() + " retrying..");
				}
			}
			String acNum = "";
			for (int i=0; i<8; i++)
				acNum += r.nextInt(10);
			PaymentMethodJson pm = restServices.getServices().getHome().getPaymentDao().getPaymentMethodByMethod("WeChat Pay");
			restServices.addPunterPaymentMethod(np.getContact(),Integer.toString(pm.getId()),acNum);
			return np;
		}
		catch (Exception e)
		{
			log.error("registerNewPunter",e);
			throw new RestSimulateException(e.getMessage());
		}
	}
	
	public void approvePaymentDetails(long id)
	{
		try
		{
			restServices.approvePayment(sponsor.getContact(), Long.toString(id));
		}
		catch (Exception e)
		{
			log.error("approvePaymentDetails",e);
			throw new RestSimulateException(e.getMessage());
		}
	}
	
	
	public RestServices getRestServices() {
		return restServices;
	}

	public void setRestServices(RestServices restServices) {
		this.restServices = restServices;
	}

	public Punter getSponsor() {
		return sponsor;
	}

	public void setSponsor(Punter sponsor) {
		this.sponsor = sponsor;
	}

	
	
}
