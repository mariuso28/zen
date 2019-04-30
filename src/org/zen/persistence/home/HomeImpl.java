package org.zen.persistence.home;

import org.zen.domain.country.persistence.CountryDao;
import org.zen.payment.persistence.PaymentDao;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.persistence.PunterDao;

public class HomeImpl implements Home {

	private BaseUserDao baseUserDao;
	private PunterDao punterDao;
	private CountryDao countryDao;
	private PaymentDao paymentDao;
	
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

	@Override
	public PunterDao getPunterDao() {
		return punterDao;
	}

	public void setPunterDao(PunterDao punterDao) {
		this.punterDao = punterDao;
	}

	@Override
	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	@Override
	public PaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	
}
