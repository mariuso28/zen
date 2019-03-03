package org.zen.user.punter.persistence;

import java.util.List;
import java.util.UUID;

import org.zen.user.punter.Punter;

public interface PunterDao {

	public void store(Punter bu);
	public Punter getByContact(final String contact);
	public Punter getById(UUID id);
	public void deleteByContact(String contact);
	public List<Punter> getChildren(Punter root);
	public void deleteAllContacts();
}
