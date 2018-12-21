package org.zen.simulate;

import org.zen.user.punter.Punter;

public abstract class ModelEvent {
	private EventType eventType;
	protected Punter punter;
	boolean executed = false;
	
	public ModelEvent(EventType eventType,Punter punter)
	{
		setEventType(eventType);
		setPunter(punter);
	}

	public abstract void execute(Model model);
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Punter getPunter() {
		return punter;
	}

	public void setPunter(Punter punter) {
		this.punter = punter;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	
	
}
