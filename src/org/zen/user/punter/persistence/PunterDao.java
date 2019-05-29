package org.zen.user.punter.persistence;

import java.util.List;
import java.util.UUID;

import org.zen.user.account.Account;
import org.zen.user.punter.Punter;

public interface PunterDao {

	public void store(Punter bu);
	public Punter getByContact(final String contact);
	public List<Punter> getByFullName(final String fullName);
	public Punter getById(UUID id);
	public void deletePunter(Punter punter);
	public List<Punter> getChildren(Punter root);
	public int getChildrenCnt(final Punter parent);
	public void deleteAllPunters();
	public void deletePuntersBetweenLevels(int from,int to);
	public void deleteAllPunters(boolean systemOwned);
	public void updateAccount(Account account);
	public void updateRating(Punter punter);
	public double getRevenue(char type);
	public List<Punter> getSystemPunters();
	public void update(Punter punter);
	public void updatePassword(Punter punter);
	public void setPunterEnabled(Punter punter);
	public List<Punter> getSponsoredChildren(Punter parent);
	public void updateUpgradeStatus(Punter punter);
	public List<Punter> getPuntersForLevel(int level);
	public int getMaxLevelPopulated();
	public List<Punter> getAllPunters();
}
