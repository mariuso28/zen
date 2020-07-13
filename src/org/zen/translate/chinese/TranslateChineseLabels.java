package org.zen.translate.chinese;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;
import org.zen.translate.Labels;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate.TranslateOption;

public class TranslateChineseLabels {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		for (String k : Labels.src)
		{
			if (home.getTranslationDao().translate(k, "zh-CN")!=null)
				continue;
			Translation translation =
				        translate.translate(
				            k,
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("zh-CN"));
			 System.out.println("xlate : " + k + " to : " + translation.getTranslatedText());
			 home.getTranslationDao().store("ch", k, translation.getTranslatedText());
		}
		System.out.println("DONE");
	}
}