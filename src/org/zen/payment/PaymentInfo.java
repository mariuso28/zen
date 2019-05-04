package org.zen.payment;

import java.util.Date;

public class PaymentInfo {
	private int xtransactionId;
	private Date transactionDate;
	private String transactionDetails;
	private byte[] uploadFileBytes;
	
	public PaymentInfo()
	{
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public byte[] getUploadFileBytes() {
		return uploadFileBytes;
	}

	public void setUploadFileBytes(byte[] uploadFileBytes) {
		this.uploadFileBytes = uploadFileBytes;
	}

	public int getXtransactionId() {
		return xtransactionId;
	}

	public void setXtransactionId(int xtransactionId) {
		this.xtransactionId = xtransactionId;
	}
	
	
	
}
