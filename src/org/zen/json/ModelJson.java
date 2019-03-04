package org.zen.json;

import java.util.List;

public class ModelJson {

	 private List<PunterJson> punters;
	 private long population;
	 private long populationInside;
	 private long populationOutside;
	 private double systemOwnedRevenue;
	 private double punterOwnedRevenue;
	 
	 public ModelJson()
	 {
	 }

	public List<PunterJson> getPunters() {
		return punters;
	}

	public void setPunters(List<PunterJson> punters) {
		this.punters = punters;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public double getSystemOwnedRevenue() {
		return systemOwnedRevenue;
	}

	public void setSystemOwnedRevenue(double systemOwnedRevenue) {
		this.systemOwnedRevenue = systemOwnedRevenue;
	}

	public long getPopulationInside() {
		return populationInside;
	}

	public void setPopulationInside(long populationInside) {
		this.populationInside = populationInside;
	}

	public long getPopulationOutside() {
		return populationOutside;
	}

	public void setPopulationOutside(long populationOutside) {
		this.populationOutside = populationOutside;
	}

	public double getPunterOwnedRevenue() {
		return punterOwnedRevenue;
	}

	public void setPunterOwnedRevenue(double punterOwnedRevenue) {
		this.punterOwnedRevenue = punterOwnedRevenue;
	}
	 
	
}
