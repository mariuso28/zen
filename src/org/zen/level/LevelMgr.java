package org.zen.level;

import java.util.ArrayList;
import java.util.List;

public class LevelMgr {
	
	private List<Level> levels = new ArrayList<Level>();
	private double buyIn = 50;
	
	public LevelMgr()
	{
		createLevels();
	}

	private void createLevels() {
		levels.add(new Level(0,0));
		levels.add(new Level(1,20));
		levels.add(new Level(2,50));
		levels.add(new Level(3,200));
		levels.add(new Level(4,1000));
		levels.add(new Level(5,2000));
		levels.add(new Level(6,5000));
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public double getBuyIn() {
		return buyIn;
	}

	public void setBuyIn(double buyIn) {
		this.buyIn = buyIn;
	}

}
