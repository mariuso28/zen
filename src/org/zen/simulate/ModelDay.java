package org.zen.simulate;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ModelDay {
	private Date date;
	private ConcurrentLinkedQueue<ModelEvent> events = new ConcurrentLinkedQueue<ModelEvent>();
	
	public ModelDay()
	{
	}

	public ModelDay(Date time) {
		setDate(time);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ConcurrentLinkedQueue<ModelEvent> getEvents() {
		return events;
	}

	public void setEvents(ConcurrentLinkedQueue<ModelEvent> events) {
		this.events = events;
	}

	

}
