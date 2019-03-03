package org.zen.user.faker;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.github.javafaker.Faker;

public class FakerUtil {
	
	private Set<String> taken = new HashSet<String>();
	
	public FakerUtil()
	{
		
	}

	public String getExlusiveRandomName()
	{
		while (true)
		{
			String name = getRandomName();
			name = sanitize(name);
			if (name!=null && !taken.contains(name))
			{
				taken.add(name);
				return name;
			}
		}
	}
	
	private String sanitize(String name) {
		String s = "";
		for (int i=0; i<name.length(); i++)
		{
			char ch = name.charAt(i);
			if (Character.isDigit(ch) || Character.isAlphabetic(ch) || ch=='.')
				s += ch;
		}
		if (s.length() < 3 || s.length()>16)
			return null;
		return s;
	}

	public String getRandomName()
	{
		Faker faker = new Faker();
		Random rand = new Random();
		switch (rand.nextInt(6))
		{
			case 0 : return faker.color().name();
			case 1 : return faker.superhero().name();
			case 2 : return faker.cat().name();
			case 3 : return faker.beer().name();
			case 4 : return faker.name().firstName();
			default : return faker.name().lastName();
		}
	}
	
	public void exclude(String name)
	{
		taken.add(name);
	}
	
	public static void main(String args[])
	{
		Faker faker = new Faker();

		String name = faker.name().fullName();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		
		String streetAddress = faker.address().streetAddress();
		
		System.out.println(faker.book().title());
		
		System.out.println(faker.hacker().abbreviation());
		System.out.println(faker.food().spice());
		System.out.println(faker.color().name());
		System.out.println(faker.gameOfThrones().character());
		System.out.println(faker.superhero().name());
		System.out.println(faker.cat().name());
		System.out.println(faker.beer().name());
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(name);
		System.out.println(streetAddress);
	}
}
