package org.zen.model;

import java.util.Random;

import org.zen.json.PunterProfileJson;
import org.zen.user.punter.Punter;

public class ZenModelFake {

	public ZenModelFake()
	{
	}
	
	public PunterProfileJson createProfile(Punter sponsor,String contact)
	{
		Random r = new Random();
		String phone = "012" + r.nextInt(10000000);
		while (phone.length()<10)
			phone += '0';
		PunterProfileJson childProfile = makeProfile(contact,contact+"@test.com",phone,"88888888",
										sponsor.getContact());
		return childProfile;
	}
	
	PunterProfileJson makeProfile(String contact,String email,String phone,String password,
																			String sponsorContact)
	{
		PunterProfileJson pj = new PunterProfileJson();
		pj.setContact(contact);
		pj.setFullName(contact.toUpperCase());
		pj.setEmail(email);
		pj.setPhone(phone);
		pj.setPassword(password);
		pj.setCountry("Cambodia");
		pj.setGender("Other");
		pj.setSponsorContact(sponsorContact);
		return pj;
	}
	
}
