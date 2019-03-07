package org.zen.web.anon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/web/anon")

@SessionAttributes({ "errMsg" } )

public class WebAnonController {
	private static Logger log = Logger.getLogger(WebAnonController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logon() {
			
		log.info("Received login");
			
		return "login";
	}
	
	@RequestMapping(value = "/goEditProfile", method = RequestMethod.GET)
	public String goEditProfile() {
			
		log.info("Received goEditProfile");
			
		return "editProfile";
	}
	
	
	@RequestMapping(value = "/goDashboard", method = RequestMethod.GET)
	public String getZenHome() {
			
		log.info("Received goDashboard");
			
		return "dashboard";
	}
	
	@RequestMapping(value = "/getSuccess", method = RequestMethod.GET)
	public String getSuccess() {
			
		log.info("Received getSuccess");
			
		return "success";
	}
	
	@RequestMapping(value = "/getModel", method = RequestMethod.GET)
	public String getTree() {
			
		log.info("Received getModel");
			
		return "treeAjax";
	}
}
