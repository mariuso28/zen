package org.zen.rest.anon;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/anon")
public class RestAnonController {
	@Autowired RestServices restServices;
	
	private static Logger log = Logger.getLogger(RestAnonController.class);
	
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
			result.success(restServices.getPunterDetails(email));
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
	public ResultJson getModel()
	{
		log.info("Received getModel");
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getModel());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
}