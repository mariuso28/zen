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
	private ConcurrentHashMap<String,Punter> map = new ConcurrentHashMap<String,Punter>();
	private RatingMgr ratingMgr = new RatingMgr();
	private long population;
	private int maxRating = 0;
	
	public ZenModel()
	{
		root = createPunter("zen@test.com","Zen","88888888",0);
		map.put(root.getEmail(),root);
	}
	
	public boolean getFullChildList(Punter punter)
	{
		return punter.getChildren().size()==FULLCHILDREN;
	}
	
	public Punter addChild(Punter parent,Punter finder, String email,String contact,String phone)
	{
		Punter child = createPunter(email,contact,phone,1);
		if (getFullChildList(parent) && parent.getRating().getRating()!=Integer.MAX_VALUE)
			parent = getNextNode(parent);
		
		parent.getChildren().add(child);
		child.setParent(parent);
		double buyIn = ratingMgr.getBuyIn();
		child.getAccount().xfer(-1*buyIn);
		child.setSeqId(population);
		finder.getAccount().xfer(buyIn);
		log.info("Created punter : " + child.getEmail() + " rating : " + child.getRating().getRating() 
					+ " xferred : " + buyIn + " to " + finder.getEmail()
					+ " population : " + population);
		int level = 0;
		while (parent!=null)
		{
			parent = parent.getParent();
			level++;
		}
		child.setLevel(level);
		map.put(child.getEmail(),child);
		return child;
	}
	
	public double calcModelRevenue()
	{
		double revenue = root.getAccount().getBalance();
		for (Punter child : root.getChildren())
		{
			revenue += child.getAccount().getBalance();
			for (Punter child2 : child.getChildren())
			{
				revenue += child2.getAccount().getBalance();
			}
		}
		return revenue;
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
		if (countDownstreamChildren(punter,punter.getRating().getRating())==punter.getRating().getUpgradeThreshold())
		{
			log.info("Punter : " + punter.getEmail() + " Rating : " + punter.getRating() + " is upgradable");
			upgradables.add(punter);
		}
		addDownstreamChildren(punter,upgradables);
		return upgradables;
	}
	
	private void addDownstreamChildren(Punter punter,List<Punter> upgradables) {
		for (Punter child : punter.getChildren())
		{
			if (countDownstreamChildren(child,child.getRating().getRating())==child.getRating().getUpgradeThreshold())
			{
				log.info("Child Punter : " + child.getEmail() + " Rating : " + child.getRating() + " is upgradable");
				upgradables.add(child);
			}
			addDownstreamChildren(child,upgradables);
		}
	}

	private int countDownstreamChildren(Punter punter,int rating)
	{
		int sz = punter.getChildren().size();
		if (--rating==0)
			return sz;
		for (Punter child : punter.getChildren())
			sz += countDownstreamChildren(child,rating);
		return sz;
	}
	
	public void upgrade(Punter punter)
	{
		punter.setUpgradeScheduled(false);
		if (punter.getRating().getRating()==Integer.MAX_VALUE)
			return;
		RatingJson newRating = ratingMgr.getRatings().get(punter.getRating().getRating()+1);
		punter.setRating(newRating);
		Punter parentToPay = punter.getParent();
		if (parentToPay.getParent()!=null)
			parentToPay = parentToPay.getParent();
		while (parentToPay != null && parentToPay.getRating().getRating()<newRating.getRating())
		{
			parentToPay.getParent();
		}
		double upgrade = punter.getRating().getUpgrade();
		punter.getAccount().xfer(-1*upgrade);
		parentToPay.getAccount().xfer(upgrade);
		log.info("Upgraded punter : " + punter.getEmail() + " rating : " + punter.getRating().getImage()
				+ " xferred : " + upgrade + " to " + parentToPay.getEmail());
		if (maxRating < punter.getRating().getRating())
			maxRating = punter.getRating().getRating();
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

	public int getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}

	public ConcurrentHashMap<String,Punter> getMap() {
		return map;
	}

	public void setMap(ConcurrentHashMap<String,Punter> map) {
		this.map = map;
	}
	
	
}
