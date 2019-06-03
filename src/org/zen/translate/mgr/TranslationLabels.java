package org.zen.translate.mgr;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class TranslationLabels {

	private static Logger log = Logger.getLogger(TransalationMgr.class);
	
	private Map<String,Map<String,String>> jspMap;
	private TransalationMgr tm;
	
	public TranslationLabels(TransalationMgr tm)
	{
		setTm(tm);
		jspMap = new HashMap<String,Map<String,String>>();
		setupJspMaps();
	}
	
	private String xlate(String src)
	{
		if (tm.getIsoCode().equals("en"))
			return src;
		
		String xlate = tm.getTranslationDao().translate(src,tm.getIsoCode());
		if (xlate==null)
		{
			log.error("No transalation found for : " + src + " isoCode : " + tm.getIsoCode());
			return src;
		}
		return xlate;
	}
	
	private void setupJspMaps()
	{
		jspMap.put("login", setupLoginLabels());
		jspMap.put("dashboard", setupDashboardLabels());
		jspMap.put("changePassword", setupPasswordLabels());
		jspMap.put("editProfile",setupEditProfileLabels());
		jspMap.put("registration",setupRegistrationLabels());
		jspMap.put("paymentSent",setupPaymentSentLabels());
		jspMap.put("paymentReceived",setupPaymentReceivedLabels());
		jspMap.put("paymentDetails",setupPaymentDetailsLabels());
		jspMap.put("upgrade",setupUpgradeLabels());
		jspMap.put("geneology",setupGeneologyLabels());
	}
	
	private Map<String,String> setupLoginLabels() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("lostPasswordLabel", xlate("Lost Password?"));
		map.put("loginLabel", xlate("Login"));
		map.put("alert1",xlate("Please supply your Zen username so we can reset your password."));
		map.put("alert2",xlate("A new password for zen member : "));
		map.put("alert3",xlate(" will be sent to your email"));
		return map;
	}
	
	private Map<String,String> setupDashboardLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("latestLabel",xlate("Latest"));
		return map;
	}
	
	private Map<String,String> setupUpgradeLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
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
		return map;
	}
	
	private Map<String,String> setupPasswordLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("myUserProfileLabel", xlate("My User Profile"));
		map.put("changePasswordLabel", xlate("Change Password")); 
		map.put("oldPasswordLabel", xlate("Old Password"));
		map.put("newPasswordLabel", xlate("New Password")); 
		map.put("confirmPasswordLabel", xlate("Confirm Password"));
		map.put("saveLabel", xlate("Save"));
		map.put("cancelLabel", xlate("Cancel"));
		return map;
	}
	
	private Map<String,String> setupEditProfileLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
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
		map.put("methodCountryLabel", xlate("Method - Country"));
		map.put("addPaymentMethodLabel", xlate("Add Payment Method :"));
		map.put("saveLabel", xlate("Save"));
		map.put("cancelLabel", xlate("Cancel"));
		return map;
	}
	
	private Map<String,String> setupRegistrationLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("newUserRegistrationLabel", xlate("New User Registration"));
		map.put("sponsorInformationLabel", xlate("Sponsor Information"));
		map.put("zenSponsorUsernameLabel", xlate("Zen User Name"));
		map.put("newAgentInformationLabel", xlate("New Agent Information"));		
		map.put("zenUsernameLabel", xlate("Zen User Name"));
		map.put("passwordLabel", xlate("Password"));
		map.put("confirmPasswordLabel", xlate("Confrim Password"));
		map.put("generateLabel", xlate("Generate"));
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
		map.put("saveLabel", xlate("Save"));
		map.put("cancelLabel", xlate("Cancel"));
		
		return map;
	}
	
	private void setMainLabels(Map<String,String> map)
	{
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
	}
	
	private Map<String,String> setupPaymentSentLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("paymentSentLabel", xlate("Payment Sent"));
		map.put("pendingLabel", xlate("Pending"));
		map.put("successfulLabel", xlate("Successful"));
		map.put("failedLabel", xlate("Failed"));
		for (int i=1; i<=3; i++)
		{
			map.put("idLabel" + i, xlate("Id"));
			map.put("toLabel" + i, xlate("To"));
			map.put("queryLabel" + i, xlate("Query"));
			map.put("descriptionLabel" + i, xlate("Description"));
			map.put("amountLabel" + i, xlate("Amount"));
			map.put("paymentDetailsLabel" + i, xlate("Payment Details"));
			map.put("statusLabel" + i, xlate("Status"));
			map.put("dateTimeLabel" + i, xlate("Date/Time"));
		}
		map.put("viewButton", xlate("View"));
		map.put("queryButton", xlate("Query"));
		return map;
	}
	
	private Map<String,String> setupPaymentReceivedLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
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
		map.put("viewButton", xlate("View"));
		map.put("queryButton", xlate("Query"));
		return map;
	}
	
	private Map<String,String> setupPaymentDetailsLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("paymentDetailsLabel", xlate("Payment Details"));
		map.put("idLabel1", xlate("Id"));
		map.put("descriptionLabel1", xlate("Description"));
		map.put("amountLabel1", xlate("Amount"));
		map.put("statusLabel1", xlate("Status"));
		map.put("dateTimeLabel1", xlate("Date/Time"));
		map.put("transactionInformationLabel",xlate("Transaction Information")); 
		map.put("transactionDateLabel",xlate("Transaction Date")); 
		map.put("transactionDetailsLabel",xlate("Transaction Details")); 
		map.put("transactionSlipLabel",xlate("Transaction Slip")); 
		map.put("paymentToLabel",xlate("Payment To")); 
		map.put("paymentFromLabel",xlate("Payment From")); 
		map.put("dateInFormatLabel",xlate("Date in (mm-dd-yyyy) format")); 
		map.put("approveButton", xlate("Approve"));
		map.put("rejectButton", xlate("Reject"));
		return map;
	}
	
	private Map<String,String> setupGeneologyLabels() {
		Map<String,String> map = new HashMap<String,String>();
		setMainLabels(map);
		map.put("myDownlineLabel", xlate("My Down line"));
		map.put("agentInformationLabel", xlate("Agent Information"));
		map.put("searchLabel", xlate("Search"));
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
		return map;
	}

	public Map<String, Map<String, String>> getJspMap() {
		return jspMap;
	}

	public void setJspMap(Map<String, Map<String, String>> jspMap) {
		this.jspMap = jspMap;
	}

	public TransalationMgr getTm() {
		return tm;
	}

	public void setTm(TransalationMgr tm) {
		this.tm = tm;
	}
	
	
}
