package org.zen.rest.services;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<PunterJson> getPunters()
	{
		Punter root = services.getZenModel().getRoot();
		PunterJson parent = addPunter(root);	
		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		List<PunterJson> result =  new ArrayList<PunterJson>();
		result.add(parent);
		return result;
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
		NumberFormat formatter = new DecimalFormat("#0.00");
		String bal = " RM" + formatter.format(punter.getAccount().getBalance());
		String href = "<a href=\"/zen/zx4/web/anon/punter&email=" + punter.getEmail() + "\""
		+ "<font color='#045023'>"+ text + bal + "</font></a>";
		pj.setText(href);
		return pj;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
}
