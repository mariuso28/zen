package org.zen.user.punter;

import java.util.ArrayList;
import java.util.List;

import org.zen.level.Level;
import org.zen.user.BaseUser;
import org.zen.user.account.Account;

public class Punter extends BaseUser {

	private Level level;
	private List<Punter> children = new ArrayList<Punter>();
	private Punter parent;
	private Account account = new Account();
	
	public Punter()
	{
		setRole(BaseUser.ROLE_PUNTER);
	}
	
	public boolean getFullChildList(int max)
	{
		return getChildren().size()==max;
	}
	
	public List<Punter> getChildren() {
		return children;
	}

	public void setChildren(List<Punter> children) {
		this.children = children;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
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
	
	
}
