package org.zen.rating;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zen.json.RatingJson;
import org.zen.rating.image.RatingImageImport;

public class RatingMgr {
	
	private static Logger log = Logger.getLogger(RatingMgr.class);
	private List<RatingJson> ratings = new ArrayList<RatingJson>();
	private double buyIn = 50;
	
	public RatingMgr()
	{
		createRatings();
	}

	private void createRatings() {
		ratings.add(new RatingJson(Integer.MAX_VALUE,0,RatingImageImport.loadImage("12")));
		ratings.add(new RatingJson(1,50,RatingImageImport.loadImage("1")));
		ratings.add(new RatingJson(2,100,RatingImageImport.loadImage("2")));
		ratings.add(new RatingJson(3,200,RatingImageImport.loadImage("3")));
		ratings.add(new RatingJson(4,400,RatingImageImport.loadImage("4")));
		ratings.add(new RatingJson(5,700,RatingImageImport.loadImage("5")));
		ratings.add(new RatingJson(6,1000,RatingImageImport.loadImage("6")));
		ratings.add(new RatingJson(7,1000,RatingImageImport.loadImage("7")));
		ratings.add(new RatingJson(8,1000,RatingImageImport.loadImage("8")));
		ratings.add(new RatingJson(9,1000,RatingImageImport.loadImage("9")));
		ratings.add(new RatingJson(10,1000,RatingImageImport.loadImage("10")));
		ratings.add(new RatingJson(11,1000,RatingImageImport.loadImage("11")));
		ratings.add(new RatingJson(12,1000,RatingImageImport.loadImage("13")));		// use 12 image for root
		for (int i=1; i<=12; i++)
		{
			ratings.get(i).setUpgradeThreshold(ratings.get(i-1).getUpgradeThreshold() + (int) Math.pow(3,i));
			log.info("Rating : " + i + " Threshold : " + ratings.get(i).getUpgradeThreshold());
		}
	}

	public List<RatingJson> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingJson> ratings) {
		this.ratings = ratings;
	}

	public double getBuyIn() {
		return buyIn;
	}

	public void setBuyIn(double buyIn) {
		this.buyIn = buyIn;
	}

	public static void main(String[] args)
	{
		new RatingMgr();
	}
	
}
