package org.zen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.zen.json.RatingJson;
import org.zen.rating.RatingMgr;
import org.zen.user.punter.Punter;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	public final static int FULLCHILDREN = 3;
	private Punter root;
	private RatingMgr ratingMgr = new RatingMgr();
	private long population;
	private int maxRating = 0;
	
	public ZenModel()
	{
		root = createPunter("zen","Zen","88888888",0);
	}

	public Punter getRoot() {
		return root;
	}

	public void setRoot(Punter root) {
		this.root = root;
	}

	public RatingMgr getRatingMgr() {
		return ratingMgr;
	}

	public void setRatingMgr(RatingMgr ratingMgr) {
		this.ratingMgr = ratingMgr;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public int getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}
	
	
	
}
