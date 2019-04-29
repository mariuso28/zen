package org.zen.simulate;

import org.apache.log4j.Logger;
import org.zen.json.PunterProfileJson;
import org.zen.model.ZenModel;
import org.zen.user.punter.Punter;

public class ModelEventAcquisition extends ModelEvent {
	private static Logger log = Logger.getLogger(ModelEventAcquisition.class);
	 
	
	public ModelEventAcquisition(Punter punter,ZenSimModel model) {
		super(EventType.ACQUIRE, punter,model);
	}

	@Override
	public void execute() throws Exception {
		
		log.info("Executing acquisition for : " + punter.getContact());
		ZenModel zenModel = model.getZenModel();
		
		PunterProfileJson childProfile = zenModel.getZenModelFake().createProfile(false,punter);
		Punter child = zenModel.getPunterMgr().registerPunter(childProfile);
		zenModel.payJoinFee(child);
		
		zenModel.tryUpgrade(punter);
		model.scheduleAcquisitions(child);
	}


}
