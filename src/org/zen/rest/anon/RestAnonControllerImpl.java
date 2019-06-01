package org.zen.rest.anon;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.PunterProfileJson;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/anon")
public class RestAnonControllerImpl implements RestAnonController{
	@Autowired RestServices restServices;
	
	
	private static Logger log = Logger.getLogger(RestAnonControllerImpl.class);
	
	@RequestMapping(value = "/register")
	// ResultJson contains message if success, message if fail
	public ResultJson register(@RequestBody PunterProfileJson profile)
	{
		log.info("Received register with profile : " + profile);
		
		ResultJson result = new ResultJson();
		try
		{
//			restServices.registerPunter(profile);
			result.success("Zen recriuter : " + profile.getContact() + " Successfully registered.");
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/resetPassword")
	// ResultJson contains message if success, message if fail
	public ResultJson resetPassword(@RequestParam("username") String username)
	{
		log.info("Received resetPassword for : " + username);
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.resetPassword(username));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/getSuccess")
	// ResultJson contains message if success, message if fail
	public ResultJson getSuccess()
	{
		log.info("Received getSuccess");
		
		ResultJson result = new ResultJson();
		result.success("SUCCESS");
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getCountries")
	// ResultJson contains List<String> if success, message if fail
	public ResultJson getCountries() {
		log.info("Received getCountries");
		ResultJson result = new ResultJson();
		try
		{
			String isoCode = restServices.getServices().getTxm().getIsoCode();
			result.success(restServices.getServices().getHome().getCountryDao().getCountryList(isoCode));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getAvailablePaymentMethods")
	// ResultJson contains List<PunterPaymentMethodJson> if success, message if fail
	public ResultJson getAvailablePaymentMethods() {
		log.info("Received getAvailablePaymentMethods");
		
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getServices().getHome().getPaymentDao().getAvailablePaymentMethods());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/getLabels")
	// ResultJson contains Map<String,String> if success, message if fail
	public ResultJson getLabels(@RequestParam("jsp") String jsp)
	{
		log.info("Received getLabels");
		
		ResultJson result = new ResultJson();
		try
		{
			Map<String,String> map = restServices.getServices().getTxm().getLabels(jsp);
			log.info("Got labels : " + map);
			result.success(map);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
}