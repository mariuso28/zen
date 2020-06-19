package org.zen.report.persistence;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.zen.persistence.PersistenceRuntimeException;

public class ReportDaoImpl extends NamedParameterJdbcDaoSupport implements ReportDao {
	private static Logger log = Logger.getLogger(ReportDaoImpl.class);
	
	@Override
	public int getPunterCntAtLevel(final Integer level)
	{
		try
		{
			final String sql = "SELECT COUNT(*) FROM baseUser WHERE level=? AND enabled=TRUE";
			Integer cnt = getJdbcTemplate().queryForObject(sql,new Object[]{ level }, Integer.class );
			return cnt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getMaxLevelPopulated : " + e.getMessage());
		}
	}
	
	@Override
	public double getPaidAtLevelAndRating(Integer level,Integer rating)
	{
		try
		{
			final String sql = "select sum(amount) from xtransaction as xt " +
								"join baseuser as pe on xt.payeeid = pe.id " +
								"where newrating = ? and paymentstatus='PAYMENTSUCCESS' and pe.level = ?";
			Double amt = getJdbcTemplate().queryForObject(sql,new Object[]{ rating, level }, Double.class );
			if (amt == null)
				return 0.0;
			return amt;
		}
		catch (DataAccessException e)
		{
			log.error("Could not execute : " + e.getMessage(),e);
			throw new PersistenceRuntimeException("Could not execute getPaidAtLevelAndRating : " + e.getMessage());
		}
	}
	


	
}
