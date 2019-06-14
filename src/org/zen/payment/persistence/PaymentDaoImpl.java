package org.zen.payment.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.payment.PaymentInfo;
import org.zen.payment.Xtransaction;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.punter.upgrade.PaymentStatus;

@Transactional
public class PaymentDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentDao {
	private static Logger log = Logger.getLogger(PaymentDaoImpl.class);
	
	@Override
	public void updateXtransaction(long id,PaymentStatus paymentStatus)
	{
		Timestamp ts = new Timestamp((new GregorianCalendar().getTime().getTime()));
		String sql = "UPDATE xtransaction SET paymentstatus=?,date=? WHERE id=?";
		try
		{
			 getJdbcTemplate().update(sql, new Object[] { paymentStatus.name(), ts, new Long(id) });
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute updateXtransaction : " + e.getMessage());
		}
	}
	
	@Override
	public Xtransaction getXtransactionById(long id)
	{
		String sql = "SELECT xt.*," +
				 "payer.contact AS payercontact,payer.fullname AS payerfullname,payer.phone AS payerphone," +
				 "payee.contact AS payeecontact,payee.fullname AS payeefullname,payee.phone AS payeephone " +
				 "FROM xtransaction AS xt "+
				 "JOIN baseuser AS payer ON xt.payerid = payer.id " +
				 "JOIN baseuser AS payee ON xt.payeeid = payee.id " +
				 "WHERE xt.id=?";
		try
		{
			Xtransaction xt = getJdbcTemplate().queryForObject(sql, new Object[] { new Long(id) }, 
					BeanPropertyRowMapper.newInstance(Xtransaction.class));
			PaymentInfo pi = getJdbcTemplate().queryForObject("SELECT * FROM paymentinfo WHERE xtransactionid = ?", new Object[] { new Long(id) }, 
					BeanPropertyRowMapper.newInstance(PaymentInfo.class));
			xt.setPaymentInfo(pi);
			return xt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getXtransactionById : " + e.getMessage());
		}
	}
	
	@Override
	public List<Xtransaction> getXtransactionsForMember(String memberType,UUID memberId,PaymentStatus paymentStatus,long offset,long limit)
	{
		String sql = "SELECT xt.*," +
					 "payer.contact AS payercontact,payer.fullname AS payerfullname,payer.phone AS payerphone," +
					 "payee.contact AS payeecontact,payee.fullname AS payeefullname,payee.phone AS payeephone " +
					 "FROM xtransaction AS xt "+
					 "JOIN baseuser AS payer ON xt.payerid = payer.id " +
					 "JOIN baseuser AS payee ON xt.payeeid = payee.id " +
					 "WHERE " + memberType + "id=? " +
					 "AND paymentstatus = ? ORDER BY date DESC offset ? limit ?"; 
		try
		{
			List<Xtransaction> xts = getJdbcTemplate().query(sql, new Object[] { memberId, paymentStatus.name(), offset, limit }, 
					BeanPropertyRowMapper.newInstance(Xtransaction.class));
			return xts;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getXtransactionsForMember : " + e.getMessage());
		}
	}
	
	@Override
	public void storeXtransaction(Xtransaction xt)
	{
		String sql = "INSERT INTO xtransaction (payerId,payeeId,date,amount,paymentstatus,description) VALUES (?,?,?,?,?,?)";
	
		try
		{
			Timestamp ts = new Timestamp(xt.getDate().getTime());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update( new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) 
		        		throws SQLException{
		        	PreparedStatement ps =
			                connection.prepareStatement(sql, new String[] {"id"});
		        		ps.setObject(1, xt.getPayerId());
						ps.setObject(2, xt.getPayeeId());
						ps.setTimestamp(3, ts);
						ps.setDouble(4, xt.getAmount());
						ps.setString(5, xt.getPaymentStatus().name());
						ps.setString(6, xt.getDescription());
						return ps;
					}
				},keyHolder);
			
			final long id = keyHolder.getKey().longValue();
			xt.setId(id);
			storePaymentInfo(xt);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute storePaymentMethod : " + e.getMessage());
		}
	}
		
	private void storePaymentInfo(Xtransaction xt) throws DataAccessException{
		
		PaymentInfo pi = xt.getPaymentInfo();
		Timestamp ts = new Timestamp(pi.getTransactionDate().getTime());
		
		getJdbcTemplate().update("INSERT INTO paymentinfo (xtransactionid,transactiondate,transactiondetails,uploadfilebytes,uploadfilename) "
				+ "VALUES (?,?,?,?,?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setLong(1, xt.getId());
						ps.setTimestamp(2, ts);
						ps.setString(3, pi.getTransactionDetails());
						ps.setBytes(4,pi.getUploadFileBytes());
						ps.setString(5, pi.getUploadFileName());
					}
				});
	}

	@Override
	public List<PaymentMethodJson> getAvailablePaymentMethods() {
		try
		{
			final String sql = "SELECT * FROM paymentmethod ORDER BY country,method";
			List<PaymentMethodJson> pms = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(PaymentMethodJson.class));
			return pms;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getAvailablePaymentMethods : " + e.getMessage());
		}
	}

	@Override
	public PaymentMethodJson getPaymentMethodByMethod(String method) {
		try
		{
			final String sql = "SELECT * FROM paymentmethod WHERE method=?";
			List<PaymentMethodJson> pms = getJdbcTemplate().query(sql,new Object[]{ method },BeanPropertyRowMapper.newInstance(PaymentMethodJson.class));
			if (pms.isEmpty())
				return null;
			return pms.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getAvailablePaymentMethods : " + e.getMessage());
		}
	}
	
	@Override
	public PaymentMethodJson getPaymentMethodById(int id) {
		try
		{
			final String sql = "SELECT * FROM paymentmethod WHERE id=?";
			List<PaymentMethodJson> pms = getJdbcTemplate().query(sql,new Object[]{ id },BeanPropertyRowMapper.newInstance(PaymentMethodJson.class));
			if (pms.isEmpty())
				return null;
			return pms.get(0);
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getAvailablePaymentMethods : " + e.getMessage());
		}
	}
	
	@Override
	public void deletePunterPaymentMethodById(long id)
	{
		try
		{
			getJdbcTemplate().update("DELETE FROM punterpaymentmethod WHERE id=?"
					, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setLong(1,id);
						}
					});
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute deletePaymentMethodById : " + e.getMessage());
		}
	}
	
	
	@Override
	public void storePaymentMethod(PaymentMethodJson pm)
	{
		try
		{
			getJdbcTemplate().update("DELETE FROM paymentmethod WHERE method=? AND country=?"
					, new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, pm.getMethod());
							ps.setString(2, pm.getCountry());
						}
					});
			getJdbcTemplate().update("INSERT INTO paymentmethod (method,country) "
				+ "VALUES (?,?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, pm.getMethod());
						ps.setString(2, pm.getCountry());
					}
				});
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute storePaymentMethod : " + e.getMessage());
		}
	}

	@Override
	public void storePunterPaymentMethod(PunterPaymentMethodJson ppm) {
		try
		{
			getJdbcTemplate().update("INSERT INTO punterpaymentmethod (punterId,method,country,accountNum,activated) "
				+ "VALUES (?,?,?,?,?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setObject(1, ppm.getPunterId());
						ps.setString(2, ppm.getMethod());
						ps.setString(3, ppm.getCountry());
						ps.setString(4, ppm.getAccountNum());
						ps.setBoolean(5, ppm.isActivated());
					}
				});
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute storePaymentMethod : " + e.getMessage());
		}
	}
}
