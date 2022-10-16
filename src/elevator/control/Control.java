package elevator.control;

import elevator.Elevator;
import elevator.Event;
import elevator.EventListener;
import static elevator.control.ControlEvent.ControlEventType.*;

public class Control implements EventListener {
    private Elevator elevator;
    private static final int NUM_FLOORS = 4;
    private static int [] insideRequests = new int[NUM_FLOORS];
    private static Direction [] outsideRequests = new Direction[NUM_FLOORS];
    
    public Control(Elevator elevator) {
        this.elevator = elevator;
    }
    
    private void handleOutside(ControlEvent event) {
        
    }
    
    private void handleInside(ControlEvent event) {
        
    }

    @Override
    public void notify(Event e) {
        ControlEvent event = (ControlEvent) e;
        if (event.type.equals(OUTSIDE)) {
            handleOutside(event);
        } else {
            handleInside(event);
        }
    }
}
