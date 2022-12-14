package elevator;

public abstract class Event {

    public enum EventType {
        Model,
        View,
        Control
    }

    private final EventType type;

    public Event(EventType type){
        this.type = type;
    }

    public EventType getEventType() {
        return type;
    };
}
