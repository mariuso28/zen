package org.zen.payment.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.payment.PaymentMethod;
import org.zen.payment.PunterPaymentMethod;
import org.zen.persistence.PersistenceRuntimeException;

public class PaymentDaoImpl extends NamedParameterJdbcDaoSupport implements PaymentDao {
	private static Logger log = Logger.getLogger(PaymentDaoImpl.class);
	
	@Override
	public List<PaymentMethod> getAvailablePaymentMethods() {
		try
		{
			final String sql = "SELECT * FROM paymentmethod ORDER BY country,method";
			List<PaymentMethod> pms = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(PaymentMethod.class));
			return pms;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getAvailablePaymentMethods : " + e.getMessage());
		}
	}

	@Override
	public void storePaymentMethod(PaymentMethod pm)
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
	public void storePunterPaymentMethod(PunterPaymentMethod ppm) {
		try
		{
			getJdbcTemplate().update("INSERT INTO punterpaymentmethod (punterId,method,country,accountNum) "
				+ "VALUES (?,?,?,?)"
				, new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setObject(1, ppm.getPunterId());
						ps.setString(2, ppm.getMethod());
						ps.setString(3, ppm.getCountry());
						ps.setString(4, ppm.getAccountNum());
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
