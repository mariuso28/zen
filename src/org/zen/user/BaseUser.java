package org.zen.user;

import java.util.UUID;

public class BaseUser {
	private UUID id;
	private String contact;
	private String email;
	private String phone;
	private String password;
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

	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", contact=" + contact + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", enabled=" + enabled + "]";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
}
