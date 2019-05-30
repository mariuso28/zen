package org.zen.translate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslateKhmer {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Keys keys = new Keys();
		for (String k : keys.getKeys())
		{
			 Translation translation =
				        translate.translate(
				            k,
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("km"));
			 home.getTranslationDao().store("km", k, translation.getTranslatedText());
		}
	}
}
