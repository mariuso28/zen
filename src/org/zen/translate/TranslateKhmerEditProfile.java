package org.zen.translate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslateKhmerEditProfile {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		String[] src = { "My User Profile","Personal Information","User Name", "Zen Sponsor", "Full Name", "Passport ID" };
		for (String k : src)
		{
			 Translation translation =
				        translate.translate(
				            k,
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("km"));
			 System.out.println("xlate : " + k + " to : " + translation.getTranslatedText());
			 home.getTranslationDao().store("km", k, translation.getTranslatedText());
		}
	}
}
