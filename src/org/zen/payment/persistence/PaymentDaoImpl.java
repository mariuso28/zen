package org.zen.payment.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.persistence.PersistenceRuntimeException;

public class PaymentDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentDao {
	private static Logger log = Logger.getLogger(PaymentDaoImpl.class);
	
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
