package org.zen.simulate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModelCalendar {

	private int duration;
	private List<ModelDay> days;
	private int nextDay;
	
	public ModelCalendar(int duration)
	{
		setDuration(duration);
		days = new ArrayList<ModelDay>();
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear(Calendar.HOUR);
		gc.clear(Calendar.MINUTE);
		gc.clear(Calendar.SECOND);
		gc.clear(Calendar.MILLISECOND);
		for (int i=0; i<duration; i++)
		{
			days.add(new ModelDay(gc.getTime()));
		}
	}
	
	public ModelDay getTheNextDay()
	{
		if (nextDay == duration)
			return null;
		return days.get(nextDay++);
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<ModelDay> getDays() {
		return days;
	}

	public void setDays(List<ModelDay> days) {
		this.days = days;
	}

	public int getNextDay() {
		return nextDay;
	}

	public void setNextDay(int nextDay) {
		this.nextDay = nextDay;
	}
	
	
}
