package org.zen.rest.auth;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.zen.json.CredentialsJson;
import org.zen.json.ResultJson;
import org.zen.json.StatusJson;
import org.zen.services.Services;
import org.zen.user.BaseUser;

@RestController
@RequestMapping("/api/a")
public class RestAuthController {

	private static final Logger log = Logger.getLogger(RestAuthController.class);

	@Autowired
	private Services services;
	
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/authorize1", method=RequestMethod.POST)
	public ResultJson authorize(@RequestBody() CredentialsJson credentials)
	{
		log.info("Got authorize1");
		
		ResultJson rj = authorize(credentials.getUsername(),credentials.getPassword());
		if (!rj.getStatus().equals(StatusJson.OK))
			return rj;
		
		Authorization auth = new Authorization();
		auth.setBody((LinkedHashMap<String, String>) rj.getResult());
		try
		{
			BaseUser baseUser = services.getHome().getBaseUserDao().getByContact(credentials.getUsername());
			auth.setRole(baseUser.getRole());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			rj.fail(e.getMessage());
			return rj;
		}
		rj.setResult(auth);
		return rj;
	}
	
	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	public ResultJson authorize(
			@RequestParam("username") String username,
			@RequestParam("password") String password
			){
		
		log.info("Attempting to authorize : " + username);
		
		ResultJson result = new ResultJson();
		String path;
		String tomcatPort = Services.getProp().getProperty("tomcatPort","8080").trim();
		path = "http://localhost:" + tomcatPort + "/zen/zx4/oauth/token";
		try
		{
			BaseUser baseUser = services.getHome().getBaseUserDao().getByContact(username);
			log.info("Got baseuser : " + baseUser.getContact());
		}
		catch (Exception e)
		{
			log.error("Error finding User: " + username + " not found - " + e.getMessage());
			result.fail("username or password incorrect");
			return result;
		}
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("grant_type", "password");
			map.add("client_id", "barClientIdPassword");
			map.add("client_secret", "secret");
			map.add("username", username);
			map.add("password", password);
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<Object> responseEntity = restTemplate.exchange(path, HttpMethod.POST, entity, Object.class);
			result.success(responseEntity.getBody());
		}
		catch(HttpClientErrorException e){
			log.error(e.getMessage(),e);
			result.fail("username or password incorrect");
			/*
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)){
				result.fail("username or password incorrect");
			}
			else
				result.fail(e.getMessage());
				*/
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			result.fail("Unexpected error on authorize - contact support");
		}
		
		return result;
	}

	@RequestMapping(value="/refreshToken", method=RequestMethod.POST)
	public ResultJson refreshToken(
			@RequestParam("refreshToken") String refreshToken,
			HttpServletRequest request
			){
		ResultJson result = new ResultJson();
		String path;
		String tomcatPort = Services.getProp().getProperty("tomcatPort","8080").trim();
		path = "http://localhost:" + tomcatPort + "/zen/zx4/oauth/token";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("grant_type", "refresh_token");
			map.add("refresh_token", refreshToken);
			map.add("client_id", "barClientIdPassword");
			map.add("client_secret", "secret");
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<Object> responseEntity = restTemplate.exchange(path, HttpMethod.POST, entity, Object.class);
			result.success(responseEntity.getBody());
		}
		catch(Exception e){
			e.printStackTrace();
			result.fail("Bad credentials");
		}
		return result;
	}

	@RequestMapping(value="/revokeRefreshToken", method=RequestMethod.POST)
	public ResultJson revokeRefreshToken(
			@RequestParam("refreshToken") String refreshToken
			){
		ResultJson result = new ResultJson();
		((JdbcTokenStore) tokenStore).removeAccessTokenUsingRefreshToken(refreshToken);
		((JdbcTokenStore) tokenStore).removeRefreshToken(refreshToken);
		result.success();
		return result;
	}
}
