package elevator.view;

import elevator.Event;
import elevator.model.Model;

public class ViewEvent extends Event {
    public Model model;
    public ViewEventType type;
    public int floor;
    public boolean up;
    
    public ViewEvent(Model model) {
        super(EventType.View);
        this.model = model;
        this.type = ViewEventType.STATE;
    }
    
    public ViewEvent(int floor, boolean up) {
        super(EventType.View);
        this.floor = floor;
        this.up = up;
        this.type = ViewEventType.OUTSIDE_REQUESTS;
    }
    
    public ViewEvent(int floor) {
        super(EventType.View);
        this.floor = floor;
        this.type = ViewEventType.INSIDE_REQUESTS;
    }
    
    enum ViewEventType {
        STATE,
        INSIDE_REQUESTS,
        OUTSIDE_REQUESTS
    }
}
