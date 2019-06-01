package org.zen.translate;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.CountryJson;
import org.zen.persistence.home.Home;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateKhmerCountry {

	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		List<CountryJson> cs = home.getCountryDao().getCountries();
		for (CountryJson c : cs)
		{
			 Translation translation =
				        translate.translate(
				            c.getCountry(),
				            TranslateOption.sourceLanguage("en"),
				            TranslateOption.targetLanguage("km"));
			 System.out.println("xlate : " + c.getCountry() + " to : " + translation.getTranslatedText());
			 c.setCountrykm(translation.getTranslatedText());
			 home.getCountryDao().update(c);
		}
		System.out.println("DONE");
	}
}
