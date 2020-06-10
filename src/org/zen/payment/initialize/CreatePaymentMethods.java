package org.zen.payment.initialize;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PaymentMethodJson;
import org.zen.persistence.home.Home;

public class CreatePaymentMethods {
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			PaymentMethodJson pm = new PaymentMethodJson();
			pm.setMethod("Test1");
			pm.setCountry("Cambodia");
			home.getPaymentDao().storePaymentMethod(pm);
			pm.setMethod("Test2");
			pm.setCountry("Cambodia");
			home.getPaymentDao().storePaymentMethod(pm);
			List<PaymentMethodJson> pms = home.getPaymentDao().getAvailablePaymentMethods();
			System.out.println(pms);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
