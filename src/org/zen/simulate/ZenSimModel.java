package org.zen.simulate;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.model.ZenModel;
import org.zen.simulate.distribution.Normal;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgrException;

public class ZenSimModel {

	private static final int ACQUISITIONMEAN = 6;
	private static final int ACQUISITIONVARIANCE = 2;
	private static final int DURATION = 10;
	private static Logger log = Logger.getLogger(ZenSimModel.class); 
	@Autowired
	private ZenModel zenModel;
	private ModelCalendar modelCalendar = new ModelCalendar(DURATION);
	private List<Punter> systemPunters;
	private Set<String> sheduledUpgrades = new HashSet<String>();
	
	public ZenSimModel() 
	{
	}
	
	public void start() throws Exception
	{
		log.info("STARTING MODEL");
		initialize();
		ModelDay today = modelCalendar.getDays().get(0);
		while (today!=null)
		{
			log.info("\n\n<=== DAY " + today.getDate() + " GOT " + today.getEvents().size() + " EVENTS ===>\n\n");
			for (ModelEvent event : today.getEvents())
			{
				event.execute();
				event.setExecuted(true);
			}
			for (ModelEvent event : today.getEvents())				// ones scheduled on zero time
			{
				if (!event.isExecuted())
					event.execute();
			}
			today.getEvents().clear();
			today=modelCalendar.getTheNextDay();
		}
	}
	
	private void initialize() throws PunterMgrException
	{
//		zenModel.getZenModelFake().reset();
		zenModel.getPunterMgr().deleteAllPunters(false);
		setSystemPunters(zenModel.getPunterMgr().getSystemPunters());
		for (Punter punter : systemPunters)
			scheduleAcquisitions(punter);
	}

	public void scheduleUpgrade(Punter punter)
	{	
		if (sheduledUpgrades.contains(punter.getContact()))
		{
			executeUpgrade(punter);
		}
		
		sheduledUpgrades.add(punter.getContact());
		int sd = upgradeDays();
		int newRating = punter.getRating()+1;
		log.info("Scheduling upgrade for " + punter.getEmail() + " to rating : " 
				+ newRating + " in " + sd + " days");
		ModelDay day = modelCalendar.getDays().get(sd);
		ModelEventUpgrade meu = new ModelEventUpgrade(punter,this);
		day.getEvents().add(meu);
	}
	
	public void executeUpgrade(Punter punter)
	{
		try
		{
			sheduledUpgrades.remove(punter.getContact());
			int newRating = getZenModel().canUpgrade2(punter);
			if (newRating<=0)
				return;
			getZenModel().upgrade(punter,newRating);
		}
		catch (Exception e)
		{
			log.error("Couldn't execute upgrade",e);
		}
	}

	public void scheduleAcquisitions(Punter punter) {
		
//		Normal normal = new Normal();
		
//		int children = normal.getGaussian(3, 1);
		int children = 3;
		log.info("Scheduling + " + children + " acquisitions for punter : " + punter.getContact());
		for (int i=0; i<children; i++)
		{
			int ad = acquisitionDays();
			if (ad<0 || ad >= modelCalendar.getDays().size())
				continue;
			
			log.info("Scheduling acquisition for " + punter.getContact() + " in " + ad + " days");
			ModelDay day = modelCalendar.getDays().get(ad);
			ModelEventAcquisition mea = new ModelEventAcquisition(punter,this);
			day.getEvents().add(mea);
		}
	}

	private int upgradeDays()
	{
		if (new Random().nextBoolean())
			return 1;
		return 0;
	}
	
	private int acquisitionDays()
	{
		Normal normal = new Normal();	
		return normal.getGaussian(ACQUISITIONMEAN, ACQUISITIONVARIANCE);
	}
	
	public ModelCalendar getModelCalendar() {
		return modelCalendar;
	}

	public void setModelCalendar(ModelCalendar modelCalendar) {
		this.modelCalendar = modelCalendar;
	}

	public List<Punter> getSystemPunters() {
		return systemPunters;
	}

	public void setSystemPunters(List<Punter> systemPunters) {
		this.systemPunters = systemPunters;
	}


	public ZenModel getZenModel() {
		return zenModel;
	}

	public void setZenModel(ZenModel zenModel) {
		this.zenModel = zenModel;
	}

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			ZenSimModel zsm = (ZenSimModel) context.getBean("zenSimModel");
			zsm.start();
			// new ModelPrint(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Model ran");
	
	}

	public Set<String> getSheduledUpgrades() {
		return sheduledUpgrades;
	}

	public void setSheduledUpgrades(Set<String> sheduledUpgrades) {
		this.sheduledUpgrades = sheduledUpgrades;
	}
	
}
