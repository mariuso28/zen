package org.zen.domain.country.persistence;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.json.CountryJson;
import org.zen.persistence.PersistenceRuntimeException;

public class CountryDaoImpl extends NamedParameterJdbcDaoSupport implements CountryDao {
	private static Logger log = Logger.getLogger(CountryDaoImpl.class);
	
	@Override
	public List<String> getCountryList(String isoCode) {
		try
		{
			String sql = "SELECT country FROM country ORDER BY displayorder,country";
			if (isoCode!=null && !isoCode.equals("") && !isoCode.equals("en"))
				sql = "SELECT country" + isoCode + " FROM country ORDER BY displayorder,country";
			List<String> countries = getJdbcTemplate().queryForList(sql,String.class);
			return countries;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getCountries : " + e.getMessage());
		}
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
