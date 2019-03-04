package org.zen.model;

import java.util.Random;

import org.zen.json.ProfileJson;
import org.zen.user.faker.FakerUtil;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;

public class ZenModelFake {

	private FakerUtil fu;
	
	public ZenModelFake()
	{
		fu = new FakerUtil();	
		fu.exclude("zen");
	}
	
	public void reset()
	{
		fu = new FakerUtil();
		fu.exclude("zen");
	}
	
	public ProfileJson createProfile(boolean systemOwned,Punter parent)
	{
		Random r = new Random();
		
		String prefix = "";
		if (!systemOwned)
			prefix = "p-";
		String contact = prefix + fu.getExclusiveRandomName();
		String phone = "012" + r.nextInt(10000000);
		while (phone.length()<10)
			phone += '0';
		ProfileJson childProfile = PunterMgr.makeProfile(contact,contact+"@test.com",phone,"88888888",
										parent.getContact(),systemOwned);
		return childProfile;
	}
	
	
}
