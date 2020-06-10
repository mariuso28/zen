package org.zen.json;

public class RatingJson {

	private int rating;
	private double upgrade;
	private String image;
	private int upgradeThreshold;
	
	public RatingJson()
	{
	}

	public RatingJson(int rating,double upgrade,String image)
	{
		setRating(rating);
		setUpgrade(upgrade);
		setImage(image);
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(double upgrade) {
		this.upgrade = upgrade;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getUpgradeThreshold() {
		return upgradeThreshold;
	}

	public void setUpgradeThreshold(int upgradeThreshold) {
		this.upgradeThreshold = upgradeThreshold;
	}

	@Override
	public String toString() {
		return "RatingJson [rating=" + rating + ", upgrade=" + upgrade + ", upgradeThreshold=" + upgradeThreshold + "]";
	}
	
}
