package org.zen.user;

import java.util.UUID;

public class BaseUser {
	private UUID id;
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

	private boolean enabled;
	public static String ROLE_PUNTER = "ROLE_PUNTER";
	public static String ROLE_AGENT = "ROLE_AGENT";
	private String role;
	
	public BaseUser()
	{
		id = UUID.randomUUID();
	}
	
	public void copyValues(BaseUser bu)
	{
		setId(bu.getId());
		setContact(bu.getContact());
		setEmail(bu.getEmail());
		setPhone(bu.getPhone());
		setEnabled(bu.isEnabled());
		setRole(bu.getRole());
		setFullName(bu.getFullName());
		setGender(bu.getGender());
		setPassportIc(bu.getPassportIc());
		setAddress(bu.getAddress());
		setState(bu.getState());
		setPostcode(bu.getPostcode());
		setCountry(bu.getCountry());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", contact=" + contact + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", enabled=" + enabled + "]";
	}

	
}
