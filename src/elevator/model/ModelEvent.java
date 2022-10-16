package elevator.model;

import elevator.Event;
import elevator.control.Direction;

/**
 *
 * @author usuario
 */
public class ModelEvent extends Event {
    public int currentFloor;
    public boolean openedDoors;
    public Direction direction;
    public ModelEventType type;
    
    public ModelEvent(int currentFloor) {
        super(EventType.Model);
        this.currentFloor = currentFloor;
        this.type = ModelEventType.FLOOR;
    }
    
    public ModelEvent(boolean openedDoors) {
        super(EventType.Model);
        this.openedDoors = openedDoors;
        this.type = ModelEventType.DOORS;
    }
    
    public ModelEvent(Direction direction) {
        super(EventType.Model);
        this.direction = direction;
        this.type = ModelEventType.DIRECTION;
    }
            
    enum ModelEventType {
        FLOOR,
        DOORS,
        DIRECTION
    }
}
