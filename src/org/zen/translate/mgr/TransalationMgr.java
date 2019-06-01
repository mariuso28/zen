package org.zen.translate.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zen.services.Services;
import org.zen.translate.persistence.TranslationDao;

public class TransalationMgr {
	
	private static Logger log = Logger.getLogger(TransalationMgr.class);
	private TranslationDao translationDao;
	private Map<String,Map<String,String>> frequents = new HashMap<String,Map<String,String>>();
	private Map<String,String> currFrequent;
	private List<String> supportedIsoCodes = new ArrayList<String>();
	private String isoCode;
	
	public TransalationMgr(Services services)
	{
		setTranslationDao(services.getHome().getTranslationDao());
		String sic = services.getProp().getProperty("supportedIsoCodes","en;km");
		for (String c : sic.split(";"))
			supportedIsoCodes.add(c);
		importFrequents();
		changeIsoCode(supportedIsoCodes.get(0));
	}

	private void importFrequents() {
		for (String c : supportedIsoCodes)
		{
			if (c.equals("en"))
				continue;
			Map<String,String> hm = new HashMap<String,String>();
			frequents.put(c, hm);
			String xlate = translationDao.translate(" not found", c);
			hm.put(" not found", xlate);
			xlate = translationDao.translate("Could not ", c);
			hm.put("Could not ", xlate);
			xlate = translationDao.translate(" - contact support.", c);
			hm.put(" - contact support.", xlate);
			xlate = translationDao.translate("Hi ", c);
			hm.put("Hi ", xlate);
		}
	}
	
	public String xlate(String src)
	{
		if (isoCode.equals("en"))
			return src;
		String xlate = currFrequent.get(src);
		if (xlate!=null)
			return xlate;
		xlate = translationDao.translate(src,isoCode);
		if (xlate==null)
		{
			log.error("No transalation found for : " + src + " isoCode : " + isoCode);
			return src;
		}
		return xlate;
	}
	
	public Map<String,String> getLabels(String jsp) {
		Map<String,String> map = new HashMap<String,String>();
		if (jsp.equals("editProfile"))
		{
			map.put("myUserProfileLabel", xlate("My User Profile"));
			map.put("personalInfomationLabel",xlate("Personal Information"));
			map.put("usernameLabel",xlate("User Name"));
			map.put("zenSponsorLabel",xlate("Zen Sponsor"));
			map.put("fullNameLabel",xlate("Full Name"));
			map.put("passportIcLabel", xlate("Passport ID"));
			map.put("genderLabel", xlate("Gender"));
			map.put("maleLabel", xlate("Male"));
			map.put("femaleLabel", xlate("Female"));
			map.put("otherLabel", xlate("Other"));
			map.put("addressLabel", xlate("Address"));
			map.put("otherLabel", xlate("Other"));
			map.put("postCodeLabel", xlate("Postcode"));
			map.put("stateLabel", xlate("State"));
			map.put("countryLabel", xlate("Country"));
			map.put("phoneLabel", xlate("Phone"));
			map.put("emailLabel", xlate("Email"));
			map.put("yourPaymentMethodsLabel", xlate("Your Payment Methods :"));
			map.put("noLabel", xlate("Number"));
			map.put("methodLabel", xlate("Method"));
			map.put("pmCountryLabel", xlate("Country"));
			map.put("accountNumberLabel", xlate("Account Number"));
			map.put("deleteLabel", xlate("Delete"));
			map.put("addPaymentMethodLabel", xlate("Add Payment Method :"));
			map.put("saveLabel", xlate("Save"));
			map.put("cancelLabel", xlate("Cancel"));
			map.put("methodCountryLabel", xlate("Method - Country"));
			map.put("newUserRegistrationLabel", xlate("New User Registration"));
			map.put("sponsorInformationLabel", xlate("Sponsor Information"));
			map.put("zenSponsorUsernameLabel", xlate("Zen User Name"));
			map.put("newAgentInformationLabel", xlate("New Agent Information"));		
			map.put("zenUsernameLabel", xlate("Zen User Name"));
			map.put("passwordLabel", xlate("Password"));
			map.put("confirmPasswordLabel", xlate("Confrim Password"));
			map.put("generateLabel", xlate("Generate"));
			map.put("changePasswordLabel", xlate("Change Password")); 
			map.put("oldPasswordLabel", xlate("Old Password"));
			map.put("newPasswordLabel", xlate("New Password")); 
			map.put("confirmPasswordLabel", xlate("Confirm Password"));
			map.put("myDownlineLabel", xlate("My Down line"));
			map.put("agentInformationLabel", xlate("Agent Information"));
			map.put("searchLabel", xlate("Search"));
		}
		else
		if (jsp.equals("login"))
		{
			map.put("lostPasswordLabel", xlate("Lost Password?"));
			map.put("loginLabel", xlate("Login"));
			map.put("alert1",xlate("Please supply your Zen username so we can reset your password."));
			map.put("alert2",xlate("A new password for zen member : "));
			map.put("alert3",xlate(" will be sent to your email"));
		}
		else
		if (jsp.equals("paymentReceived") || jsp.equals("paymentSent"))
		{
			map.put("paymentReceivedLabel", xlate("Payment Received"));
			map.put("pendingLabel", xlate("Pending"));
			map.put("successfulLabel", xlate("Successful"));
			map.put("failedLabel", xlate("Failed"));
			for (int i=1; i<=3; i++)
			{
				map.put("idLabel" + i, xlate("Id"));
				map.put("fromLabel" + i, xlate("From"));
				map.put("descriptionLabel" + i, xlate("Description"));
				map.put("amountLabel" + i, xlate("Amount"));
				map.put("paymentDetailsLabel" + i, xlate("Payment Details"));
				map.put("statusLabel" + i, xlate("Status"));
				map.put("dateTimeLabel" + i, xlate("Date/Time"));
			}
			map.put("viewButton", xlate("VIEW"));
			map.put("queryButton", xlate("QUERY"));
		}
		return map;
	}
	
	public String getJspPrefix()
	{
		if (isoCode.equals("en"))
			return "";
		return isoCode + "/";
	}

	public void changeIsoCode(String ic) {
		setIsoCode(ic);
		currFrequent = frequents.get(ic);
	}

	public TranslationDao getTranslationDao() {
		return translationDao;
	}

	public void setTranslationDao(TranslationDao translationDao) {
		this.translationDao = translationDao;
	}

	public List<String> getSupportedIsoCodes() {
		return supportedIsoCodes;
	}

	public void setSupportedIsoCodes(List<String> supportedIsoCodes) {
		this.supportedIsoCodes = supportedIsoCodes;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public Map<String,String> getCurrFrequent() {
		return currFrequent;
	}

	public void setCurrFrequent(Map<String,String> currFrequent) {
		this.currFrequent = currFrequent;
	}

	
	
	
}
