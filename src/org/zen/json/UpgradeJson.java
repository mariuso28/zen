package org.zen.json;

public class UpgradeJson {
	private int currentRank;
	private int upgradeRank;
	private PunterProfileJson upline;
	private double fee;
	
	
	public UpgradeJson()
	{
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
	
}
