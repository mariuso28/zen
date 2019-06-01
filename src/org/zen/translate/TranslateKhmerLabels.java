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

		String[] src = { "My User Profile","Personal Information","User Name", "Zen Sponsor", "Full Name", "Passport ID", // Profile/Reg Stuff
				"Gender", "Male", "Female", "Other", "Address", "Postcode", "State", "Country", "Phone",
				"Email", "Your Payment Methods :", "Number", "Method", "Country", "Account Number", "Delete",
				"Add Payment Method :", "Save", "Cancel", "Method - Country",
				"New User Registration", "Sponsor Information", "Zen User Name", "New Agent Information",		// Registration only stuff
				"Zen Username", "Password", "Confrim Password", "Generate",
				"Change Password", "Old Password", "New Password", "Confirm Password",							// Password stuff
				"My Down line", "Agent Information", "Search",													// Geneology
				"Login", "Lost Password?", "Please supply your Zen username so we can reset your password.",	// login
				"A new password for zen member : ", " will be sent to your email"};													
		
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
