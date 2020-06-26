package org.zen.translate.maintenance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateNewPhrase {

	public static void translate(Home home,String xcode,String target,String phrase)
	{
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Translation translation =
		        translate.translate(
		        	phrase,
		            TranslateOption.sourceLanguage("en"),
		            TranslateOption.targetLanguage(xcode));
		System.out.println("xlate : " + phrase + " to target lang : " + target + " - "+ translation.getTranslatedText());
		home.getTranslationDao().store(target, phrase, translation.getTranslatedText());
	}
	
	public static void translate(Home home,String phrase)
	{
		translate(home,"km","km",phrase);
		translate(home,"id","id",phrase);
		translate(home,"zh-CN","ch",phrase);
	}
	
	public static void main(String... args)
	{
		// PHRASE ALSO NEEDS TO BE ADDED TO LABELS OR KEYS
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		translate(home,"New phrase");
		System.out.println("DONE");
	}
}
