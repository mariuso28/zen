package org.zen.rest.punter;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ChangePasswordJson;
import org.zen.json.PunterProfileJson;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/punter")
public class RestPunterControllerImpl implements RestPunterController
{
	private static Logger log = Logger.getLogger(RestPunterControllerImpl.class);
	@Autowired
	private RestServices restServices;
	
	
	@RequestMapping(value = "/getRandomUsername")
	// ResultJson contains String username if success, message if fail
	public ResultJson getRandomUsername()
	{
		log.info("Received getRandomUsername");
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getRandomUsername());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/initializeRegistration")
	// ResultJson contains new ProfileJson if success, message if fail
	public ResultJson initializeRegistration(OAuth2Authentication auth)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received initializeRegistration for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			PunterProfileJson pj = new PunterProfileJson();
			pj.setSponsorContact(contact);				// all other fields are empty
			result.success(pj);
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/storeRegistration")
	// ResultJson contains nothing if success, message and profile in progress ProfileJson if fail
	public ResultJson storeRegistration(OAuth2Authentication auth,@RequestBody PunterProfileJson profile)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received storeRegistration for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.register(contact,profile);
			result.success("Successfully registered");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.setResult(profile);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/changePassword")
	// ResultJson contains message if success, message if fail
	public ResultJson changePassword(OAuth2Authentication auth,@RequestBody ChangePasswordJson changePassword)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received changePassword for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.changePassword(contact,changePassword);
			result.success("Successfully changed");
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/updatePunter")
	// ResultJson contains message if success, message if fail
	public ResultJson updatePunter(OAuth2Authentication auth,@RequestBody PunterProfileJson profile)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received updatePunter for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.updatePunterProfile(contact,profile);
			result.success("Successfully updated");
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getPunter")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunter(OAuth2Authentication auth)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received getPunter for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			PunterProfileJson punter = restServices.getPunterProfile(contact);
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
	
	@Override
	@RequestMapping(value = "/getDownstreamPunters")
	// ResultJson contains downstream punter profiles if success, message if fail
	public ResultJson getDownstreamPunters(OAuth2Authentication auth)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received getDownstreamPunters for : " + contact);
		
		ResultJson result = new ResultJson();
		
		try
		{
			List<PunterProfileJson> punters = restServices.getDownstreamPunters(contact);
			result.success(punters);
		}
		catch (Exception e)
		{
			result.fail("Error getting downstream members for : " + contact + " - contact support.");
		}
		return result;
	}
	 
	@Override
	@RequestMapping(value = "/getPaymentMethods")
	// ResultJson contains List<PunterPaymentMethodJson> if success, message if fail
	public ResultJson getPaymentMethods(OAuth2Authentication auth) {
		log.info("Received getPaymentMethods");
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getPunterPaymentMethods(contact));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/addPaymentMethod")
	// ResultJson contains nothing if success, message if fail
	public ResultJson addPaymentMethod(OAuth2Authentication auth,@RequestParam("id") String id,@RequestParam("accountNumber") String accountNumber) {
		log.info("Received addPaymentMethod with id : " + id + " accNum : " + accountNumber);
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			restServices.addPunterPaymentMethod(contact,id,accountNumber);
			result.success();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/deletePaymentMethod")
	// ResultJson contains nothing if success, message if fail
	public ResultJson deletePaymentMethod(OAuth2Authentication auth,@RequestParam("id") String id) {
		log.info("Received deletePaymentMethod with id : " + id );
		@SuppressWarnings("unused")
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			restServices.deletePunterPaymentMethod(id);
			result.success();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
}