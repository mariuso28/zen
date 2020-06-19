package org.zen.persistence.home;

import org.zen.domain.country.persistence.CountryDao;
import org.zen.payment.persistence.PaymentDao;
import org.zen.report.persistence.ReportDao;
import org.zen.translate.persistence.TranslationDao;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.persistence.PunterDao;

public interface Home {
	
	public BaseUserDao getBaseUserDao();
	public PunterDao getPunterDao();
	public CountryDao getCountryDao();
	public PaymentDao getPaymentDao();
	public TranslationDao getTranslationDao();
	public ReportDao getReportDao();

}
