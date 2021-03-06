package org.zen.translate.malay;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;
import org.zen.translate.Keys;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslateMalayMessages {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Keys keys = new Keys();
		for (String k : keys.getKeys())
		{
			 Translation translation =
				        translate.translate(
				            k,
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("ms"));
			 System.out.println("xlate : " + k + " to : " + translation.getTranslatedText());
			 home.getTranslationDao().store("ms", k, translation.getTranslatedText());
		}
		System.out.println("DONE");
	}
}
