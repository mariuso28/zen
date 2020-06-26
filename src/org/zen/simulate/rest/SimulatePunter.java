package org.zen.simulate.rest;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.rest.services.RestServices;
import org.zen.user.punter.Punter;

public class SimulatePunter {
	private static Logger log = Logger.getLogger(SimulatePunter.class);
	@Autowired
	private RestServices restServices;
	private Punter punter;
	
	public SimulatePunter(RestServices restServices,Punter punter)
	{
		setRestServices(restServices);
		setPunter(punter);
	}
	
	public long submitTransactionDetails()
	{
		try
		{
			GregorianCalendar gc = new GregorianCalendar();
			String transactionDetails = "Made payment ref : XXXXYYY"; 
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String date = sdf.format(gc.getTime());
			return restServices.submitTransactionDetails(punter.getContact(),null,date,transactionDetails);
		}
		catch (Exception e)
		{
			log.error("submitTransactionDetails",e);
			throw new RestSimulateException(e.getMessage());
		}
	}
	
	public RestServices getRestServices() {
		return restServices;
	}
	public void setRestServices(RestServices restServices) {
		this.restServices = restServices;
	}
	public Punter getPunter() {
		return punter;
	}
	public void setPunter(Punter punter) {
		this.punter = punter;
	}
	
	
	
	
}
