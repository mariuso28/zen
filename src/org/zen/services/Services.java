package org.zen.services;

import org.zen.user.BaseUser;
import org.zen.user.agent.DefaultAgent;

public class Services {

	public BaseUser getBaseUser(String email)
	{
		return DefaultAgent.get();
	}
}
