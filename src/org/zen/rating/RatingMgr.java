package org.zen.rating;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zen.json.RatingJson;
import org.zen.model.ZenModel;
import org.zen.rating.image.RatingImageImport;

public class RatingMgr {
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RatingMgr.class);
	public static double ZENBUYIN = 10;
	private List<RatingJson> ratings = new ArrayList<RatingJson>();
	
	public RatingMgr()
	{
		createRatings();
	}

	private void createRatings() {
		ratings.add(new RatingJson(Integer.MAX_VALUE,0,RatingImageImport.loadImage("12")));
		ratings.add(new RatingJson(0,0,RatingImageImport.loadImage("0")));
		ratings.add(new RatingJson(1,RatingLevel2.ZENBUYIN,RatingImageImport.loadImage("1")));
		ratings.add(new RatingJson(2,RatingLevel2.ZEN2,RatingImageImport.loadImage("2")));
		ratings.add(new RatingJson(3,RatingLevel2.ZEN3,RatingImageImport.loadImage("3")));
		ratings.add(new RatingJson(4,RatingLevel2.ZEN4,RatingImageImport.loadImage("4")));
		ratings.add(new RatingJson(5,RatingLevel2.ZEN5,RatingImageImport.loadImage("5")));
		ratings.add(new RatingJson(6,RatingLevel2.ZEN6,RatingImageImport.loadImage("6")));
		ratings.add(new RatingJson(7,RatingLevel2.ZEN7,RatingImageImport.loadImage("7")));
		ratings.add(new RatingJson(8,RatingLevel2.ZEN8,RatingImageImport.loadImage("8")));
		ratings.add(new RatingJson(9,RatingLevel2.ZEN9,RatingImageImport.loadImage("9")));
		ratings.add(new RatingJson(10,RatingLevel2.ZEN10,RatingImageImport.loadImage("10")));
		ratings.add(new RatingJson(11,RatingLevel2.ZEN11,RatingImageImport.loadImage("11")));
		ratings.add(new RatingJson(12,RatingLevel2.ZEN12,RatingImageImport.loadImage("13")));		// use 12 image for root
		for (int i=1; i<=12; i++)
		{
			ratings.get(i).setUpgradeThreshold(ratings.get(i-1).getUpgradeThreshold() + (int) Math.pow(ZenModel.FULLCHILDREN,i));
//			log.info("Rating : " + i + " Threshold : " + ratings.get(i).getUpgradeThreshold());
		}
	}
	
	public double getUpgradeFeeForRating(int rating)
	{
		return ratings.get(rating+1).getUpgrade();
	}

	public List<RatingJson> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingJson> ratings) {
		this.ratings = ratings;
	}

	public static void main(String[] args)
	{
		new RatingMgr();
	}
	
}
