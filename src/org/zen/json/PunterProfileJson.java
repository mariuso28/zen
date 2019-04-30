package org.zen.json;

import java.util.Date;
import java.util.UUID;

public class PunterProfileJson {
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
	private String upstreamContact;
	private String sponsorContact;
	private boolean systemOwned;
	private int rating;
	private Date activated;
	
	public PunterProfileJson()
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

	
	public String getSponsorContact() {
		return sponsorContact;
	}

	public void setSponsorContact(String sponsorContact) {
		this.sponsorContact = sponsorContact;
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

	public String getUpstreamContact() {
		return upstreamContact;
	}

	public void setUpstreamContact(String upstreamContact) {
		this.upstreamContact = upstreamContact;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}

	
	
	
}
