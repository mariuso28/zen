package org.zen.user.test;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;

public class TestGetAvailableParent {

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			Punter p = home.getPunterDao().getByContact("sat");
			if (p==null)
			{
				System.out.println("NOT FOUND");
				System.exit(0);
			}
			Punter parent = home.getPunterDao().getAvailableParent(UUID.fromString("24e6da31-89bc-43c1-a44d-59d0cdae7f4f"));
			if (parent==null)
				System.out.println("null");
			System.out.println(parent.getContact());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}
