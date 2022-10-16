package elevator.model;

import elevator.Elevator;
import elevator.Event;
import elevator.EventListener;
import elevator.control.Direction;
import elevator.view.ViewEvent;

public class Model implements EventListener {
    private Elevator elevator;
    public int currentFloor;
    public boolean openedDoors;
    public Direction direction;
    
    public Model(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void notify(Event e) {
        ModelEvent event = (ModelEvent) e;
        switch (event.type){
            case FLOOR -> {
                this.currentFloor = event.currentFloor;
            }
            case DOORS -> {
                this.openedDoors = event.openedDoors;
            }
            case DIRECTION -> {
                this.direction = event.direction;
            }
        }
        elevator.notify(new ViewEvent(this));
    }
}
