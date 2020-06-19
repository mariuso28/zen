package org.zen.report.rollup;

public class RuFee {
	private int rating;
	private double upgradeFee;
	private double upgradePaid;

	public RuFee()
	{
	}

	
	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public double getUpgradeFee() {
		return upgradeFee;
	}

	public void setUpgradeFee(double upgradeFee) {
		this.upgradeFee = upgradeFee;
	}

	public double getUpgradePaid() {
		return upgradePaid;
	}

	public void setUpgradePaid(double upgradePaid) {
		this.upgradePaid = upgradePaid;
	}


	@Override
	public String toString() {
		return "RuFee [rating=" + rating + ", upgradeFee=" + upgradeFee + ", upgradePaid=" + upgradePaid + "]";
	}
	
	

}
