package org.zen.user.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.zen.persistence.PersistenceRuntimeException;

@Transactional
public class BaseUserDaoImpl extends NamedParameterJdbcDaoSupport implements BaseUserDao {
	private static Logger log = Logger.getLogger(BaseUserDaoImpl.class);
	
	@Override
	public List<String> getNearestContactId(final String tryWith) {
		
		List<String> possibilities = new ArrayList<String>();
		try
		{
			final String sql = "SELECT contact FROM baseUser WHERE contact=?";
			String contact = (String) getJdbcTemplate().queryForObject(
			            sql, new Object[] { tryWith }, String.class);
			if (contact == null)
				return possibilities;
			// tryWith taken generate some not taken
			else
				generatePossibilities(possibilities,tryWith);
			return possibilities;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getById : " + e.getMessage());
		}
	}

	private void generatePossibilities(List<String> possibilities,String tryWith) throws DataAccessException{
		final String sql = "SELECT contact FROM baseUser WHERE contact=?";
		int cnt = 0;
		while (possibilities.size()<5)
		{
			tryWith += Integer.toString(cnt);
			String contact = (String) getJdbcTemplate().queryForObject(
		            sql, new Object[] { tryWith }, String.class);
			if (contact == null)
				possibilities.add(tryWith);
			cnt++;
		}
	}

}
