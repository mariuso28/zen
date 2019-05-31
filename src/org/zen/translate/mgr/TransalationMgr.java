package org.zen.translate.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zen.services.Services;
import org.zen.translate.persistence.TranslationDao;

public class TransalationMgr {
	
	private static Logger log = Logger.getLogger(TransalationMgr.class);
	private TranslationDao translationDao;
	private Map<String,Map<String,String>> frequents = new HashMap<String,Map<String,String>>();
	private Map<String,String> currFrequent;
	private List<String> supportedIsoCodes = new ArrayList<String>();
	private String isoCode;
	
	public TransalationMgr(Services services)
	{
		setTranslationDao(services.getHome().getTranslationDao());
		String sic = services.getProp().getProperty("supportedIsoCodes","en;km");
		for (String c : sic.split(";"))
			supportedIsoCodes.add(c);
		importFrequents();
		changeIsoCode(supportedIsoCodes.get(0));
	}

	private void importFrequents() {
		for (String c : supportedIsoCodes)
		{
			if (c.equals("en"))
				continue;
			Map<String,String> hm = new HashMap<String,String>();
			frequents.put(c, hm);
			String xlate = translationDao.translate(" not found", c);
			hm.put("Could not ", xlate);
			xlate = translationDao.translate("Could not ", c);
			hm.put("Could not ", xlate);
			xlate = translationDao.translate(" - contact support.", c);
			hm.put(" - contact support.", xlate);
			xlate = translationDao.translate("Hi ", c);
			hm.put("Hi ", xlate);
		}
	}
	
	public String xlate(String src)
	{
		if (isoCode.equals("en"))
			return src;
		String xlate = currFrequent.get(src);
		if (xlate!=null)
			return xlate;
		xlate = translationDao.translate(src,isoCode);
		if (xlate==null)
		{
			log.error("No transalation found for : " + src + " isoCode : " + isoCode);
			return src;
		}
		return xlate;
	}
	
	public String getJspPrefix()
	{
		if (isoCode.equals("en"))
			return "";
		return "_" + isoCode;
	}

	public void changeIsoCode(String ic) {
		setIsoCode(ic);
		currFrequent = frequents.get(ic);
	}

	public TranslationDao getTranslationDao() {
		return translationDao;
	}

	public void setTranslationDao(TranslationDao translationDao) {
		this.translationDao = translationDao;
	}

	public List<String> getSupportedIsoCodes() {
		return supportedIsoCodes;
	}

	public void setSupportedIsoCodes(List<String> supportedIsoCodes) {
		this.supportedIsoCodes = supportedIsoCodes;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public Map<String,String> getCurrFrequent() {
		return currFrequent;
	}

	public void setCurrFrequent(Map<String,String> currFrequent) {
		this.currFrequent = currFrequent;
	}
	
	
}
