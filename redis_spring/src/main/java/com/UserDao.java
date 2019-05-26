package com;

public interface UserDao {
	public void saveUser(final User user);
	public User getUser(final long id);
}
