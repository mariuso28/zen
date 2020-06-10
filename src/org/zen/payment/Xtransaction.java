package org.zen.payment;

import java.util.Date;
import java.util.UUID;

import org.zen.user.punter.upgrade.PaymentStatus;

public class Xtransaction {

	private long id;
	
	private UUID payerId;
	private String payerFullName;
	private String payerContact;
	private String payerPhone;
	
	private UUID payeeId;
	private String payeeContact;
	private String payeeFullName;
	private String payeePhone;
	private Date date;
	private double amount;
	private PaymentStatus paymentStatus;
	private PaymentInfo paymentInfo;
	private String description;
	
	public Xtransaction()
	{
		
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getPayerId() {
		return payerId;
	}

	public void setPayerId(UUID payerId) {
		this.payerId = payerId;
	}

	public String getPayerFullName() {
		return payerFullName;
	}

	public void setPayerFullName(String payerFullName) {
		this.payerFullName = payerFullName;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public UUID getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(UUID payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayeeContact() {
		return payeeContact;
	}

	public void setPayeeContact(String payeeContact) {
		this.payeeContact = payeeContact;
	}

	public String getPayeeFullName() {
		return payeeFullName;
	}

	public void setPayeeFullName(String payeeFullName) {
		this.payeeFullName = payeeFullName;
	}

	public String getPayeePhone() {
		return payeePhone;
	}

	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}
	
	
	
}
