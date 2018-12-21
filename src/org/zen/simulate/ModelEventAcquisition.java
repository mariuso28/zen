package org.zen.simulate;

import java.util.List;

import org.apache.log4j.Logger;
import org.zen.user.punter.Punter;

public class ModelEventAcquisition extends ModelEvent {
	private static Logger log = Logger.getLogger(ModelEventAcquisition.class);
	 
	
	public ModelEventAcquisition(Punter punter) {
		super(EventType.ACQUIRE, punter);
	}

	@Override
	public void execute(Model model) {
		
		Punter parent = model.getZenModel().getTrueParent(punter);
		log.info("Executing acquisition for : " + parent.getEmail());
		String contact = ModelCodeMaker.makeCode(parent);
		Punter child = model.getZenModel().addChild(parent,contact+"@test.com", contact,"88888888");
		List<Punter> upgradables = model.getZenModel().canUpgrade(parent);
		for (Punter up : upgradables)
		if (!up.isUpgradeScheduled())
			model.scheduleUpgrade(up);
		
		model.scheduleAcquisitions(child);
	}


}
