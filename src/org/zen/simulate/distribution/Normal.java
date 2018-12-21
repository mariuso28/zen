package org.zen.simulate.distribution;

import java.util.Random;

import org.apache.log4j.Logger;

public final class Normal {

	private static Logger log = Logger.getLogger(Normal.class);

	private Random fRandom = new Random();

	public double getGaussianD(double aMean, double aVariance){
		return aMean + fRandom.nextGaussian() * aVariance;
	}
	
	public int getGaussian(int aMean,int aVariance){
		return (int) getGaussianD(aMean,aVariance);
	}

	public static void main(String... aArgs){
		Normal gaussian = new Normal();
		int MEAN = 5; 
		int VARIANCE = 1;
		for (int idx = 1; idx <= 100; ++idx){
			log.info("Generated : " + gaussian.getGaussian(MEAN, VARIANCE));
		}
	}
	/*
	public static void main(String... aArgs){
		Normal gaussian = new Normal();
		double MEAN = 5.0f; 
		double VARIANCE = 5.0f;
		for (int idx = 1; idx <= 10; ++idx){
			log.info("Generated : " + gaussian.getGaussianD(MEAN, VARIANCE));
		}
	}
	*/
} 

