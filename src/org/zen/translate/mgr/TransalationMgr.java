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
	private TranslationLabels jspMapKh;
	private TranslationLabels jspMapId;
	private TranslationLabels jspMapEn;
	private TranslationLabels jspMapCh;
	private TranslationLabels jspMapMs;
	private Map<String,Map<String,String>> jspMap;
	
	public TransalationMgr(Services services)
	{
		setTranslationDao(services.getHome().getTranslationDao());
		services.getHome().getCountryDao().initializeCountryLists();
		String sic = Services.getProp().getProperty("supportedIsoCodes","en;km;id;ch");
		for (String c : sic.split(";"))
			supportedIsoCodes.add(c);
		importFrequents();
		setIsoCode("en");
		jspMapEn = new TranslationLabels(this);	
		setIsoCode("km");
		jspMapKh = new TranslationLabels(this);	
		setIsoCode("id");
		jspMapId = new TranslationLabels(this);	
		setIsoCode("ch");
		jspMapCh = new TranslationLabels(this);	
		setIsoCode("ms");
		jspMapMs = new TranslationLabels(this);	
		new TranslationLabels(this);	
		changeIsoCode(supportedIsoCodes.get(0));		// default
	}

	private void importFrequents() {
		for (String c : supportedIsoCodes)
		{
			if (c.equals("en"))
				continue;
			Map<String,String> hm = new HashMap<String,String>();
			frequents.put(c, hm);
			String xlate = translationDao.translate(" not found", c);
			hm.put(" not found", xlate);
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
	
	public Map<String,String> getLabels(String jsp)
	{
		return jspMap.get(jsp);
	}
	
	public String getJspPrefix()
	{
		if (isoCode.equals("en"))
			return "";
		return isoCode + "/";
	}

	public void changeIsoCode(String ic) {
		setIsoCode(ic);
		currFrequent = frequents.get(ic);
		if (ic.equals("km"))
			jspMap = jspMapKh.getJspMap();
		else
		if (ic.equals("id"))
			jspMap = jspMapId.getJspMap();
		else
		if (ic.equals("ch"))
			jspMap = jspMapCh.getJspMap();
		else
		if (ic.equals("ms"))
			jspMap = jspMapMs.getJspMap();
		else
			jspMap = jspMapEn.getJspMap();
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

	public TranslationLabels getJspMapId() {
		return jspMapId;
	}

	public void setJspMapId(TranslationLabels jspMapId) {
		this.jspMapId = jspMapId;
	}

	
	
	
}
