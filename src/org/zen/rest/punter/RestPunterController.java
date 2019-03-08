package org.zen.rest.punter;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ChangePasswordJson;
import org.zen.json.ProfileJson;
import org.zen.json.ResultJson;

@RestController
@RequestMapping("/api/punter")
public interface RestPunterController
{
	@RequestMapping(value = "/initializeRegistration")
	// ResultJson contains new ProfileJson if success, message if fail
	public ResultJson initializeRegistration(OAuth2Authentication auth);
	
	@RequestMapping(value = "/storeRegistration")
	// ResultJson contains message if success, message and profile in progress ProfileJson if fail
	public ResultJson storeRegistration(OAuth2Authentication auth,@RequestBody ProfileJson profile);
	
	@RequestMapping(value = "/changePassword")
	// ResultJson contains message if success, message if fail
	public ResultJson changePassword(OAuth2Authentication auth,@RequestBody ChangePasswordJson changePassword);
	
	@RequestMapping(value = "/getPunter")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunter(OAuth2Authentication auth);
	
	@RequestMapping(value = "/updatePunter")
	// ResultJson contains message if success, message if fail
	public ResultJson updatePunter(OAuth2Authentication auth,@RequestBody ProfileJson profile);
}