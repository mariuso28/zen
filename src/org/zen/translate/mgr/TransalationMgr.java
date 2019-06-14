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
	private TranslationLabels jspMapKh;
	private TranslationLabels jspMapId;
	private TranslationLabels jspMapEn;
	private Map<String,Map<String,String>> jspMap;
	
	public TransalationMgr(Services services)
	{
		setTranslationDao(services.getHome().getTranslationDao());
		services.getHome().getCountryDao().initializeCountryLists();
		String sic = services.getProp().getProperty("supportedIsoCodes","en;km;id");
		for (String c : sic.split(";"))
			supportedIsoCodes.add(c);
		importFrequents();
		setIsoCode("en");
		jspMapEn = new TranslationLabels(this);	
		setIsoCode("km");
		jspMapKh = new TranslationLabels(this);	
		setIsoCode("id");
		jspMapId = new TranslationLabels(this);	
		changeIsoCode(supportedIsoCodes.get(0));		// default
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
	
	public Map<String,String> getLabels(String jsp)
	{
		return jspMap.get(jsp);
	}
	
	/*
	public Map<String,String> getLabels(String jsp) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("sbDashboardLabel", xlate("Dashboard"));
		map.put("sbMyProfileLabel", xlate("My Profile"));
		map.put("sbEditProfileLabel", xlate("Edit Profile"));
		map.put("sbChangePasswordLabel", xlate("Change Password")); 
		map.put("sbAgentsLabel", xlate("Agents")); 
		map.put("sbAgentListLabel", xlate("Agent List")); 
		map.put("sbUpgradeLabel", xlate("Upgrade"));
		map.put("sbNewRegistrationLabel", xlate("New Registration"));
		map.put("sbPaymentLabel", xlate("Payment")); 
	    map.put("sbPaymentReceivedLabel", xlate("Payment Received"));
		map.put("sbPaymentSentLabel", xlate("Payment Sent")); 
		map.put("sbLogoutLabel", xlate("Logout")); 
		
		map.put("dbPasswordLabel", xlate("Password"));
		map.put("dbPaymentReceivedLabel", xlate("Payment Received"));
		map.put("dbPaymentSentLabel", xlate("Payment Sent")); 
		map.put("dbUpgradeLabel", xlate("Upgrade"));
		map.put("dbAgentListLabel", xlate("Agent List")); 
		map.put("dbNewRegistrationLabel", xlate("New Registration"));
		
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
		if (jsp.equals("paymentReceived") || jsp.equals("paymentSent") || jsp.equals("paymentDetails"))
		{
			map.put("paymentReceivedLabel", xlate("Payment Received"));
			map.put("paymentSentLabel", xlate("Payment Sent"));
			map.put("paymentDetailsLabel", xlate("Payment Details"));
			map.put("pendingLabel", xlate("Pending"));
			map.put("successfulLabel", xlate("Successful"));
			map.put("failedLabel", xlate("Failed"));
			for (int i=1; i<=3; i++)
			{
				map.put("idLabel" + i, xlate("Id"));
				map.put("fromLabel" + i, xlate("From"));
				map.put("toLabel" + i, xlate("To"));
				map.put("queryLabel" + i, xlate("Query"));
				map.put("descriptionLabel" + i, xlate("Description"));
				map.put("amountLabel" + i, xlate("Amount"));
				map.put("paymentDetailsLabel" + i, xlate("Payment Details"));
				map.put("statusLabel" + i, xlate("Status"));
				map.put("dateTimeLabel" + i, xlate("Date/Time"));
			}
			map.put("transactionInformationLabel",xlate("Transaction Information")); 
			map.put("transactionDateLabel",xlate("Transaction Date")); 
			map.put("transactionDetailsLabel",xlate("Transaction Details")); 
			map.put("transactionSlipLabel",xlate("Transaction Slip")); 
			map.put("paymentToLabel",xlate("Payment To")); 
			map.put("paymentFomLabel",xlate("Payment From")); 
			map.put("dateInFormatLabel",xlate("Date in (mm-dd-yyyy) format")); 
			map.put("viewButton", xlate("View"));
			map.put("queryButton", xlate("Query"));
			map.put("approveButton", xlate("Approve"));
			map.put("rejectButton", xlate("Reject"));
		}
		else
		if (jsp.equals("upgrade"))
		{
			map.put("upgradeLabel",xlate("Upgrade"));
			map.put("congratulationsLabel",xlate("Contratulations!"));
			map.put("uplineInformationLabel",xlate("Up Line Information"));
			map.put("fullNameLabel",xlate("Full Name"));
			map.put("fullNameLabel2",xlate("Full Name"));
			map.put("zenUsernameLabel", xlate("Zen User Name"));
			map.put("zenUsernameLabel2", xlate("Zen User Name"));
			map.put("rankLabel", xlate("Rank"));
			map.put("makePaymentUplineLabel", xlate("Make Payment to Up Line"));
			map.put("methodLabel", xlate("Method"));
			map.put("pmCountryLabel", xlate("Country"));
			map.put("accountNumberLabel", xlate("Account Number"));
			map.put("submitPaymentFormLabel", xlate("Submit Payment Form"));
			map.put("submissionInformationLabel","Submission Information");
			map.put("titleLabel","Title");
			map.put("transactionDateLabel",xlate("Transaction Date")); 
			map.put("transactionDetailsLabel",xlate("Transaction Details")); 
			map.put("dateInFormatLabel",xlate("Date in (mm-dd-yyyy) format")); 
			map.put("transactionDetails",xlate("Copy and Paste Transaction Details"));				// not a label contents of text area
			map.put("uploadTransactionSlipLabel",xlate("Upload Transaction Slip"));
			map.put("submitButton", xlate("Submit"));
			map.put("cancelButton", xlate("Cancel"));
			map.put("checkPaymentEmailLabel", xlate("Check Payment Email")); 
			map.put("cpLabel1", xlate("Check your email for the payment confirmation from us.")); 
			map.put("cpLabel2", xlate("If you do not receive the email confirmation within 24 hours, please check your SPAM folder.")); 
			map.put("cpLabel3", xlate("Write to ")); 
			map.put("cpLabel4", xlate(" for further assistance.")); 
			map.put("upLabel1",xlate("You have successfully registered with Zen. Make you registration payment today to proceed to "));
			map.put("upLabel2",xlate(" and start making money!"));
			map.put("upLabel3",xlate("Zen Activate Member At Rank "));
			map.put("upLabel4",xlate("Activation Fee: "));
			map.put("upLabel5",xlate("You are now at Rank "));
			map.put("upLabel6",xlate(" Upgrade today to "));
			map.put("upLabel7",xlate(" to start earning more!"));
			map.put("upLabel8",xlate("Zen Upgrade Member To Rank "));
			map.put("upLabel9",xlate("Upgrade Fee: "));
		}
		else
		if (jsp.equals("dashboard"))
		{
			map.put("latestLabel",xlate("Latest"));
		}
		return map;
	}
	*/
	
	public String getJspPrefix()
	{
		if (isoCode.equals("en"))
			return "";
		return isoCode + "/";
	}

	public void changeIsoCode(String ic) {
		setIsoCode(ic);
		currFrequent = frequents.get(ic);
		if (ic.equals("km"))
			jspMap = jspMapKh.getJspMap();
		else
		if (ic.equals("id"))
			jspMap = jspMapId.getJspMap();
		else
			jspMap = jspMapEn.getJspMap();
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

	public TranslationLabels getJspMapId() {
		return jspMapId;
	}

	public void setJspMapId(TranslationLabels jspMapId) {
		this.jspMapId = jspMapId;
	}

	
	
	
}
