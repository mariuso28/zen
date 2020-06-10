package org.zen.rest.punter;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zen.json.ChangePasswordJson;
import org.zen.json.PunterJson;
import org.zen.json.PunterProfileJson;
import org.zen.json.ResultJson;
import org.zen.json.UpgradeJson;
import org.zen.json.XactionJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/punter")
public class RestPunterControllerImpl implements RestPunterController
{
	private static Logger log = Logger.getLogger(RestPunterControllerImpl.class);
	@Autowired
	private RestServices restServices;
	
	
	@Override
	@RequestMapping(value = "/getNotifications")
	// ResultJson contains List<NotificationJson> if success, message if fail
	public ResultJson getNotifications(OAuth2Authentication auth) {
		log.info("Received getNotifications");
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.getNotifications(contact));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/getRandomUsername")
	// ResultJson contains String username if success, message if fail
	public ResultJson getRandomUsername()
	{
//		log.info("Received getRandomUsername");
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
			pj.setPhone("+855");
			pj.setCountry("116");
			pj.setGender("Female");
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
			PunterProfileJson punter = restServices.getPunterProfileForCaller(contact,contact);
			if (punter!=null)
				result.success(punter);
			else
				result.fail(restServices.x("Error getting Zen member : ") + contact + restServices.x(" - contact support."));
		}
		catch (Exception e)
		{
			result.fail(restServices.x("Error getting Zen member : ") + contact + restServices.x(" - contact support."));
		}
		return result;
	}
	
	@RequestMapping(value = "/getPunterByContact")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunterByContact(OAuth2Authentication auth,@RequestParam("contact") String contact)
	{
		String caller = ((User) auth.getPrincipal()).getUsername();		
		log.info("Received getPunterByContact for : " + contact);	
		ResultJson result = new ResultJson();
		
		try
		{
			PunterProfileJson punter = restServices.getPunterProfileForCaller(caller,contact);
			if (punter!=null)
				result.success(punter);
			else
				result.fail(restServices.x("Error getting Zen member : ") + contact + restServices.x(" - contact support."));
		}
		catch (Exception e)
		{
			result.fail(restServices.x("Error getting Zen member : ") + contact + restServices.x(" - contact support."));
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/sendPaymentQuery")
	// ResultJson contains nothing if success, message if fail
	public ResultJson sendPaymentQuery(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId)
	{
		String contact = ((User) auth.getPrincipal()).getUsername();
		log.info("Received getDownstreamPunters for : " + contact);		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.sendPaymentQuery(contact,paymentId);
			result.success();
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
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
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getModel")
	// contains tree json if success, message if fail
	public List<PunterJson> getModel(OAuth2Authentication auth,@RequestParam Map<String,String> allRequestParams)
	{
		log.info("Received getModel : " + allRequestParams);
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		String parentId = allRequestParams.get("parentId");
		
		try
		{	
			if (parentId == null)
				return restServices.getRoot(contact);
		
			return restServices.getPunters(parentId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	@RequestMapping(value = "/searchModelByContact")
	// ResultJson contains list contact and parentlist in reverse order if success, message if fail
	public ResultJson searchModelByContact(OAuth2Authentication auth,@RequestParam String searchTerm)
	{
		log.info("Received searchModelByContact with : " + searchTerm);
	//	String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			result.success(restServices.searchModelByContact(searchTerm));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
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
	
	@Override
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
	
	@Override
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
	
	@Override
	@RequestMapping(value = "/getUpgradeRequest")
	// ResultJson contains UpgradeJson if success, message if fail
	public ResultJson getUpgradeRequest(OAuth2Authentication auth)
	{
		log.info("Received getUpgradeRequest");
		String contact = ((User) auth.getPrincipal()).getUsername();
		
		ResultJson result = new ResultJson();
		try
		{
			UpgradeJson uj = restServices.getUpgradeRequest(contact);
			result.success(uj);
		}
		catch (Exception e)
		{
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/submitTransactionDetails")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson submitTransactionDetails(OAuth2Authentication auth,@RequestParam("uploadfile") MultipartFile uploadfile,
			@RequestParam("transactionDate") String transactionDate,@RequestParam("transactionDetails") String transactionDetails)
	{
		log.info("Received submitTransactionDetails");
		log.info("Upload File : " + uploadfile.getName() + " date : " + transactionDate + " details : " + transactionDetails);
		String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.submitTransactionDetails(contact,uploadfile,transactionDate,transactionDetails);
			result.success();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getPaymentsReceived")
	// PkfzResultJson contains List<XactionJson> if success, message if fail
	public ResultJson getPaymentsReceived(OAuth2Authentication auth,@RequestParam("paymentStatus") String paymentStatus,
									@RequestParam("offset") long offset, @RequestParam("limit") long limit)
	{
		log.info("Received getPaymentsReceived");
		String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			List<XactionJson> xjs = restServices.getXtransactionsForMember("payee",contact,paymentStatus,offset,limit);
			result.success(xjs);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getPaymentDetails")
	// PkfzResultJson contains XactionJson if success, message if fail
	public ResultJson getPaymentDetails(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId
										,@RequestParam("memberType") String memberType)
	{
		log.info("Received getPaymentDetails with " + paymentId + " : " + memberType);
	//	String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			XactionJson xj = restServices.getXtransactionById(paymentId,memberType);
			result.success(xj);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/approvePayment")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson approvePayment(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId)
	{
		log.info("Received approvePayment with " + paymentId);
		String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.approvePayment(contact,paymentId);
			result.success();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/rejectPayment")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson rejectPayment(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId)
	{
		log.info("Received rejectPayment with " + paymentId);
		String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			restServices.rejectPayment(contact,paymentId);
			result.success();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
	
	@Override
	@RequestMapping(value = "/getPaymentsSent")
	// PkfzResultJson contains List<XactionJson> if success, message if fail
	public ResultJson getPaymentsSent(OAuth2Authentication auth,@RequestParam("paymentStatus") String paymentStatus,
									@RequestParam("offset") long offset, @RequestParam("limit") long limit)
	{
		log.info("Received getPaymentsSent");
		String contact = ((User) auth.getPrincipal()).getUsername();		
		ResultJson result = new ResultJson();
		
		try
		{
			List<XactionJson> xjs = restServices.getXtransactionsForMember("payer",contact,paymentStatus,offset,limit);
			result.success(xjs);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			result.fail(e.getMessage());
		}
		return result;
	}
}