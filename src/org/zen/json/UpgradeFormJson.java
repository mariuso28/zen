package org.zen.json;

import java.util.Date;

public class UpgradeFormJson {
	private Date transactionDate;
	private String transactionDetails;
	
	public UpgradeFormJson()
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

	
	
}
