package org.zen.model;

import org.apache.log4j.Logger;
import org.zen.level.LevelMgr;
import org.zen.user.punter.Punter;

public class ZenModel {

	private static Logger log = Logger.getLogger(ZenModel.class);
	
	public final static int MAXCHILDREN = 3;
	private Punter root;
	private LevelMgr levelMgr = new LevelMgr();
	private long population;
	
	public ZenModel()
	{
		root = createPunter("zen@test.com","Zen","88888888",1);
	}
	
	public Punter addChild(Punter parent,String email,String contact,String phone)
	{
		Punter child = createPunter(email,contact,phone,parent.getLevel().getLevel()+1);
		parent.getChildren().add(child);
		child.setParent(parent);
		double buyIn = levelMgr.getBuyIn();
		child.getAccount().xfer(-1*buyIn);
		parent.getAccount().xfer(buyIn);
		population++;
		log.info("Created punter : " + child.getEmail() + " level : " + child.getLevel() 
					+ " xferred : " + buyIn + " to " + parent.getEmail()
					+ " population : " + population);
		return child;
	}
	
	public void upgrade(Punter punter)
	{
		Punter parentToPay = punter.getParent().getParent();
		while (!parentToPay.getFullChildList(MAXCHILDREN))
		{
			parentToPay.getParent();
		}
		double upgrade = punter.getLevel().getUpgrade();
		punter.getAccount().xfer(-1*upgrade);
		parentToPay.getAccount().xfer(upgrade);
		log.info("Upgraded punter : " + punter.getEmail() + " level : " + punter.getLevel()
				+ " xferred : " + upgrade + " to " + parentToPay.getEmail());
	}
	
	private Punter createPunter(String email,String contact,String phone,int level)
	{
		Punter punter = new Punter();
		punter.setEmail(email);
		punter.setContact(contact);
		punter.setPhone(phone);
		punter.setLevel(levelMgr.getLevels().get(level));
		return punter;
	}
	
	public Punter getRoot() {
		return root;
	}

	public void setRoot(Punter root) {
		this.root = root;
	}

	public LevelMgr getLevelMgr() {
		return levelMgr;
	}

	public void setLevelMgr(LevelMgr levelMgr) {
		this.levelMgr = levelMgr;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}
	
	
}
