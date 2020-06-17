package org.zen.model;

import java.util.Random;

import org.zen.json.PunterProfileJson;
import org.zen.user.faker.FakeContact;
import org.zen.user.punter.Punter;

public class ZenModelFake {

	public ZenModelFake()
	{
	}
	
	public PunterProfileJson createProfile(Punter sponsor, FakeContact fc) {
		Random r = new Random();
		String phone = "012" + r.nextInt(10000000);
		while (phone.length()<10)
			phone += '0';
		PunterProfileJson pj = makeProfile(fc.getContact(),fc.getEmail(),phone,"88888888",
				sponsor==null ? null : sponsor.getContact());
		pj.setFullName(fc.getSurName() + " " + fc.getGivenName());
		pj.setEmail(fc.getEmail());
		return pj;
	}
	
	private PunterProfileJson makeProfile(String contact,String email,String phone,String password,
																			String sponsorContact)
	{
		PunterProfileJson pj = new PunterProfileJson();
		pj.setContact(contact);
		pj.setFullName(contact.toUpperCase());
		pj.setEmail(email);
		pj.setPhone(phone);
		pj.setPassword(password);
		pj.setCountry("458");
		pj.setGender("Other");
		pj.setSponsorContact(sponsorContact);
		return pj;
	}

	
	
}
