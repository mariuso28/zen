package org.zen.user.punter.upgrade;

import java.util.Date;
import java.util.UUID;

public class UpgradeStatus {
	private UUID id;
	private PaymentStatus paymentStatus;
	private int newRating;
	private String payeeContact;
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

	public String getPayeeContact() {
		return payeeContact;
	}

	public void setPayeeContact(String payeeContact) {
		this.payeeContact = payeeContact;
	}

	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
