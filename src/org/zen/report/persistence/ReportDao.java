package org.zen.report.persistence;

public interface ReportDao {
	public int getPunterCntAtLevel(Integer level);
	public double getPaidAtLevelAndRating(Integer level,Integer rating);
}
