package org.zen.json;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class UpgradeJson {
	private int currentRank;
	private int upgradeRank;
	private PunterProfileJson upline;
	private double fee;
	private String transactionDate;				// now mm-dd-yyyy
	
	
	public UpgradeJson()
	{
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		transactionDate = sdf.format(gc.getTime());
	}

	public PunterProfileJson getUpline() {
		return upline;
	}

	public void setUpline(PunterProfileJson upline) {
		this.upline = upline;
	}

	public int getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(int currentRank) {
		this.currentRank = currentRank;
	}

	public int getUpgradeRank() {
		return upgradeRank;
	}

	public void setUpgradeRank(int upgradeRank) {
		this.upgradeRank = upgradeRank;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
