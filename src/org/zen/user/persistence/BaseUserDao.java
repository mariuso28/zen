package org.zen.user.persistence;

import java.util.List;
import java.util.UUID;

import org.zen.persistence.PersistenceRuntimeException;
import org.zen.user.BaseUser;

public interface BaseUserDao {

	public List<String> getNearestContactId(final String tryWith);
}
