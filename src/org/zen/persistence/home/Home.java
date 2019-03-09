package org.zen.persistence.home;

import org.zen.domain.country.persistence.CountryDao;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.persistence.PunterDao;

public interface Home {
	
	public BaseUserDao getBaseUserDao();
	public PunterDao getPunterDao();
	public CountryDao getCountryDao();

}
