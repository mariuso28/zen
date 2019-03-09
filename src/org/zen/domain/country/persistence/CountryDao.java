package org.zen.domain.country.persistence;

import java.util.List;

import org.zen.json.CountryJson;

public interface CountryDao {

	public List<CountryJson> getCountries();
	public CountryJson getCountryByCode(String code);
}
