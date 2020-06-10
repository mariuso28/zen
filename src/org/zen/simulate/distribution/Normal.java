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
		int MEAN = 10; 
		int VARIANCE = 3;
		int mc = 0;
		int cnt = 100;
		for (int idx = 0; idx < cnt; ++idx){
			int rnd = gaussian.getGaussian(MEAN, VARIANCE);
			log.info("Generated : " + rnd);
			if (rnd<0)
				mc += 1;
		}
		log.info("Cnt : " + cnt + " MC : " + mc);
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

