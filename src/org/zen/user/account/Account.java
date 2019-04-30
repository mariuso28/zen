package org.zen.user.account;

import java.util.List;
import java.util.UUID;

import org.zen.payment.PunterPaymentMethod;

public class Account {

	private UUID id;			// 1-1 matches baseuser (punter etc.) id
	private double balance;
	private List<PunterPaymentMethod> punterPaymentMethod;
	
	
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

	public List<PunterPaymentMethod> getPunterPaymentMethod() {
		return punterPaymentMethod;
	}

	public void setPunterPaymentMethod(List<PunterPaymentMethod> punterPaymentMethod) {
		this.punterPaymentMethod = punterPaymentMethod;
	}
}
