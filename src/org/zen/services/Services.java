package org.zen.services;

import org.zen.model.ZenModelOriginal;
import org.zen.persistence.home.Home;
import org.zen.simulate.Model;
import org.zen.user.BaseUser;
import org.zen.user.agent.DefaultAgent;

public class Services {

	private Home home;
	private ZenModelOriginal zenModel;
	
	public void initServices()
	{
		Model simModel = new Model();
		zenModel = simModel.getZenModel();
	}
	
	public BaseUser getBaseUser(String email)
	{
		return DefaultAgent.get();
	}

	public ZenModelOriginal getZenModel() {
		return zenModel;
	}

	public void setZenModel(ZenModelOriginal zenModel) {
		this.zenModel = zenModel;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
}
