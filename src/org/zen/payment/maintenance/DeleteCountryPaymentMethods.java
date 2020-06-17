package org.zen.payment.maintenance;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PaymentMethodJson;
import org.zen.persistence.home.Home;

public class DeleteCountryPaymentMethods {
	
	public static void deletePaymentMethods(Home home,String country)
	{
		home.getPaymentDao().deletePaymentMethodByCountry(country);
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			
			Home home = (Home) context.getBean("home");
			deletePaymentMethods(home,"Cambodia");
			
			List<PaymentMethodJson> pmsz = home.getPaymentDao().getAvailablePaymentMethods();
			System.out.println(pmsz);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
