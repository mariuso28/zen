package org.zen.web.anon;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.zen.services.Services;

@Controller
@RequestMapping("/web/anon")

@SessionAttributes({ "errMsg", "link" } )

public class WebAnonController {
	private static Logger log = Logger.getLogger(WebAnonController.class);
	@Autowired
	private Services services;
	
	@RequestMapping(value = "/changeLanguage", method = RequestMethod.GET)
	public Object changeLanguage(String isoCode,ModelMap model) {
			
		log.info("Received changeLanguage with isoCode : " + isoCode);
		
		services.getTxm().changeIsoCode(isoCode);
		
		Object link = model.get("link");
		if (link == null)
			return "login" ;
		return link;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logon(ModelMap model) {
			
		log.info("Received login");
			
		model.addAttribute("link","login");
		return "login";
	}
	
	
	@RequestMapping(value = "/goGeneology", method = RequestMethod.GET)
	public ModelAndView goGeneology(String contact,ModelMap model) {
			
		log.info("Received goGeneology");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("contact","");
		ModelAndView mv = new ModelAndView("geneology" ,"map",map);
		model.addAttribute("link",mv);
		return mv;
	}
	
	@RequestMapping(value = "/goGeneologyContact", method = RequestMethod.GET)
	public ModelAndView goGeneologyContact(String contact,ModelMap model) {
			
		log.info("Received goGeneologyContact");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("contact",contact);
		ModelAndView mv = new ModelAndView("geneology" ,"map",map);
		model.addAttribute("link",mv);
		return mv;
	}
	
	@RequestMapping(value = "/goNewRegistration", method = RequestMethod.GET)
	public String goNewRegistration(ModelMap model) {
			
		log.info("Received goNewRegistration");
		
		model.addAttribute("link","registration");
		return "registration" ;
	}
	
	@RequestMapping(value = "/goUpgrade", method = RequestMethod.GET)
	public String goUpgrade(ModelMap model) {
			
		log.info("Received goUpgrade");
		
		model.addAttribute("link","upgrade");
		return "upgrade" ;
	}
	
	@RequestMapping(value = "/goPaymentReceived", method = RequestMethod.GET)
	public String goPaymentReceived(ModelMap model) {
			
		log.info("Received goPaymentReceived");
		model.addAttribute("link","paymentReceived");
		return "paymentReceived";
	}
	
	@RequestMapping(value = "/goPaymentSent", method = RequestMethod.GET)
	public String goPaymentSent(ModelMap model) {
			
		log.info("Received goPaymentSent");
		model.addAttribute("link","paymentSent");
		return "paymentSent" ;
	}
	
	@RequestMapping(value = "/goAgentList", method = RequestMethod.GET)
	public String goAgentList(ModelMap model) {
			
		log.info("Received goAgentList");
		model.addAttribute("link","agentList");
		return "agentList";
	}
	
   
	@RequestMapping(value = "/goChangePassword", method = RequestMethod.GET)
	public String goChangePassword(ModelMap model) {
			
		log.info("Received goChangePassword");
		model.addAttribute("link","changePassword");	
		return "changePassword";
	}
	
	@RequestMapping(value = "/goEditProfile", method = RequestMethod.GET)
	public String goEditProfile(ModelMap model) {
			
		log.info("Received goEditProfile");
		model.addAttribute("link","editProfile");	
		return "editProfile";
	}
	
	
	@RequestMapping(value = "/goDashboard", method = RequestMethod.GET)
	public String getZenHome(ModelMap model) {
			
		log.info("Received goDashboard");
		model.addAttribute("link","dashboard");	
		return "dashboard";
	}
	
	@RequestMapping(value = "/goPaymentDetails", method = RequestMethod.GET)
	public ModelAndView goPaymentDetails(String paymentId,String memberType,ModelMap model) {
			
		log.info("Received goPaymentDetails with " + paymentId + " : " + memberType);
			
		Map<String,String> info = new HashMap<String,String>();
		info.put("paymentId",paymentId);
		info.put("memberType",memberType);
		ModelAndView mv = new ModelAndView("paymentDetails" ,"info",info);
		model.addAttribute("link",mv);
		return mv;
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
			
		return "accessDenied";
	}
}
