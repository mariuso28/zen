package org.zen.user.faker;

public class FakeContact {

	private String contact;
	private String surName;
	private String givenName;
	private String fullName;
	private String email;
	
	public FakeContact()
	{
	}

	public FakeContact(String contact,String surName,String givenName,String email)
	{
		setContact(contact);
		setSurName(surName);
		setGivenName(givenName);
		setFullName(surName + " " + givenName);
		setEmail(email);
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
