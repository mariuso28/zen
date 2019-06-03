package org.zen.rest.anon;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.PunterProfileJson;
import org.zen.json.ResultJson;

@RestController
@RequestMapping("/api/anon")
public interface RestAnonController
{
	@RequestMapping(value = "/register")
	// ResultJson contains message if success, message if fail
	public ResultJson register(@RequestBody PunterProfileJson profile);
	
	@RequestMapping(value = "/getSuccess")
	// ResultJson contains message if success, message if fail
	public ResultJson getSuccess();	
				
	@RequestMapping(value = "/getCountries")
	// ResultJson contains List<CountryDisplayJson> if success, message if fail
	public ResultJson getCountries();	
	
	@RequestMapping(value = "/getAvailablePaymentMethods")
	// ResultJson contains List<PunterPaymentMethodJson> if success, message if fail
	public ResultJson getAvailablePaymentMethods();
	
	@RequestMapping(value = "/resetPassword")
	// ResultJson contains message if success, message if fail
	public ResultJson resetPassword(@RequestParam("username") String username);
	
	@RequestMapping(value = "/getLabels")
	// ResultJson contains Map<String,String> if success, message if fail
	public ResultJson getLabels(@RequestParam("jsp") String jsp);
}