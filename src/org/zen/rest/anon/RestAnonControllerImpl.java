package org.zen.rest.anon;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ProfileJson;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/anon")
public class RestAnonControllerImpl implements RestAnonController{
	@Autowired RestServices restServices;
	
	private static Logger log = Logger.getLogger(RestAnonControllerImpl.class);
	
	@RequestMapping(value = "/register")
	// ResultJson contains message if success, message if fail
	public ResultJson register(@RequestBody ProfileJson profile)
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
	
	@RequestMapping(value = "/getSuccess")
	// ResultJson contains message if success, message if fail
	public ResultJson getSuccess()
	{
		log.info("Received getSuccess");
		
		ResultJson result = new ResultJson();
		result.success("SUCCESS");
		return result;
	}
	
	@RequestMapping(value = "/getPunterDetails")
	// ResultJson contains punterDetails if success, message if fail
	public ResultJson getPunterDetails(@RequestParam("email") String email)
	{
		log.info("Received getPunterDetails");
		ResultJson result = new ResultJson();
		try
		{
//			result.success(restServices.getPunterDetails(email));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/getModel")
	// ResultJson contains model if success, message if fail
	public ResultJson getModel(@RequestParam("contact") String contact)
	{
		log.info("Received getModel with : " + contact);
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getModel(contact));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/getCountries")
	// ResultJson contains List<CountryJson> if success, message if fail
	public ResultJson getCountries() {
		log.info("Received getCountries");
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getServices().getHome().getCountryDao().getCountries());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
}