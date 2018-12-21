package org.zen.rating;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zen.rating.image.RatingImageImport;

public class RatingMgr {
	
	private static Logger log = Logger.getLogger(RatingMgr.class);
	private List<Rating> ratings = new ArrayList<Rating>();
	private double buyIn = 50;
	
	public RatingMgr()
	{
		createRatings();
	}

	private void createRatings() {
		ratings.add(new Rating(Integer.MAX_VALUE,0,RatingImageImport.loadImage("12")));
		ratings.add(new Rating(1,50,RatingImageImport.loadImage("1")));
		ratings.add(new Rating(2,100,RatingImageImport.loadImage("2")));
		ratings.add(new Rating(3,200,RatingImageImport.loadImage("3")));
		ratings.add(new Rating(4,400,RatingImageImport.loadImage("4")));
		ratings.add(new Rating(5,700,RatingImageImport.loadImage("5")));
		ratings.add(new Rating(6,1000,RatingImageImport.loadImage("6")));
		ratings.add(new Rating(7,1000,RatingImageImport.loadImage("7")));
		ratings.add(new Rating(8,1000,RatingImageImport.loadImage("8")));
		ratings.add(new Rating(9,1000,RatingImageImport.loadImage("9")));
		ratings.add(new Rating(10,1000,RatingImageImport.loadImage("10")));
		ratings.add(new Rating(11,1000,RatingImageImport.loadImage("11")));
		ratings.add(new Rating(12,1000,RatingImageImport.loadImage("13")));		// use 12 image for root
		for (int i=1; i<=12; i++)
			ratings.get(i).setUpgradeThreshold((int) Math.pow(3,i));
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
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
		for (int i=0; i<13; i++)
			log.info(i + " " + Math.pow(3, i));
	}
	
}
