package org.zen.services;

public class MailException extends Exception {
	private static final long serialVersionUID = -5170023996079870500L;
	private Exception e;
	
	public MailException(String message,Exception e)
	{
		super("Error on email : " + message);
		setE(e);
	}

	public MailException(String message)
	{
		super("Error on emal : " + message);
	}
	
	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	@Override
	public String toString() {
		return "PkfzHomeStorageException [e=" + e + "]";
	}
	

}
