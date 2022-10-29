package elevator.model;

import elevator.Elevator;
import elevator.Event;
import elevator.EventListener;
import elevator.view.ViewEvent;

public class Model implements EventListener {
    private final Elevator elevator;
    public int currentFloor;
    public boolean openedDoors;
    public boolean upDirection;
    
    public Model(Elevator elevator) {
        this.elevator = elevator;
        // DIRECTION INITIALIZE UPWARDS
        this.upDirection = true;
    }

    @Override
    public void notify(Event e) {
        ModelEvent event = (ModelEvent) e;
        switch (event.type){
            case FLOOR -> {
            }
            case DOORS -> {
                this.openedDoors = event.openedDoors;
            }
            case DIRECTION -> {
                this.upDirection = event.upDirection;
            }
        }
        elevator.notify(new ViewEvent(this));
    }
}
