package org.zen.json;

public class PunterDetailJson {
	private String contact;
	private String email;
	private String phone;
	private RatingJson rating;
	private AccountJson account = new AccountJson();
	private boolean upgradeScheduled;
	private int level;
	
	public PunterDetailJson()
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

	public RatingJson getRating() {
		return rating;
	}

	public void setRating(RatingJson rating) {
		this.rating = rating;
	}

	public AccountJson getAccount() {
		return account;
	}

	public void setAccount(AccountJson account) {
		this.account = account;
	}

	public boolean isUpgradeScheduled() {
		return upgradeScheduled;
	}

	public void setUpgradeScheduled(boolean upgradeScheduled) {
		this.upgradeScheduled = upgradeScheduled;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
}
