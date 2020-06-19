package org.zen.report.rollup;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.model.ZenModel;
import org.zen.persistence.home.Home;
import org.zen.rating.RatingMgr;
import org.zen.report.excel.RollupXls;

public class RuMgr {
	
	private static Logger log = Logger.getLogger(RuMgr.class);
	private RuMatrix ruMatrixTemplate;
	private Home home;
	
	public RuMgr(Home home) throws RuMgrException
	{
		setHome(home);
		createRuMatrixTemplate();
		populateRuMatrixTemplate(home);
	}

	private void populateRuMatrixTemplate(Home home) throws RuMgrException {
		try
		{
			for (RuRow rr : getRuMatrixTemplate().getRows())
			{
				int cnt = home.getReportDao().getPunterCntAtLevel(rr.getLevel());
				rr.setActMemb(cnt);
				for (RuFee rf : rr.getFees())
				{
					double paid = home.getReportDao().getPaidAtLevelAndRating(rr.getLevel(), rf.getRating());
					rf.setUpgradePaid(paid);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new RuMgrException("Cannot populate");
		}
	}

	private void createRuMatrixTemplate()
	{
		RatingMgr ratingMgr = new RatingMgr();
		RuMatrix tp = new RuMatrix();
		for (int i=0; i<ratingMgr.getRatings().size(); i++)
		{
			RuRow rr = createRuRow(ratingMgr,i);
			tp.getRows().add(rr);
		}
		ruMatrixTemplate = tp;
	}
	

	private RuRow createRuRow(RatingMgr rm, int i) {
		RuRow rr = new RuRow();
		rr.setLevel(i);
		if (i==0)
			rr.setPotMemb(1);
		else
			rr.setPotMemb((int) Math.pow(ZenModel.FULLCHILDREN,i));
		createFees(rr,rm);
		return rr;
	}

	private void createFees(RuRow rr,RatingMgr rm) {
		for (int i=1; i<=8; i++)
		{
			RuFee rf = new RuFee();
			rf.setUpgradeFee(rm.getUpgradeFeeForRating(i));
			rf.setRating(i);
			rr.getFees().add(rf);
		}
	}

	public RuMatrix getRuMatrixTemplate() {
		return ruMatrixTemplate;
	}

	public void setRuMatrixTemplate(RuMatrix ruMatrixTemplate) {
		this.ruMatrixTemplate = ruMatrixTemplate;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
	
	public static void main(String[] args)
	{

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			Home home = (Home) context.getBean("home");
			RuMgr rm = new RuMgr(home);
			new RollupXls(rm);
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

}
