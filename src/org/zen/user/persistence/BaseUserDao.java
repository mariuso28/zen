package org.zen.user.persistence;

import java.util.List;

import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.BaseUser;

public interface BaseUserDao {
	
	public BaseUser getByContact(final String contact) throws PersistenceRuntimeException;
	public List<String> getNearestContactId(final String tryWith);
}
