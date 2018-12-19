package org.zen.user.account;

public class Account {

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
}
