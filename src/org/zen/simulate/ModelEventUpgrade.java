package org.zen.simulate;

import org.apache.log4j.Logger;
import org.zen.user.punter.Punter;

public class ModelEventUpgrade extends ModelEvent {
	private static Logger log = Logger.getLogger(ModelEventUpgrade.class);
	
	public ModelEventUpgrade(Punter punter) {
		super(EventType.UPGRADE, punter);
	}

	@Override
	public void execute(Model model) {
		log.info("Executing upgrade for : " + punter.getEmail());
		model.getZenModel().upgrade(punter);
		
	}
	
	

}
