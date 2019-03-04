package org.zen.user.punter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.zen.json.ProfileJson;
import org.zen.user.BaseUser;
import org.zen.user.account.Account;

public class Punter extends BaseUser {

	private int rating;
	private List<Punter> children = new ArrayList<Punter>();
	private UUID parentId;
	private Punter parent;
	private UUID sponsorId;
	private Punter sponsor;
	private List<Punter> sponsoredChildren = new ArrayList<Punter>();
	private Account account = new Account();
	private boolean upgradeScheduled = false;
	private boolean systemOwned;
	
	public Punter()
	{
		super();
		setRole(BaseUser.ROLE_PUNTER);
		setEnabled(false);
	}
	
	public void copyProfileValues(ProfileJson profile)
	{
		setContact(profile.getContact());
		setEmail(profile.getEmail());
		setPhone(profile.getPhone());
		setSystemOwned(profile.isSystemOwned());
	}
	
	public String toSummaryString()
	{
		String msg = "Punter contact=" + getContact() + " rating=" + rating; 
		if (parent == null)
			return msg;
		msg+= " sponsor=" + sponsor.getContact() + " parent=" + parent.getContact();
		return msg;
	}
	
	public List<Punter> getChildren() {
		return children;
	}

	public void setChildren(List<Punter> children) {
		this.children = children;
	}

	

	public Punter getParent() {
		return parent;
	}

	public void setParent(Punter parent) {
		this.parent = parent;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isUpgradeScheduled() {
		return upgradeScheduled;
	}

	public void setUpgradeScheduled(boolean upgradeScheduled) {
		this.upgradeScheduled = upgradeScheduled;
	}

	public Punter getSponsor() {
		return sponsor;
	}

	public void setSponsor(Punter sponsor) {
		this.sponsor = sponsor;
	}

	public List<Punter> getSponsoredChildren() {
		return sponsoredChildren;
	}

	public void setSponsoredChildren(List<Punter> sponsoredChildren) {
		this.sponsoredChildren = sponsoredChildren;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public UUID getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(UUID sponsorId) {
		this.sponsorId = sponsorId;
	}

	public boolean isSystemOwned() {
		return systemOwned;
	}

	public void setSystemOwned(boolean systemOwned) {
		this.systemOwned = systemOwned;
	}

	@Override
	public String toString() {
		return "Punter [rating=" + rating + ", upgradeScheduled=" + upgradeScheduled + ", getId()=" + getId()
				+ ", getContact()=" + getContact() + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone()
				+ ", isEnabled()=" + isEnabled() + "]";
	}

	
	
}
