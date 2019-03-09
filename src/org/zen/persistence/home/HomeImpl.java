package org.zen.persistence.home;

import org.zen.domain.country.persistence.CountryDao;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.persistence.PunterDao;

public class HomeImpl implements Home {

	private BaseUserDao baseUserDao;
	private PunterDao punterDao;
	private CountryDao countryDao;
	
	public HomeImpl()
	{
	}
	
	@Override
	public BaseUserDao getBaseUserDao() {
		return baseUserDao;
	}

	public void setBaseUserDao(BaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	public PunterDao getPunterDao() {
		return punterDao;
	}

	public void setPunterDao(PunterDao punterDao) {
		this.punterDao = punterDao;
	}

	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

}
