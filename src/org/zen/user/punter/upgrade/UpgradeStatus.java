package org.zen.user.punter.upgrade;

import java.util.Date;

public class UpgradeStatus {
	private PaymentStatus paymentStatus;
	private int newRating;
	private String sponsorContact;
	private Date changed;
	
	public UpgradeStatus()
	{
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getNewRating() {
		return newRating;
	}

	public void setNewRating(int newRating) {
		this.newRating = newRating;
	}

	public String getSponsorContact() {
		return sponsorContact;
	}

	public void setSponsorContact(String sponsorContact) {
		this.sponsorContact = sponsorContact;
	}

	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}
	
	
}
