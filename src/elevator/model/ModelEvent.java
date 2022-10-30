package elevator.model;

import elevator.Event;

public class ModelEvent extends Event {
    public int currentFloor;
    public boolean openedDoors;
    public boolean upDirection;
    public ModelEventType type;
    
    public ModelEvent(boolean openedDoors) {
        super(EventType.Model);
        this.openedDoors = openedDoors;
        this.type = ModelEventType.DOORS;
    }
    
    public ModelEvent(int currentFloor) {
        super(EventType.Model);
        this.currentFloor = currentFloor;
        this.type = ModelEventType.FLOOR;
    }
    
    public ModelEvent(int currentFloor, boolean upDirection) {
        super(EventType.Model);
        this.currentFloor = currentFloor;
        this.upDirection = upDirection;
        this.type = ModelEventType.DIRECTION;
    }
            
    enum ModelEventType {
        FLOOR,
        DOORS,
        DIRECTION
    }
}
