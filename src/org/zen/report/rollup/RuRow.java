package org.zen.report.rollup;

import java.util.ArrayList;
import java.util.List;

public class RuRow {
	private int level;
	private int potMemb;
	private int actMemb;
	private List<RuFee> fees = new ArrayList<RuFee>();
	
	public RuRow()
	{
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPotMemb() {
		return potMemb;
	}

	public void setPotMemb(int potMemb) {
		this.potMemb = potMemb;
	}

	public int getActMemb() {
		return actMemb;
	}

	public void setActMemb(int actMemb) {
		this.actMemb = actMemb;
	}

	public List<RuFee> getFees() {
		return fees;
	}

	public void setFees(List<RuFee> fees) {
		this.fees = fees;
	}

	
	
	
}
