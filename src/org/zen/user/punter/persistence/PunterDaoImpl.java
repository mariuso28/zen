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
import org.zen.user.account.Account;
import org.zen.user.punter.Punter;

@Transactional
public class PunterDaoImpl extends NamedParameterJdbcDaoSupport implements PunterDao {
	private static Logger log = Logger.getLogger(PunterDaoImpl.class);

	@Override
	public void updatePassword(final Punter punter)
	{
		try
		{
			getJdbcTemplate().update("UPDATE baseuser SET password=? WHERE id=?"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, punter.getPassword());
						ps.setObject(2, punter.getId());
					}
				});
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute update password : " + e1.getMessage());
		}	
	}
	
	@Override
	public void updateAccount(final Account account)
	{
		try
		{
			getJdbcTemplate().update("UPDATE account SET balance=? WHERE id=?"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setDouble(1, account.getBalance());
						ps.setObject(2, account.getId());
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
	public double getRevenue(char type)
	{
		try
		{
			String sql = "select sum(balance) from account as a" + 
								" join baseuser as bu on a.id = bu.id";
			if (type=='S')		// system
				sql += " where bu.systemowned = true";
			if (type=='P')		// punter
				sql += " where bu.systemowned = false";
			// type 'A' is all no cond
			Double amt = getJdbcTemplate().queryForObject(sql,Double.class );
			if (amt==null)
				return 0.0;
			return amt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getSystemOwnedRevenue : " + e.getMessage());
		}
	}
	
	@Override
	public List<Punter> getSystemPunters()
	{
		try
		{
			final String sql = "SELECT * FROM baseuser WHERE systemowned=true AND rating>0";
			List<Punter> punters = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(Punter.class));
			for (Punter p : punters)
				populateAccount(p);
			return punters;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getSystemOwnedRevenue : " + e.getMessage());
		}
	}
	
	@Override
	public void updateRating(final Punter punter)
	{
		try
		{
			getJdbcTemplate().update("UPDATE baseuser SET rating=? WHERE id=?"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1,punter.getRating());
						ps.setObject(2,punter.getId());
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
	public void update(final Punter punter) {
		try
		{
			getJdbcTemplate().update("UPDATE baseuser SET email=?,phone=?,"
										+ "fullname=?,gender=?,passportic=?,address=?,state=?,postcode=?,country=?"
										+ " WHERE id=?"
						, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
			    	  	
						ps.setString(1, punter.getEmail().toLowerCase());
						ps.setString(2, punter.getPhone());
						
						ps.setString(3, punter.getFullName());
						ps.setString(4, punter.getGender());
						ps.setString(5, punter.getPassportIc());
						ps.setString(6, punter.getAddress());
						ps.setString(7, punter.getState());
						ps.setString(8, punter.getPostcode());
						ps.setString(9, punter.getCountry());
						ps.setObject(10, punter.getId());
						
			      }
			    });
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute update : " + e1.getMessage());
		}	
	}
	
	@Override
	public void store(final Punter punter) {
		punter.setId(UUID.randomUUID());
		try
		{
			getJdbcTemplate().update("INSERT INTO baseuser (id,contact,email,phone,password,role,enabled,rating,"
										+ "fullname,gender,passportic,address,state,postcode,country,"
										+ "parentid,sponsorid,systemowned) "
										+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
			        , new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
			    	  	
							ps.setObject(1, punter.getId());
			    	  	ps.setString(2, punter.getContact());
						ps.setString(3, punter.getEmail().toLowerCase());
						ps.setString(4, punter.getPhone());
						ps.setString(5, punter.getPassword());
						ps.setString(6, punter.getRole());
						ps.setBoolean(7, punter.isEnabled());
						ps.setInt(8, punter.getRating());
						
						ps.setString(9, punter.getFullName());
						ps.setString(10, punter.getGender());
						ps.setString(11, punter.getPassportIc());
						ps.setString(12, punter.getAddress());
						ps.setString(13, punter.getState());
						ps.setString(14, punter.getPostcode());
						ps.setString(15, punter.getCountry());
						
						if (punter.getParent()==null)							// root
						{
							ps.setObject(16, null);
							ps.setObject(17, null);
						}
						else
						{
							ps.setObject(16, punter.getParent().getId());
							ps.setObject(17, punter.getSponsor().getId());
						}
						ps.setBoolean(18, punter.isSystemOwned());
			      }
			    });
			createPunterAccount(punter.getId());
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute store : " + e1.getMessage());
		}	
	}
	
	private void createPunterAccount(final UUID id) throws DataAccessException
	{
		getJdbcTemplate().update("INSERT INTO account (id,balance) "
				+ "VALUES (?,0.0)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setObject(1, id);
					}
				});
	}

	@Override
	public Punter getByContact(final String contact) {
		try
		{
			final String sql = "SELECT * FROM baseuser WHERE contact=?";
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setString(1, contact);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (punters.isEmpty())
				return null;
			populateAccount(punters.get(0));
			return punters.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getByEmail : " + e.getMessage());
		}
	}

	private void populateAccount(final Punter punter) throws DataAccessException{
		final String sql = "SELECT * FROM account WHERE id=?";
		List<Account> accounts = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
			        public void setValues(PreparedStatement preparedStatement) throws SQLException {
			          preparedStatement.setObject(1, punter.getId());
			        }
			      }, BeanPropertyRowMapper.newInstance(Account.class));
		if (accounts.isEmpty())
		{
			log.error("Account doesn't exist for punter : " + punter.getId());
			return;
		}
		punter.setAccount(accounts.get(0));
	}

	@Override
	public List<Punter> getChildren(final Punter parent) {
		try
		{
			final String sql = "SELECT * FROM baseUser WHERE parentid=?";
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, parent.getId());
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			for (Punter p : punters)
				populateAccount(p);
			return punters;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getChildren : " + e.getMessage());
		}
	}
		
	public int getChildrenCnt(final Punter parent) {
		try
		{
			final String sql = "SELECT COUNT(*) FROM baseUser WHERE parentid=?";
			Integer cnt = getJdbcTemplate().queryForObject(sql,new Object[] { parent.getId() }, Integer.class );
			return cnt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getChildren : " + e.getMessage());
		}
	}

	@Override
	public Punter getById(final UUID id) {
		try
		{
			final String sql = "SELECT * FROM baseUser WHERE id=?";
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, id);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (punters.isEmpty())
				return null;
			populateAccount(punters.get(0));
			return punters.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getById : " + e.getMessage());
		}
	}
	
	@Override
	public void deletePunter(final Punter punter) {
		try
		{
			final String sql = "DELETE FROM account WHERE id=?";
			getJdbcTemplate().update(sql,new Object[] { punter.getId() });
			
			final String sql2 = "DELETE FROM baseuser WHERE id=?";
			getJdbcTemplate().update(sql2,new Object[] { punter.getId() });
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deletePunter : " + e.getMessage());
		}
	}
	
	@Override
	public void deleteAllPunters(boolean systemOwned) {
		try
		{
			final String sql = "DELETE FROM account AS acc WHERE "
					+ "acc.id IN (SELECT bu.id FROM baseuser AS bu WHERE Role = 'ROLE_PUNTER' "
					+ "AND systemowned='" + systemOwned + "')";
			getJdbcTemplate().update(sql);
			
			final String sql2 = "DELETE FROM baseuser WHERE Role = 'ROLE_PUNTER' AND systemowned='" + systemOwned + "'";
			getJdbcTemplate().update(sql2);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deleteAllPunters : " + e.getMessage());
		}
	}

	
}
