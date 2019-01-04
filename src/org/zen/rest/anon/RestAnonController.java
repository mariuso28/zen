package org.zen.rest.anon;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value = "/getPunterTree")
	// ResultJson contains tree if success, message if fail
	public ResultJson getPunterTree()
	{
		log.info("Received getPunterTree");
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getPunters());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	/*	
	@RequestMapping(value = "/getPunterTree")
	// root contains tree if success, message if fail
	public Object getPunterTree()
	{
		log.info("Received getPunterTree");
		try
		{
			PunterJson root = restServices.getPunters();
			return root;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			return null;
		}
	}
	*/
}