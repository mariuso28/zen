package org.zen.json;

public class PaymentInfoJson {
	
	private String transactionDate;
	private String transactionDetails;
	private String uploadFileImage;
	
	public PaymentInfoJson()
	{
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public String getUploadFileImage() {
		return uploadFileImage;
	}

	public void setUploadFileImage(String uploadFileImage) {
		this.uploadFileImage = uploadFileImage;
	}

	
	
}
