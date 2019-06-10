package org.zen.user.punter;

import java.util.UUID;

public class PunterHead {
	private UUID id;
	private String contact;
	private long downlineCnt;
	private int r;
	
	public PunterHead()
	{
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getDownlineCnt() {
		return downlineCnt;
	}

	public void setDownlineCnt(long downlineCnt) {
		this.downlineCnt = downlineCnt;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	@Override
	public String toString() {
		return "PunterHead [id=" + id + ", contact=" + contact + ", downlineCnt=" + downlineCnt + ", r=" + r + "]";
	}
	
	
}
