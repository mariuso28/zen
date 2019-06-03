package org.zen.translate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslateKhmerLabels {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		String[] src = { "Dashboard", "My Profile", "Edit Profile", "Change Password", "Agents", "Agent List", "Upgrade",
				"New Registration", "Payment", "Payment Received", "Payment Sent", "Logout", "Password",
				"Feature not available for top level Zen.","Feature not available until payment to join complete.",
				"My User Profile","Personal Information","User Name", "Zen Sponsor", "Full Name", "Passport ID", // Profile/Reg Stuff
				"Gender", "Male", "Female", "Other", "Address", "Postcode", "State", "Country", "Phone",
				"Email", "Your Payment Methods :", "Number", "Method", "Country", "Account Number", "Delete",
				"Add Payment Method :", "Save", "Cancel", "Method - Country",
				"Profile successfully updated",
				"New User Registration", "Sponsor Information", "Zen User Name", "New Agent Information",		// Registration only stuff
				"Zen Username", "Password", "Confrim Password", "Generate",
				"You have no payment methods set up","Please edit profile to set up at least one.",
				"Change Password", "Old Password", "New Password", "Confirm Password",							// Password stuff
				"Password/verify password must match.",
				"My Down line", "Agent Information", "Search",													// Geneology
				"Login", "Lost Password?", "Please supply your Zen username so we can reset your password.",	// login
				"A new password for zen member : ", " will be sent to your email",
				"Payment Received", "Pending", "Successful", "Failed", "Id", "From", "Description", "Amount",	// payments			
				"Payment Details", "Status", "Date/Time", "View","Payment Sent","To", "Query","Payment Details",
				"Transaction Information", "Transaction Date", "Transaction Details","Transaction Slip",
				"Approve", "Reject", "Payment To", "Payment From","Date in (mm-dd-yyyy) format",
				"None","Due","Pending","Successful","Failed",
				"Zen Activate Member At Rank ","Zen Upgrade Member To Rank ",
				"Payment approved. Member upgraded and notified.","Payment rejected. Member notified.",
				"Upgrade", "Contratulations!", "Up Line Information", "Rank", "Make Payment to Up Line",		// upgrade
				"Submit Payment Form", "Submission Information", "Title", "Upload Transaction Slip",
				"Submit", "Check Payment Email", "Check your email for the payment confirmation from us.",
				"If you do not receive the email confirmation within 24 hours, please check your SPAM folder.",
				"Write to "," for further assistance.", "Copy and Paste Transaction Details","Submit",
				"You have successfully registered with Zen. Make you registration payment today to proceed to ",
				" and start making money!", "Zen Activate Member At Rank ","Activation Fee: ","You are now at Rank ",
				" Upgrade today to "," to start earning more!","Zen Upgrade Member To Rank ", "Upgrade Fee: ",
				"Transaction details successfully submitted.","Check email for comfirmation of receipt.",
				"Latest"																						// dashboard
				};
		
		for (String k : src)
		{
			if (home.getTranslationDao().translate(k, "km")!=null)
				continue;
			Translation translation =
				        translate.translate(
				            k,
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("km"));
			 System.out.println("xlate : " + k + " to : " + translation.getTranslatedText());
			 home.getTranslationDao().store("km", k, translation.getTranslatedText());
		}
		System.out.println("DONE");
	}
}
