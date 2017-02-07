package tr.com.muzo.component.base;

import javafx.event.Event;
import javafx.event.EventType;

public class ButtonActionEvent extends Event {

	public ButtonActionEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	private static final long serialVersionUID = 88796976847206166L;

}
