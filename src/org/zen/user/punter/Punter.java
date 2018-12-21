package org.zen.user.punter;

import java.util.ArrayList;
import java.util.List;

import org.zen.rating.Rating;
import org.zen.user.BaseUser;
import org.zen.user.account.Account;

public class Punter extends BaseUser {

	private Rating rating;
	private List<Punter> children = new ArrayList<Punter>();
	private Punter parent;
	private Account account = new Account();
	private boolean upgradeScheduled;
	private int level;
	
	public Punter()
	{
		super();
		setRole(BaseUser.ROLE_PUNTER);
	}
	
	public List<Punter> getChildren() {
		return children;
	}

	public void setChildren(List<Punter> children) {
		this.children = children;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
