package org.zen.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zen.rating.Rating;
import org.zen.rating.RatingMgr;
import org.zen.user.punter.Punter;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	public final static int FULLCHILDREN = 3;
	private Punter root;
	private RatingMgr ratingMgr = new RatingMgr();
	private long population;
	
	public ZenModel()
	{
		root = createPunter("zen@test.com","Zen","88888888",0);
	}
	
	public boolean getFullChildList(Punter punter)
	{
		return punter.getChildren().size()==FULLCHILDREN;
	}
	
	public Punter addChild(Punter parent,String email,String contact,String phone)
	{
		Punter child = createPunter(email,contact,phone,1);
		if (getFullChildList(parent) && parent.getRating().getRating()!=Integer.MAX_VALUE)
			parent = getNextNode(parent);
		
		parent.getChildren().add(child);
		child.setParent(parent);
		double buyIn = ratingMgr.getBuyIn();
		child.getAccount().xfer(-1*buyIn);
		child.setSeqId(population);
		parent.getAccount().xfer(buyIn);
		log.info("Created punter : " + child.getEmail() + " rating : " + child.getRating().getRating() 
					+ " xferred : " + buyIn + " to " + parent.getEmail()
					+ " population : " + population);
		int level = 0;
		while (parent!=null)
		{
			parent = parent.getParent();
			level++;
		}
		child.setLevel(level);
		return child;
	}
	
	public Punter getTrueParent(Punter parent) {
		if (getFullChildList(parent) && parent!=root)
			parent = getNextNode(parent);
		return parent;
	}

	private Punter getNextNode(Punter parent) {
		int min = 3;
		Punter choose = null;
		for (Punter child : parent.getChildren())
			if (child.getChildren().size() < min)
			{
				choose = child;
			}	
		if (choose != null)
			return choose;
		return getNextNode(parent.getChildren().get(0));
	}

	public List<Punter> canUpgrade(Punter punter)
	{
		List<Punter> upgradables = new ArrayList<Punter>();
		if (countDownstreamChildren(punter)==punter.getRating().getRating())
			upgradables.add(punter);
		addDownstreamChildren(punter,upgradables);
		return upgradables;
	}
	
	private void addDownstreamChildren(Punter punter,List<Punter> upgradables) {
		for (Punter child : punter.getChildren())
		{
			if (countDownstreamChildren(child)==child.getRating().getRating())
				upgradables.add(child);
			addDownstreamChildren(child,upgradables);
		}
	}

	private int countDownstreamChildren(Punter punter)
	{
		int sz = punter.getChildren().size();
		for (Punter child : punter.getChildren())
			sz += countDownstreamChildren(child);
		return sz;
	}
	
	public void upgrade(Punter punter)
	{
		punter.setUpgradeScheduled(false);
		if (punter.getRating().getRating()==Integer.MAX_VALUE)
			return;
		Rating newRating = ratingMgr.getRatings().get(punter.getRating().getRating()+1);
		punter.setRating(newRating);
		Punter parentToPay = punter.getParent();
		while (parentToPay.getRating().getRating()<newRating.getRating())
		{
			parentToPay.getParent();
		}
		double upgrade = punter.getRating().getUpgrade();
		punter.getAccount().xfer(-1*upgrade);
		parentToPay.getAccount().xfer(upgrade);
		log.info("Upgraded punter : " + punter.getEmail() + " rating : " + punter.getRating()
				+ " xferred : " + upgrade + " to " + parentToPay.getEmail());
	}
	
	private Punter createPunter(String email,String contact,String phone,int rating)
	{
		Punter punter = new Punter();
		punter.setEmail(email);
		punter.setContact(contact);
		punter.setPhone(phone);
		punter.setRating(ratingMgr.getRatings().get(rating));
		population++;
		return punter;
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
	
	
}
