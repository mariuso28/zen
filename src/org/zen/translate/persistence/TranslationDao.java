package org.zen.translate.persistence;

public interface TranslationDao {
	public void store(String isoCode,String src,String translation);
	public String translate(String src,String isoCode);
}
