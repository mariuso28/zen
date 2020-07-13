package org.zen.user.maintain;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;

public class UserPwForLevel4 {

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		int cnt = 0;
		
		try
		{
			Home home = (Home) context.getBean("home");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println("Password is zxxz6828");
			String pw = encoder.encode("zxxz6828");
			List<Punter> ps = home.getPunterDao().getPuntersForLevel(4);
			for (Punter p : ps)
			{
				System.out.println("Changing : " + p.getContact());
				p.setPassword(pw);
				home.getPunterDao().updatePassword(p);
				cnt++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE WITH " + cnt + " USERS");
	}
}
