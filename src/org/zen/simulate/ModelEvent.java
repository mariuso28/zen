package org.zen.simulate;

import org.zen.user.punter.Punter;

public abstract class ModelEvent {
	private EventType eventType;
	protected Punter punter;
	boolean executed = false;
	protected ZenSimModel model;
	
	public ModelEvent(EventType eventType,Punter punter,ZenSimModel model)
	{
		setEventType(eventType);
		setPunter(punter);
		setModel(model);
	}

	public abstract void execute() throws Exception;
	
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

	public ZenSimModel getModel() {
		return model;
	}

	public void setModel(ZenSimModel model) {
		this.model = model;
	}
	
	
}
