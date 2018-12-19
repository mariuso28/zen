package org.zen.level;

public class Level {

	private int level;
	private double upgrade;
	
	public Level()
	{
	}

	public Level(int level,double upgrade)
	{
		setLevel(level);
		setUpgrade(upgrade);
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(double upgrade) {
		this.upgrade = upgrade;
	}
	
	
}
