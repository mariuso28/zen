package org.zen.translate.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.persistence.PersistenceRuntimeException;

public class TranslationDaoImpl extends NamedParameterJdbcDaoSupport implements TranslationDao {
	private static Logger log = Logger.getLogger(TranslationDaoImpl.class);
	
	@Override
	public void store(String isoCode, String src, String translation) {
		try
		{
			getJdbcTemplate().update("DELETE FROM translations WHERE isocode=? AND src=?"
					, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, isoCode);
							ps.setString(2, src);
						}
					});
			getJdbcTemplate().update("INSERT INTO translations (isoCode,src,translation) "
				+ "VALUES (?,?,?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, isoCode);
						ps.setString(2, src);
						ps.setString(3, translation);
					}
				});
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not store : " + e.getMessage());
		}
	}

	@Override
	public String translate(String src, String isoCode) {
		try
		{
			String sql = "SELECT translation FROM translations WHERE isocode=? AND src=?";
			return getJdbcTemplate().queryForObject(sql, new Object[] { src, isoCode }, String.class);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not translate : " + e.getMessage());
		}
	}

}
