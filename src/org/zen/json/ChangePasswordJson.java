package org.zen.json;

public class ChangePasswordJson {

	private String oldPassword;
	private String password;
	
	public ChangePasswordJson()
	{
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
