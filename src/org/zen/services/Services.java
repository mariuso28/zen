package org.zen.services;

import org.zen.model.ZenModel;
import org.zen.simulate.Model;
import org.zen.user.BaseUser;
import org.zen.user.agent.DefaultAgent;

public class Services {

	private ZenModel zenModel;
	
	public void initServices()
	{
		Model simModel = new Model();
		zenModel = simModel.getZenModel();
	}
	
	public BaseUser getBaseUser(String email)
	{
		return DefaultAgent.get();
	}

	public ZenModel getZenModel() {
		return zenModel;
	}

	public void setZenModel(ZenModel zenModel) {
		this.zenModel = zenModel;
	}
}
