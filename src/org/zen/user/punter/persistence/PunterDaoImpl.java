package org.zen.user.punter.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.account.Account;
import org.zen.user.punter.Punter;
import org.zen.user.punter.upgrade.UpgradeStatus;

@Transactional
public class PunterDaoImpl extends NamedParameterJdbcDaoSupport implements PunterDao {
	private static Logger log = Logger.getLogger(PunterDaoImpl.class);

	@Override
	public void setPunterEnabled(Punter punter)
	{
		try
		{
			getJdbcTemplate().update("UPDATE baseuser SET enabled=? WHERE id=?"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setBoolean(1, punter.isEnabled());
						ps.setObject(2, punter.getId());
					}
				});
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute update enabled : " + e1.getMessage());
		}	
	}
	
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
				populateObjects(p);
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
										+ "parentid,sponsorid,systemowned,level) "
										+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
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
						ps.setInt(19, punter.getLevel());
			      }
			    });
			createPunterObjects(punter);
		}
		catch (DataAccessException e1)
		{
			log.error("Could not execute : " + e1.getMessage(),e1);
			throw new PersistenceRuntimeException("Could not execute store : " + e1.getMessage());
		}	
	}
	
	private void createPunterObjects(final Punter punter) throws DataAccessException
	{
		getJdbcTemplate().update("INSERT INTO account (id,balance) "
				+ "VALUES (?,0.0)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setObject(1, punter.getId());
					}
				});
		
		UpgradeStatus us = punter.getUpgradeStatus();
		Timestamp ts = new Timestamp(us.getChanged().getTime());
		getJdbcTemplate().update("INSERT INTO upgradestatus (id,sponsorcontact,changed,paymentstatus,newrating) "
						+ "VALUES (?,?,?,?,?)"
						, new PreparedStatementSetter() {
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setObject(1, punter.getId());
								ps.setString(2,us.getSponsorContact());
								ps.setTimestamp(3, ts);
								ps.setString(4, us.getPaymentStatus().name());
								ps.setInt(5,us.getNewRating());
							}
						});
		
	}
	
	@Override
	public void updateUpgradeStatus(final Punter punter)
	{
		try
		{
			UpgradeStatus us = punter.getUpgradeStatus();
			us.setChanged((new GregorianCalendar()).getTime());
			Timestamp ts = new Timestamp(us.getChanged().getTime());
			getJdbcTemplate().update("UPDATE upgradestatus SET sponsorcontact=?,changed=?,paymentstatus=?,newrating=? "
							+ "WHERE id=?"
							, new PreparedStatementSetter() {
								public void setValues(PreparedStatement ps) throws SQLException {
									ps.setString(1,us.getSponsorContact());
									ps.setTimestamp(2, ts);
									ps.setString(3, us.getPaymentStatus().name());
									ps.setInt(4,us.getNewRating());
									ps.setObject(5, punter.getId());
								}
							});
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute updateUpgradeStatus : " + e.getMessage());
		}
	}

	@Override
	public Punter getByContact(final String contact) {
		
		final String sql = "SELECT bu.*,s.contact as sponsorcontact,p.contact as parentcontact FROM baseuser as bu " +
							"INNER JOIN baseuser AS s ON s.id = bu.sponsorid " + 
							"INNER JOIN baseuser AS p ON p.id = bu.parentid " +
							"WHERE bu.contact=?";
		try
		{
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setString(1, contact);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (punters.isEmpty())						// for zen
			{
				final String sql2 = "SELECT bu.* FROM baseuser as bu " +
						"WHERE bu.contact=? AND bu.sponsorid is NULL AND bu.parentid is NULL";
				punters = getJdbcTemplate().query(sql2,new PreparedStatementSetter() {
			        public void setValues(PreparedStatement preparedStatement) throws SQLException {
			          preparedStatement.setString(1, contact);
			        }
			      }, BeanPropertyRowMapper.newInstance(Punter.class));
			}
			if (punters.isEmpty())
			{
				return null;
			}
			populateObjects(punters.get(0));
			return punters.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getByEmail : " + e.getMessage());
		}
	}

	private void populateObjects(final Punter punter) throws DataAccessException{
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
		final String sql2 = "SELECT * FROM punterpaymentmethod WHERE punterid=?";
		List<PunterPaymentMethodJson> ppms = getJdbcTemplate().query(sql2,new PreparedStatementSetter() {
			        public void setValues(PreparedStatement preparedStatement) throws SQLException {
			          preparedStatement.setObject(1, punter.getId());
			        }
			      }, BeanPropertyRowMapper.newInstance(PunterPaymentMethodJson.class));
		punter.setPaymentMethods(ppms);
		final String sql3 = "SELECT * FROM upgradestatus WHERE id=?";
		List<UpgradeStatus> uss = getJdbcTemplate().query(sql3,new PreparedStatementSetter() {
			        public void setValues(PreparedStatement preparedStatement) throws SQLException {
			          preparedStatement.setObject(1, punter.getId());
			        }
			      }, BeanPropertyRowMapper.newInstance(UpgradeStatus.class));
		if (uss.isEmpty())
		{
			log.error("UpgradeStatus doesn't exist for punter : " + punter.getId());
			return;
		}
		punter.setUpgradeStatus(uss.get(0));
	}


	@Override
	public List<Punter> getChildren(final Punter parent) {
		final String sql = "SELECT bu.*,s.contact as sponsorcontact,p.contact as parentcontact FROM baseuser as bu " +
				"INNER JOIN baseuser AS s ON s.id = bu.sponsorid " + 
				"INNER JOIN baseuser AS p ON p.id = bu.parentid " +
				"WHERE bu.parentid=?";
		try
		{
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, parent.getId());
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			for (Punter p : punters)
				populateObjects(p);
			parent.setChildren(punters);
			return punters;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getChildren : " + e.getMessage());
		}
	}
	
	@Override
	public List<Punter> getPuntersForLevel(int level) {
		final String sql = "SELECT bu.*,s.contact as sponsorcontact,p.contact as parentcontact FROM baseuser as bu " +
				"INNER JOIN baseuser AS s ON s.id = bu.sponsorid " + 
				"INNER JOIN baseuser AS p ON p.id = bu.parentid " +
				"WHERE bu.level=?";
		try
		{
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, level);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			for (Punter p : punters)
				populateObjects(p);
			return punters;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getChildren : " + e.getMessage());
		}
	}
	
	@Override
	public List<Punter> getSponsoredChildren(final Punter parent) {
		final String sql = "SELECT bu.*,s.contact as sponsorcontact,p.contact as parentcontact FROM baseuser as bu " +
			"INNER JOIN baseuser AS s ON s.id = bu.sponsorid " + 
			"INNER JOIN baseuser AS p ON p.id = bu.parentid " +
			"WHERE bu.parentid=?";
		try
		{
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, parent.getId());
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			for (Punter p : punters)
				populateObjects(p);
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
	public int getMaxLevelPopulated() {
		try
		{
			final String sql = "SELECT MAX(level) FROM baseUser";
			Integer cnt = getJdbcTemplate().queryForObject(sql,Integer.class );
			return cnt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getMaxLevelPopulated : " + e.getMessage());
		}
	}
	
	@Override
	public Punter getById(final UUID id) {
		final String sql = "SELECT bu.*,s.contact as sponsorcontact,p.contact as parentcontact FROM baseuser as bu " +
				"INNER JOIN baseuser AS s ON s.id = bu.sponsorid " + 
				"INNER JOIN baseuser AS p ON p.id = bu.parentid " +
				"WHERE bu.id=?";
		try
		{
			List<Punter> punters = getJdbcTemplate().query(sql,new PreparedStatementSetter() {
				        public void setValues(PreparedStatement preparedStatement) throws SQLException {
				          preparedStatement.setObject(1, id);
				        }
				      }, BeanPropertyRowMapper.newInstance(Punter.class));
			if (punters.isEmpty())						// for zen
			{
				final String sql2 = "SELECT bu.* FROM baseuser as bu " +
						"WHERE bu.id=? AND bu.sponsorid is NULL AND bu.parentid is NULL";
				punters = getJdbcTemplate().query(sql2,new PreparedStatementSetter() {
			        public void setValues(PreparedStatement preparedStatement) throws SQLException {
			          preparedStatement.setObject(1, id);
			        }
			      }, BeanPropertyRowMapper.newInstance(Punter.class));
			}
			if (punters.isEmpty())
				return null;
			populateObjects(punters.get(0));
			return punters.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getById : " + e.getMessage());
		}
	}
	
	@Override
	public void deletePunter(final Punter punter)
	{
		List<Punter> cs = getChildren(punter);
		if (!cs.isEmpty())
		{
			for (Punter c : cs)
				deletePunter(c);
		}
		deletePunterA(punter);
	}
	
	private void deletePunterA(final Punter punter) {
		try
		{
			final String sqlu = "DELETE FROM upgradestatus WHERE id=?";
			getJdbcTemplate().update(sqlu,new Object[] { punter.getId() });
			
			final String sqlpi = "DELETE FROM paymentinfo WHERE xtransactionid IN (SELECT id FROM xtransaction WHERE payerid=? OR payeeid=?)";
			getJdbcTemplate().update(sqlpi,new Object[] { punter.getId(), punter.getId() });
			
			final String sqlx = "DELETE FROM xtransaction WHERE payerid=? OR payeeid=?";
			getJdbcTemplate().update(sqlx,new Object[] { punter.getId(), punter.getId() });
			
			final String sqlp = "DELETE FROM punterpaymentmethod WHERE punterid=?";
			getJdbcTemplate().update(sqlp,new Object[] { punter.getId() });
			
			final String sqla = "DELETE FROM account WHERE id=?";
			getJdbcTemplate().update(sqla,new Object[] { punter.getId() });
			
			final String sqlb = "DELETE FROM baseuser WHERE id=?";
			getJdbcTemplate().update(sqlb,new Object[] { punter.getId() });
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deletePunter : " + e.getMessage());
		}
	}
	
	public void deletePuntersBetweenLevels(int from,int to) {
		
		for (int level=to; level>=from; level--)
		{
			try
			{
				final String sql0 = "DELETE FROM upgradestatus AS pus WHERE "
						+ "pus.id IN (SELECT bu.id FROM baseuser AS bu WHERE level='" + level + "')";
				getJdbcTemplate().update(sql0);
				
				final String sql = "DELETE FROM punterpaymentmethod AS pmm WHERE "
						+ "pmm.punterid IN (SELECT bu.id FROM baseuser AS bu WHERE level='" + level + "')";
				getJdbcTemplate().update(sql);
				
				final String sql1 = "DELETE FROM account AS acc WHERE "
						+ "acc.id IN (SELECT bu.id FROM baseuser AS bu WHERE level='" + level + "')";
				getJdbcTemplate().update(sql1);
				
				final String sqlpi1 = "DELETE FROM paymentinfo WHERE xtransactionid IN "
						+ "(SELECT id FROM xtransaction WHERE payerid IN "
						+ "(SELECT bu.contact FROM baseuser AS bu WHERE level='" + level + "'))";
				getJdbcTemplate().update(sqlpi1);
				
				final String sqlpi2 = "DELETE FROM paymentinfo WHERE xtransactionid IN "
						+ "(SELECT id FROM xtransaction WHERE payeeid IN "
						+ "(SELECT bu.contact FROM baseuser AS bu WHERE level='" + level + "'))";
				getJdbcTemplate().update(sqlpi2);
				
				final String sqlx = "DELETE FROM xtransaction WHERE payerid IN " + 
											"(SELECT bu.contact FROM baseuser AS bu WHERE level='" + level + "'))";
				getJdbcTemplate().update(sqlx);
				
				final String sqlx2 = "DELETE FROM xtransaction WHERE payeeid IN " + 
						"(SELECT bu.contact FROM baseuser AS bu WHERE level='" + level + "'))";
				getJdbcTemplate().update(sqlx2);
					
				final String sql2 = "DELETE FROM baseuser WHERE Role = 'ROLE_PUNTER' AND level='" + level + "'";
				getJdbcTemplate().update(sql2);
			}
			catch (DataAccessException e)
			{
				log.error("Could not execute : " + e.getMessage(),e);
				throw new PersistenceRuntimeException("Could not execute deletePuntersBetweenLevels : " + e.getMessage());
			}
		}
	}

	
	@Override
	public void deleteAllPunters(boolean systemOwned) {
		// INCOMPLETE NEEDS CHECKING IF USED
		try
		{
			final String sql0 = "DELETE FROM upgradestatus AS pus WHERE "
					+ "pus.id IN (SELECT bu.id FROM baseuser AS bu WHERE systemowned='" + systemOwned + "')";
			getJdbcTemplate().update(sql0);
			
			final String sql = "DELETE FROM punterpaymentmethod AS pmm WHERE "
					+ "pmm.punterid IN (SELECT bu.id FROM baseuser AS bu WHERE systemowned='" + systemOwned + "')";
			getJdbcTemplate().update(sql);
			
			final String sql1 = "DELETE FROM account AS acc WHERE "
					+ "acc.id IN (SELECT bu.id FROM baseuser AS bu WHERE systemowned='" + systemOwned + "')";
			getJdbcTemplate().update(sql1);
			
			final String sqlpi1 = "DELETE FROM paymentinfo WHERE xtransactionid IN "
					+ "(SELECT id FROM xtransaction WHERE payerid IN "
					+ "(SELECT bu.contact FROM baseuser AS bu WHERE systemowned='" + systemOwned + "'))";
			getJdbcTemplate().update(sqlpi1);
			
			final String sqlpi2 = "DELETE FROM paymentinfo WHERE xtransactionid IN "
					+ "(SELECT id FROM xtransaction WHERE payeeid IN "
					+ "(SELECT bu.contact FROM baseuser AS bu WHERE systemowned='" + systemOwned + "'))";
			getJdbcTemplate().update(sqlpi2);
			
			final String sqlx = "DELETE FROM xtransaction WHERE payerid IN " + 
										"(SELECT bu.contact FROM baseuser AS bu WHERE systemowned='" + systemOwned + "'))";
			getJdbcTemplate().update(sqlx);
			
			final String sqlx2 = "DELETE FROM xtransaction WHERE payeeid IN " + 
					"(SELECT bu.contact FROM baseuser AS bu WHERE systemowned='" + systemOwned + "'))";
			getJdbcTemplate().update(sqlx2);
				
			final String sql2 = "DELETE FROM baseuser WHERE Role = 'ROLE_PUNTER' AND systemowned='" + systemOwned + "'";
			getJdbcTemplate().update(sql2);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deleteAllPunters : " + e.getMessage());
		}
	}

	@Override
	public void deleteAllPunters() {
		try
		{
			final String sql0 = "DELETE FROM upgradestatus";
			getJdbcTemplate().update(sql0);
			
			final String sql = "DELETE FROM punterpaymentmethod";
			getJdbcTemplate().update(sql);
			
			final String sql1 = "DELETE FROM account";
			getJdbcTemplate().update(sql1);
			
			final String sqlpi = "DELETE FROM paymentinfo";
			getJdbcTemplate().update(sqlpi);
			
			final String sqlx = "DELETE FROM xtransaction";
			getJdbcTemplate().update(sqlx);
			
			final String sql2 = "DELETE FROM baseuser";
			getJdbcTemplate().update(sql2);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deleteAllPunters : " + e.getMessage());
		}
	}

	

	
}
