package org.zen.web.anon;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.zen.services.Services;

@Controller
@RequestMapping("/web/anon")

@SessionAttributes({ "errMsg" } )

public class WebAnonController {
	private static Logger log = Logger.getLogger(WebAnonController.class);
	@Autowired
	private Services services;
	
	@RequestMapping(value = "/changeLanguage", method = RequestMethod.GET)
	public String changeLanguage(String isoCode) {
			
		log.info("Received changeLanguage");
		
		services.getTxm().changeIsoCode(isoCode);
		
		return services.getTxm().getJspPrefix() + "login" ;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logon() {
			
		log.info("Received login");
			
		return services.getTxm().getJspPrefix() + "login";
	}
	
	
	@RequestMapping(value = "/goGeneology", method = RequestMethod.GET)
	public ModelAndView goGeneology(String contact) {
			
		log.info("Received goGeneology");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("contact","");
		return new ModelAndView(services.getTxm().getJspPrefix() + "geneolog" ,"map",map);
	}
	
	@RequestMapping(value = "/goGeneologyContact", method = RequestMethod.GET)
	public ModelAndView goGeneologyContact(String contact) {
			
		log.info("Received goGeneologyContact");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("contact",contact);
		return new ModelAndView(services.getTxm().getJspPrefix() + "geneology","map",map);
	}
	
	@RequestMapping(value = "/goNewRegistration", method = RequestMethod.GET)
	public String goNewRegistration() {
			
		log.info("Received goNewRegistration");
			
		return services.getTxm().getJspPrefix() + "registration" ;
	}
	
	@RequestMapping(value = "/goUpgrade", method = RequestMethod.GET)
	public String goUpgrade() {
			
		log.info("Received goUpgrade");
			
		return services.getTxm().getJspPrefix() + "upgrade" ;
	}
	
	@RequestMapping(value = "/goPaymentReceived", method = RequestMethod.GET)
	public String goPaymentReceived() {
			
		log.info("Received goPaymentReceived");
			
		return services.getTxm().getJspPrefix() + "paymentReceived";
	}
	
	@RequestMapping(value = "/goPaymentSent", method = RequestMethod.GET)
	public String goPaymentSent() {
			
		log.info("Received goPaymentSent");
			
		return services.getTxm().getJspPrefix() + "paymentSent" ;
	}
	
	@RequestMapping(value = "/goAgentList", method = RequestMethod.GET)
	public String goAgentList() {
			
		log.info("Received goAgentList");
			
		return services.getTxm().getJspPrefix() + "agentList";
	}
	
   
	@RequestMapping(value = "/goChangePassword", method = RequestMethod.GET)
	public String goChangePassword() {
			
		log.info("Received goChangePassword");
			
		return services.getTxm().getJspPrefix() + "changePassword";
	}
	
	@RequestMapping(value = "/goEditProfile", method = RequestMethod.GET)
	public String goEditProfile() {
			
		log.info("Received goEditProfile");
			
		return services.getTxm().getJspPrefix() + "editProfile";
	}
	
	
	@RequestMapping(value = "/goDashboard", method = RequestMethod.GET)
	public String getZenHome() {
			
		log.info("Received goDashboard");
			
		return services.getTxm().getJspPrefix() + "dashboard";
	}
	
	@RequestMapping(value = "/goPaymentDetails", method = RequestMethod.GET)
	public ModelAndView goPaymentDetails(String paymentId,String memberType) {
			
		log.info("Received goPaymentDetails with " + paymentId + " : " + memberType);
			
		Map<String,String> info = new HashMap<String,String>();
		info.put("paymentId",paymentId);
		info.put("memberType",memberType);
		return new ModelAndView(services.getTxm().getJspPrefix() + "paymentDetails","info",info);
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
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied() {
			
		log.info("Received accessDenied");
			
		return services.getTxm().getJspPrefix() + "accessDenied";
	}
}
