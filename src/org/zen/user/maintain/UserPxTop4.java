package org.zen.user.maintain;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

import java.util.List;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zen.persistence.home.Home;
import org.zen.user.punter.Punter;

public class UserPxTop4 {

	private static String createRandomPassword()
	{
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
		        .withinRange('2', 'Z')
		        .filteredBy(LETTERS, DIGITS)
		        .build();
		String random = generator.generate(8);
		random = random.replace('O','Z');
		random = random.replace('L','C');
		random = random.replace("0","X").toLowerCase();
		
		return random;
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			Punter punter = home.getPunterDao().getByContact("mony");
			String raw = createRandomPassword();
			System.out.println(punter.getContact() + " " + raw + " " + punter.getLevel());
			String pw = encoder.encode(raw);
			punter.setPassword(pw);
			home.getPunterDao().updatePassword(punter);
			
			List<Punter> ps = home.getPunterDao().getAllPunters();
			for (Punter p : ps)
			{
				if (p.isEnabled() && p.getLevel()<4)
				{
					raw = createRandomPassword();
					System.out.println(p.getContact() + " " + raw + " " + p.getLevel());
					pw = encoder.encode(raw);
					p.setPassword(pw);
					home.getPunterDao().updatePassword(p);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
