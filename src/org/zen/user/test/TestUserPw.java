package org.zen.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;

public class TestUserPw {

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			Punter p = home.getPunterDao().getByContact("gold");
			System.out.println("Enabled is : " + p.isEnabled());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean m = encoder.matches("88888888",p.getPassword());
			System.out.println("PW Match is : " + m);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
