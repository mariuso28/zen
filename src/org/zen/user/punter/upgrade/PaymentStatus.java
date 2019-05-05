package org.zen.user.punter.upgrade;

public enum PaymentStatus {
	NONE("None"),PAYMENTDUE("Due"),PAYMENTMADE("Pending"),PAYMENTSUCCESS("Successful"),PAYMENTFAIL("Failed");

	private String displayStatus;

	PaymentStatus(String ds)
	{
		setDisplayStatus(ds);
	}
	
	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}
	
	
	
	
}
