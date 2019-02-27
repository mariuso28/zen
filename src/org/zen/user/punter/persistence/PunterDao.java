package org.zen.user.punter.persistence;

import java.util.UUID;

import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.punter.Punter;

public interface PunterDao {

	public void store(Punter bu) throws PersistenceRuntimeException;
	public Punter getByContact(final String contact) throws PersistenceRuntimeException;
	public Punter getById(UUID id) throws PersistenceRuntimeException;
}
