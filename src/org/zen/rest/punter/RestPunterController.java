package org.zen.rest.punter;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ProfileJson;
import org.zen.json.ResultJson;

@RestController
@RequestMapping("/api/punter")
public interface RestPunterController
{
	@RequestMapping(value = "/getPunter")
	// ResultJson contains punter's profile if success, message if fail
	public ResultJson getPunter(OAuth2Authentication auth);
	
}