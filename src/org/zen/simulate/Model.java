package org.zen.simulate;

import java.util.Random;

import org.apache.log4j.Logger;
import org.zen.model.ZenModel;
import org.zen.simulate.distribution.Normal;
import org.zen.user.punter.Punter;

public class Model {

	private static final int ACQUISITIONMEAN = 10;
	private static final int ACQUISITIONVARIANCE = 3;
	private static final int DURATION = 20;
	private static Logger log = Logger.getLogger(Model.class); 
	private ZenModel zenModel;
	private ModelCalendar modelCalendar = new ModelCalendar(DURATION);
	
	public Model()
	{
		log.info("STARTING MODEL");
		initialize();
		ModelDay today = modelCalendar.getDays().get(0);
		while (today!=null)
		{
			log.info("Day " + today.getDate() + " got " + today.getEvents().size() + " Events");
			for (ModelEvent event : today.getEvents())
			{
				event.execute(this);
				event.setExecuted(true);
			}
			for (ModelEvent event : today.getEvents())				// ones scheduled on zero time
			{
				if (!event.isExecuted())
					event.execute(this);
			}
			today.getEvents().clear();
			today=modelCalendar.getTheNextDay();
		}
	}
	
	private void initialize()
	{
		zenModel = new ZenModel();
		scheduleAcquisitions(zenModel.getRoot());
	}

	public void scheduleUpgrade(Punter punter)
	{
		int sd = upgradeDays(punter);
		int newRating = punter.getRating().getRating()+1;
		log.info("Scheduling upgrade for " + punter.getEmail() + " to rating : " 
				+ newRating + " in " + sd + " days");
		ModelDay day = modelCalendar.getDays().get(sd);
		ModelEventUpgrade meu = new ModelEventUpgrade(punter);
		day.getEvents().add(meu);
	}
	
	public void scheduleAcquisitions(Punter punter) {
		
		Random rand = new Random();
		int children = 3;
		if (punter!=zenModel.getRoot())
		{
			children = rand.nextInt(3) + 1;
			for (int i=0; i<5; i++)
			{
				if (rand.nextInt(3)==0)
					children += 1;
			}
		}
		for (int i=0; i<children; i++)
		{
			int ad = acquisitionDays(punter);
			if (ad<0 || ad >= modelCalendar.getDays().size())
				continue;
			
			log.info("Scheduling acquisition for " + punter.getEmail() + " in " + ad + " days");
			ModelDay day = modelCalendar.getDays().get(ad);
			ModelEventAcquisition mea = new ModelEventAcquisition(punter);
			day.getEvents().add(mea);
		}
	}

	private int upgradeDays(Punter punter)
	{
		if (punter.getRating().getRating()==0)
			return 0;
/*	
		Normal normal = new Normal();
		
		return normal.getGaussian(5, 1);
		*/
		return 1;
	}
	
	private int acquisitionDays(Punter punter)
	{
		if (punter==zenModel.getRoot())
			return 0;
		
		if (punter.getParent()==zenModel.getRoot())
			return 1;
		
		Normal normal = new Normal();
		
		return normal.getGaussian(ACQUISITIONMEAN, ACQUISITIONVARIANCE);
	}
	
	@SuppressWarnings("unused")
	private int getPopulationWeight()
	{
		return (int) zenModel.getPopulation() / 13;
	}

	public ModelCalendar getModelCalendar() {
		return modelCalendar;
	}

	public void setModelCalendar(ModelCalendar modelCalendar) {
		this.modelCalendar = modelCalendar;
	}

	public ZenModel getZenModel() {
		return zenModel;
	}

	public void setZenModel(ZenModel zenModel) {
		this.zenModel = zenModel;
	}

	public static void main(String[] args)
	{
		Model model = new Model();
		log.info("Model ran - population : " + model.getZenModel().getPopulation());
		new ModelPrint(model);
	}
	
}
