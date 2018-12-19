package org.zen.simulate;

import java.util.Random;

import org.apache.log4j.Logger;
import org.zen.model.ZenModel;
import org.zen.user.punter.Punter;

public class Model {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Model.class); 
	private ZenModel zenModel;
	private ModelCalendar modelCalendar = new ModelCalendar(30);
	
	public Model()
	{
		initialize();
		ModelDay today = modelCalendar.getDays().get(0);
		while (today!=null)
		{
			for (ModelEvent event : today.getEvents())
				event.execute(this);
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
		ModelDay day = modelCalendar.getDays().get(sd);
		ModelEventUpgrade meu = new ModelEventUpgrade(punter);
		day.getEvents().add(meu);
	}
	
	public void scheduleAcquisitions(Punter punter) {
		int ad = acquisitionDays(punter);
		ModelDay day = modelCalendar.getDays().get(ad);
		ModelEventAcquisition mea = new ModelEventAcquisition(punter);
		day.getEvents().add(mea);
	}

	private int upgradeDays(Punter punter)
	{
		Random rand = new Random();
		if (punter.getLevel().getLevel()<2)
			return 0;
		if (punter.getLevel().getLevel()<3)
			return rand.nextInt(4);
		return 3 + rand.nextInt(10);
	}
	
	private int acquisitionDays(Punter punter)
	{
		Random rand = new Random();
		if (punter.getLevel().getLevel()<2)
			return 0;
		if (punter.getLevel().getLevel()<3)
			return rand.nextInt(4);
		return 3 + rand.nextInt(10) + getPopulationWeight();
	}
	
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

	
}
