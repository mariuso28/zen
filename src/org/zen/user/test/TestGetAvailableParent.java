package org.zen.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;

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
			PunterMgr pm = (PunterMgr) context.getBean("punterMgr");
			Punter parent = pm.getAvailableParent(p);
			System.out.println(parent.getContact());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);
	}
}
