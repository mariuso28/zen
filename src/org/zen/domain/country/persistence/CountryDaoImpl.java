package org.zen.domain.country.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.json.CountryDisplayJson;
import org.zen.json.CountryJson;
import org.zen.persistence.PersistenceRuntimeException;

public class CountryDaoImpl extends NamedParameterJdbcDaoSupport implements CountryDao {
	private static Logger log = Logger.getLogger(CountryDaoImpl.class);
	private List<CountryDisplayJson> countryListEn = null;						// performance cache - need restart if country table changes
	private List<CountryDisplayJson> countryListKm = null;
	
	@Override
	public void initializeCountryLists()
	{
		 getCountryList("en");
		 getCountryList("km");
	}
	
	@Override
	public List<CountryDisplayJson> getCountryList(String isoCode) {
		try
		{
			if (isIsoCodeEn(isoCode))
			{	
				if (countryListEn==null)
				{
					String sql = "SELECT country,code FROM country ORDER BY displayorder,country";
					countryListEn = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(CountryDisplayJson.class));
				}
				return countryListEn;

			}
			if (countryListKm==null)
			{
				String sql = "SELECT country" + isoCode + " AS country,code FROM country ORDER BY displayorder,country" + isoCode;
				countryListKm = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(CountryDisplayJson.class));
			}
			return countryListKm;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getCountryList : " + e.getMessage());
		}
	}
	
	private boolean isIsoCodeEn(String isoCode)
	{
		return isoCode==null || isoCode.equals("") || isoCode.equals("en");
	}
	
	@Override
	public List<CountryJson> getCountries() {
		try
		{
			final String sql = "SELECT * FROM country ORDER BY displayorder,country";
			List<CountryJson> countries = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(CountryJson.class));
			return countries;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getCountries : " + e.getMessage());
		}
	}

	@Override
	public CountryJson getCountryByCode(final String code) {
		try
		{
			final String sql = "SELECT * FROM country WHERE code=?";
			CountryJson country = getJdbcTemplate().queryForObject(sql,new Object[] { code }, BeanPropertyRowMapper.newInstance(CountryJson.class));
			return country;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getCountryByCode : " + e.getMessage());
		}
	}

	@Override
	public void update(CountryJson country) {
		String sql = "UPDATE country SET country=?,countrykm=? WHERE code=?";
		try
		{
			 getJdbcTemplate().update(sql, new Object[] { country.getCountry(), country.getCountrykm(), country.getCode() });
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute update : " + e.getMessage());
		}
	}

}
