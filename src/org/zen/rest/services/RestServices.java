package org.zen.rest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zen.json.PunterJson;
import org.zen.services.Services;
import org.zen.user.punter.Punter;

public class RestServices {
private static final Logger log = Logger.getLogger(RestServices.class);
	
	@Autowired
	private Services services;
	
	public RestServices()
	{
	}
	
	public PunterJson getPunters()
	{
		Punter root = services.getZenModel().getRoot();
		PunterJson parent = addPunter(root);	
		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		return parent;
	}

	private PunterJson addPunter(Punter punter) {
		PunterJson pj = createPunterJson(punter);
		for (Punter p : punter.getChildren())
		{
			pj.getChildren().add(addPunter(p));
		}
		return pj;
	}
	
	private PunterJson createPunterJson(Punter punter)
	{
		PunterJson pj = new PunterJson();
		pj.setId(punter.getSeqId());
		int imageId = 12;
		if (!punter.getEmail().equals("zen@test.com"))
			imageId = punter.getRating().getRating();
		pj.setImageUrl("../../../img/" + imageId + ".jpeg");
		String text = punter.getEmail();
		pj.setText(text);
		return pj;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
}
