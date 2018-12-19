package org.zen.simulate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelDay {
	private Date date;
	private List<ModelEvent> events = new ArrayList<ModelEvent>();
	
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

	public List<ModelEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ModelEvent> events) {
		this.events = events;
	}
	
	

}
