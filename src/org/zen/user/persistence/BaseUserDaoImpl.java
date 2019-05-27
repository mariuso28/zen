package org.zen.user.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.BaseUser;

@Transactional
public class BaseUserDaoImpl extends NamedParameterJdbcDaoSupport implements BaseUserDao {
	private static Logger log = Logger.getLogger(BaseUserDaoImpl.class);
	
	@Override
	public BaseUser getByContact(final String contact) throws PersistenceRuntimeException {
		try
		{
			final String sql = "SELECT * FROM baseuser WHERE contact=?";
			List<BaseUser> bus = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setString(1, contact);
				        }
				      }, BeanPropertyRowMapper.newInstance(BaseUser.class));
			if (bus.isEmpty())
				return null;
			return bus.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getByContact : " + e.getMessage());
		}
	}
	
	@Override
	public List<String> getNearestContactId(final String tryWith) {
		
		List<String> possibilities = new ArrayList<String>();
		try
		{
			final String sql = "SELECT contact FROM baseUser WHERE contact=?";
			List<String> contacts = getJdbcTemplate().queryForList(
			            sql, new Object[] { tryWith }, String.class);
			if (contacts.size()==0)
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
		int cnt = 1;
		String orig = tryWith;
		while (possibilities.size()<5)
		{
			tryWith = orig + Integer.toString(cnt);
			List<String> contacts = getJdbcTemplate().queryForList(
		            sql, new Object[] { tryWith }, String.class);
			if (contacts.size()==0)
				possibilities.add(tryWith);
			cnt++;
		}
	}

}
