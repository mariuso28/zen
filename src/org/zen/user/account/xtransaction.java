package org.zen.user.account;

import java.util.Date;

public class xtransaction {

	public static char XTYPEBUYINDUE = 'D'; 
	public static char XTYPEBUYINPAID = 'P';
	public static char XTYPEUPGRADEDUE = 'U'; 
	public static char XTYPEUPGRADEPAID = 'X';
	
	private String payer;
	private String payee;
	private Date date;
	private double amount;
	private char xtype;
	
	xtransaction()
	{
		
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public char getXtype() {
		return xtype;
	}

	public void setXtype(char xtype) {
		this.xtype = xtype;
	}
	
	
	
}
