package org.zen.user.punter.persistence;

import java.util.List;
import java.util.UUID;

import org.zen.user.account.Account;
import org.zen.user.punter.Punter;

public interface PunterDao {

	public void store(Punter bu);
	public Punter getByContact(final String contact);
	public Punter getById(UUID id);
	public void deletePunter(Punter punter);
	public List<Punter> getChildren(Punter root);
	public void deleteAllPunters();
	public void updateAccount(Account account);
}
