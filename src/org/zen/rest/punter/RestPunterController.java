package org.zen.rest.punter;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zen.json.ChangePasswordJson;
import org.zen.json.PunterProfileJson;
import org.zen.json.ResultJson;

@RestController
@RequestMapping("/api/punter")
public interface RestPunterController
{
	
	@RequestMapping(value = "/getRandomUsername")
	// ResultJson contains String username if success, message if fail
	public ResultJson getRandomUsername();
	
	@RequestMapping(value = "/initializeRegistration")
	// ResultJson contains new ProfileJson if success, message if fail
	public ResultJson initializeRegistration(OAuth2Authentication auth);
	
	@RequestMapping(value = "/storeRegistration")
	// ResultJson contains message if success, message and profile in progress ProfileJson if fail
	public ResultJson storeRegistration(OAuth2Authentication auth,@RequestBody PunterProfileJson profile);
	
	@RequestMapping(value = "/changePassword")
	// ResultJson contains message if success, message if fail
	public ResultJson changePassword(OAuth2Authentication auth,@RequestBody ChangePasswordJson changePassword);
	
	@RequestMapping(value = "/getPunter")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunter(OAuth2Authentication auth);
	
	@RequestMapping(value = "/getPunterByContact")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunterByContact(OAuth2Authentication auth,@RequestParam("contact") String contact);
	
	@RequestMapping(value = "/updatePunter")
	// ResultJson contains message if success, message if fail
	public ResultJson updatePunter(OAuth2Authentication auth,@RequestBody PunterProfileJson profile);
	
	@RequestMapping(value = "/getDownstreamPunters")
	// ResultJson contains downstream punter profiles if success, message if fail
	public ResultJson getDownstreamPunters(OAuth2Authentication auth);
	
	@RequestMapping(value = "/getPaymentMethods")
	// ResultJson contains List<PaymentMethodJson> if success, message if fail
	public ResultJson getPaymentMethods(OAuth2Authentication auth);
	
	@RequestMapping(value = "/addPaymentMethod")
	// ResultJson contains nothing if success, message if fail
	public ResultJson addPaymentMethod(OAuth2Authentication auth,@RequestParam("id") String id,@RequestParam("accountNumber") String accountNumber);
	
	@RequestMapping(value = "/deletePaymentMethod")
	// ResultJson contains nothing if success, message if fail
	public ResultJson deletePaymentMethod(OAuth2Authentication auth,@RequestParam("id") String id);
	
	@RequestMapping(value = "/getUpgradeRequest")
	// ResultJson contains UpgradeJson if success, message if fail
	public ResultJson getUpgradeRequest(OAuth2Authentication auth);
	
	@RequestMapping(value = "/submitTransactionDetails")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson submitTransactionDetails(OAuth2Authentication auth,@RequestParam("uploadfile") MultipartFile uploadfile,
			@RequestParam("transactionDate") String transactionDate,@RequestParam("transactionDetails") String transactionDetails);

	@RequestMapping(value = "/getPaymentsReceived")
	// PkfzResultJson contains List<XactionJson> if success, message if fail
	public ResultJson getPaymentsReceived(OAuth2Authentication auth, String paymentStatus, long offset, long limit);
	
	@RequestMapping(value = "/getPaymentDetails")
	// PkfzResultJson contains XactionJson if success, message if fail
	public ResultJson getPaymentDetails(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId,@RequestParam("memberType") String memberType);
	
	@RequestMapping(value = "/approvePayment")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson approvePayment(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId);
	
	@RequestMapping(value = "/rejectPayment")
	// PkfzResultJson contains nothing if success, message if fail
	public ResultJson rejectPayment(OAuth2Authentication auth,@RequestParam("paymentId") String paymentId);
	
	@RequestMapping(value = "/getPaymentsSent")
	// PkfzResultJson contains List<XactionJson> if success, message if fail
	public ResultJson getPaymentsSent(OAuth2Authentication auth, String paymentStatus, long offset, long limit);
	
}