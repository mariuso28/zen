package org.zen.simulate;

import org.apache.log4j.Logger;
import org.zen.user.punter.Punter;

public class ModelEventAcquisition extends ModelEvent {
	private static Logger log = Logger.getLogger(ModelEventAcquisition.class);
	
	public ModelEventAcquisition(Punter punter) {
		super(EventType.ACQUIRE, punter);
	}

	@Override
	public void execute(Model model) {
		log.info("Executing acquisition for : " + punter.getEmail());
		String contact = "z" + punter.getLevel().getLevel() + "-" + model.getZenModel().getPopulation();
		Punter child = model.getZenModel().addChild(punter,contact+"@test.com", contact,"88888888");
		model.scheduleUpgrade(child);
	}

	
	
	

}
