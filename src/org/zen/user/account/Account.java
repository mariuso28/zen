package org.zen.user.account;

import java.util.UUID;

public class Account {

	private UUID id;			// 1-1 matches baseuser (punter etc.) id
	private double balance;
	
	public Account()
	{
	}
	
	public void xfer(double amount)
	{
		balance += amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
