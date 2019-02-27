package org.zen.user.punter.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.punter.Punter;

@Transactional
public class PunterDaoImpl extends NamedParameterJdbcDaoSupport implements PunterDao {
	private static Logger log = Logger.getLogger(PunterDaoImpl.class);

	@Override
	public void store(final Punter bu) throws PersistenceRuntimeException {
		bu.setId(UUID.randomUUID());
		try
		{
			getJdbcTemplate().update("INSERT INTO baseuser (id,email,phone,password,role,enabled) "
										+ "VALUES (?,?,?,?,?,false)"
			        , new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
			    	  	ps.setObject(1, bu.getId());
						ps.setString(2, bu.getEmail().toLowerCase());
						ps.setString(3, bu.getPhone());
						ps.setString(4, bu.getPassword());
						ps.setString(5, bu.getRole());
			      }
			    });
			
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute store : " + e1.getMessage());
		}	
	}

	@Override
	public Punter getByContact(final String contact) throws PersistenceRuntimeException {
		try
		{
			final String sql = "SELECT * FROM baseuser WHERE contact=?";
			List<Punter> bus = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setString(1, contact);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (bus.isEmpty())
				return null;
			return bus.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getByEmail : " + e.getMessage());
		}
	}

	@Override
	public Punter getById(final UUID id) throws PersistenceRuntimeException {
		try
		{
			final String sql = "SELECT * FROM baseUser WHERE id=?";
			List<Punter> bus = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, id);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (bus.isEmpty())
				return null;
			return bus.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getById : " + e.getMessage());
		}
	}
	
}
