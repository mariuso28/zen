package org.zen.json;

import java.util.List;

public class ModelJson {

	 private List<PunterJson> punters;
	 private long population;
	 private double topRevenue;
	 
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

	public double getTopRevenue() {
		return topRevenue;
	}

	public void setTopRevenue(double topRevenue) {
		this.topRevenue = topRevenue;
	}
	 
}
