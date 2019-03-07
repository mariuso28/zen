package org.zen.rest.punter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ProfileJson;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/punter")
public class RestPunterControllerImpl implements RestPunterController
{
	private static Logger log = Logger.getLogger(RestPunterControllerImpl.class);
	@Autowired
	private RestServices restServices;
	
	@RequestMapping(value = "/getPunter")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunter(OAuth2Authentication auth)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received getPunter for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			ProfileJson punter = restServices.getPunterProfile(contact);
			if (punter!=null)
				result.success(punter);
			else
				result.fail("Error getting Zen member : " + contact + " - contact support.");
		}
		catch (Exception e)
		{
			result.fail("Error getting Zen member : " + contact + " - contact support.");
		}
		return result;
	}
	

}