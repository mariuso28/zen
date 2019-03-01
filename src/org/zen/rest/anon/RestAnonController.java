package org.zen.rest.anon;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zen.json.ProfileJson;
import org.zen.json.ResultJson;
import org.zen.rest.services.RestServices;

@RestController
@RequestMapping("/api/anon")
public interface RestAnonController
	

@RequestMapping(value = "/register")
// ResultJson contains message if success, message if fail
public ResultJson register(@RequestBody ProfileJson profile);

@RequestMapping(value = "/getSuccess")
// ResultJson contains message if success, message if fail
public ResultJson getSuccess()
	
@RequestMapping(value = "/getPunterDetails")
// ResultJson contains punterDetails if success, message if fail
public ResultJson getPunterDetails(@RequestParam("email") String email);
	
@RequestMapping(value = "/getModel")
// ResultJson contains model if success, message if fail
public ResultJson getModel();
	
	
}