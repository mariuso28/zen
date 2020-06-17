package org.zen.payment.maintenance;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.PaymentMethodJson;
import org.zen.persistence.home.Home;

public class CreateMalaysianPaymentMethods {
	
	public static void createPaymentMethods(Home home)
	{
		String[] pms = { "PayPal", "Touch n Go" ,"WeChat Pay", "MaybankPay", "BigPay" };
		for (String pm : pms)
		{
			PaymentMethodJson pmj = new PaymentMethodJson();
			pmj.setMethod(pm);
			pmj.setCountry("Malaysia");
			home.getPaymentDao().storePaymentMethod(pmj);
		}
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			
			Home home = (Home) context.getBean("home");
			createPaymentMethods(home);
			
			List<PaymentMethodJson> pmsz = home.getPaymentDao().getAvailablePaymentMethods();
			System.out.println(pmsz);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
