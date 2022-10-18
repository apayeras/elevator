package elevator.view;

import elevator.Event;
import elevator.model.Model;

/**
 *
 * @author usuario
 */
public class ViewEvent extends Event {
    public Model model;
    public ViewEventType type;
    
    public ViewEvent(Model model) {
        super(EventType.View);
        this.model = model;
        this.type = ViewEventType.STATE;
    }
    
    enum ViewEventType {
        STATE,
        REQUESTS
    }
}
