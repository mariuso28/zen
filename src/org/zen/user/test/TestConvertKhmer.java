package org.zen.user.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;
import org.zen.user.faker.FakeContact;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;

public class TestConvertKhmer {

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			PunterMgr pm = (PunterMgr) context.getBean("punterMgr");
			List<Punter> ps = home.getPunterDao().getAllPunters();
			for (Punter p : ps)
			{
				if (p.getRating()==-1)
				{
					p.setContact(pm.getZenContact().getContact());
					p.setFullName(pm.getZenContact().getFullName());
				}
				else
				{
					FakeContact fc = pm.getFakeContact(p.isSystemOwned());
					p.setContact(fc.getContact());
					p.setFullName(fc.getFullName());
					
				}
				System.out.println("converting punter to : " + p.getContact() + " " + p.getFullName() + " system " + p.isSystemOwned());
				home.getPunterDao().update(p);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
	}
}
