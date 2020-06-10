package org.zen.user.maintain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;

public class DeletePunter {
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			Punter p = home.getPunterDao().getByContact("moon");
			if (p==null)
			{
				System.out.println("NOT FOUND");
			}
			else
				home.getPunterDao().deletePunter(p);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
