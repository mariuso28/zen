package org.zen.translate;

import java.util.ArrayList;
import java.util.List;

public class Keys {
	private List<String> keys = new ArrayList<String>();
	
	public Keys()
	{
		createKeys();
	}
	
	private void createKeys()
	{
		keys.add("Important news from Zen. Your payment failed.");
		keys.add("Hi ");
		keys.add("Your payment to be upgraded to Rank");
		keys.add("Failed. Please try an alternative method or contact:");
		keys.add("Phone:");
		keys.add("Email:");
		keys.add("Great news from Zen! You have successfully upgraded and can ");
		keys.add("start to earn $$$");
		keys.add("earn even more $$$");
		keys.add("You have successfully upgraded to Rank");
		keys.add("Good luck in your recruiting and making money!!");
		keys.add("Great news from Zen! You are now entitled to upgrade and earn more $$$");
		keys.add("You have satisfied the requirements to upgrade from Rank");
		keys.add(" to ");
		keys.add("Please login and proceed to upgrade to make your upgrade payment of $");
		keys.add("To continue to recruit and make even more money!!");
		keys.add("Please login to pay your fee to join Zen and start to earn $$$");
		keys.add("Your Zen Registration has been successfully set up for username :");
		keys.add("Please login and proceed to upgrade to make your initial joing payment of $");
		keys.add("To start to recruit and make money!!");
		keys.add("Zen - password reset");
		keys.add("Your Zen password has been reset for username :");
		keys.add("to : ");
		keys.add("Please login and change at your covenience.");
		// keys.add("");
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	
	
}
