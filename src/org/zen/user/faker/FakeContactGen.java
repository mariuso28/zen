package org.zen.user.faker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.zen.services.Services;

public class FakeContactGen {
	private static Logger log = Logger.getLogger(FakeContactGen.class);

	private List<String> surNames = new ArrayList<String>();
	private List<String> givenNames = new ArrayList<String>();
	private int nextSurname;
	private int nextGiven;
	
	public FakeContactGen()
	{
		String givenNamePath = Services.getProp().getProperty("fakeGivenNamesPath");
		String surNamePath = Services.getProp().getProperty("fakeSurnamesPath");
		log.info("Using given names from : " + givenNamePath);
		log.info("Using surnames from : " + surNamePath);
		loadNames(surNamePath,givenNamePath);
		Collections.shuffle(surNames);
		Collections.shuffle(givenNames);
		nextSurname = 0;
		nextGiven = 0;
	}
	
	public FakeContact getSytemFakeContact()
	{
		if (nextSurname==surNames.size())
			nextSurname = 0;
		if (nextGiven==givenNames.size())
			nextGiven = 0;
		FakeContact fc = new FakeContact();
		String contact = givenNames.get(nextGiven++);;
		fc.setContact(contact.toLowerCase());
		fc.setGivenName(contact);
		fc.setSurName(surNames.get(nextSurname++));
		fc.setFullName(fc.getGivenName()+" "+fc.getSurName());
		return fc;
	}
	
	public FakeContact getFakeContact()
	{
		FakeContact fc = new FakeContact();
		Random r = new Random();
		String contact = surNames.get(r.nextInt(surNames.size()-1));
		fc.setContact(contact.toLowerCase());
		fc.setSurName(contact);
		fc.setGivenName(givenNames.get(r.nextInt(givenNames.size()-1)));
		fc.setFullName(fc.getSurName()+" "+fc.getGivenName());
		fc.setEmail(contact+"@test.com");
		return fc;
	}
	
	private List<String> loadNames(String namePath)
	{
		List<String> names = new ArrayList<String>();
		try {
            File f = new File(namePath);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
            	names.add(readLine.trim());
            }
            b.close();
        } catch (IOException e) {
            log.error("FATAL ERROR LOADING NAMES - EXITING",e);
            System.exit(9);
        }
		return names;
	}
	
	private void loadNames(String surNamePath,String givenNamePath)
	{
		surNames = loadNames(surNamePath);
		givenNames = loadNames(givenNamePath);
	}
}
