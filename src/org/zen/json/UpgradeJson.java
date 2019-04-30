package org.zen.json;

import java.util.List;

public class UpgradeJson {
	private int currentRank;
	private int upgradeRank;
	private PunterProfileJson upline;
	private List<PunterPaymentMethodJson> ppms;
	private UpgradeFormJson form;
	
	public UpgradeJson()
	{
	}

	public PunterProfileJson getUpline() {
		return upline;
	}

	public void setUpline(PunterProfileJson upline) {
		this.upline = upline;
	}

	public List<PunterPaymentMethodJson> getPpms() {
		return ppms;
	}

	public void setPpms(List<PunterPaymentMethodJson> ppms) {
		this.ppms = ppms;
	}

	public UpgradeFormJson getForm() {
		return form;
	}

	public void setForm(UpgradeFormJson form) {
		this.form = form;
	}

	public int getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(int currentRank) {
		this.currentRank = currentRank;
	}

	public int getUpgradeRank() {
		return upgradeRank;
	}

	public void setUpgradeRank(int upgradeRank) {
		this.upgradeRank = upgradeRank;
	}
	
	
	
}
