package org.zen.user.faker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.zen.services.Services;

public class FakeContactGen {
	private static Logger log = Logger.getLogger(FakeContactGen.class);

	private List<String> surNames = new ArrayList<String>();
	private List<String> givenNames = new ArrayList<String>();
	
	public FakeContactGen(Services services)
	{
		String givenNamePath = services.getProp().getProperty("fakeGivenNamesPath");
		String surNamePath = services.getProp().getProperty("fakeSurnamesPath");
		log.info("Using given names from : " + givenNamePath);
		log.info("Using surnames from : " + surNamePath);
		loadNames(surNamePath,givenNamePath);
	}
	
	public FakeContact getFakeContact()
	{
		FakeContact fc = new FakeContact();
		Random r = new Random();
		String contact = surNames.get(r.nextInt(surNames.size()-1));
		fc.setContact(contact);
		fc.setSurName(contact);
		fc.setGivenName(givenNames.get(r.nextInt(givenNames.size()-1)));
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
