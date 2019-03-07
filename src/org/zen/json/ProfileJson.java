package org.zen.json;

public class ProfileJson {
	private String contact;
	private String email;
	private String phone;
	private String password;
	private String fullName;
	private String gender;
	private String passportIc;
	private String address;
	private String state;
	private String postcode;
	private String country;
	private String sponsorContactId;
	private boolean systemOwned;
	
	public ProfileJson()
	{
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSponsorContactId() {
		return sponsorContactId;
	}

	public void setSponsorContactId(String sponsorContactId) {
		this.sponsorContactId = sponsorContactId;
	}

	public boolean isSystemOwned() {
		return systemOwned;
	}

	public void setSystemOwned(boolean systemOwned) {
		this.systemOwned = systemOwned;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassportIc() {
		return passportIc;
	}

	public void setPassportIc(String passportIc) {
		this.passportIc = passportIc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ProfileJson [contact=" + contact + ", email=" + email + ", phone=" + phone + ", password=" + password
				+ ", sponsorContactId=" + sponsorContactId + "]";
	}
	
	
}
