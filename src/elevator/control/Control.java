package elevator.control;

import elevator.Elevator;
import elevator.Event;
import elevator.EventListener;
import static elevator.control.ControlEvent.ControlEventType.*;
import elevator.model.Model;
import elevator.model.ModelEvent;
import elevator.view.ViewEvent;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control extends Thread implements EventListener {
    private final Elevator elevator;
    private static final int NUM_FLOORS = 4;
    private static int [] insideRequests = new int[NUM_FLOORS];
    private static Direction [] outsideRequests = new Direction[NUM_FLOORS];
    private static boolean wait;
    private static boolean threadAlreadyRunning;
    
    public Control(Elevator elevator) {
        this.elevator = elevator;
        Arrays.fill(insideRequests, 0); 
        Arrays.fill(outsideRequests, Direction.IDLE); 
    }
    
    @Override
    public void run(){
        threadAlreadyRunning = true;
        Model model = elevator.getModel();
        while (checkRequests() || model.openedDoors) {
            //Model model = elevator.getModel();
        
            // Open door if floor has any request
            if (!model.openedDoors 
                    && (insideRequests[model.currentFloor] == 1 || outsideRequests[model.currentFloor] != Direction.IDLE)) {
                insideRequests[model.currentFloor] = 0;
                outsideRequests[model.currentFloor] = Direction.IDLE;
                elevator.notify(new ModelEvent(true));
                continue;
            }

            // Close door if the elevator is done waiting
            if (model.openedDoors && wait) {
                elevator.notify(new ModelEvent(false));
                wait = false;
                continue;
            }

            // Wait
            if (model.openedDoors) {
                wait = true;
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }

            // Go up
            if (model.direction == Direction.UP 
                    && insideRequests[model.currentFloor] == 0 
                    && outsideRequests[model.currentFloor] == Direction.IDLE
                    && aboveRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor++));
                continue;
            }

            // Go down
            if (model.direction == Direction.DOWN
                    && insideRequests[model.currentFloor] == 0 
                    && outsideRequests[model.currentFloor] == Direction.IDLE
                    && belowRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor--));
                continue;
            }

            // Change direction and go down
            if (model.direction == Direction.UP
                    && insideRequests[model.currentFloor] == 0 
                    && outsideRequests[model.currentFloor] == Direction.IDLE
                    && belowRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor--, Direction.DOWN));
                continue;
            }

            // Change direction and go up
            if (model.direction == Direction.DOWN
                    && insideRequests[model.currentFloor] == 0 
                    && outsideRequests[model.currentFloor] == Direction.IDLE
                    && aboveRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor++, Direction.UP));
                continue;
            }
        }
        threadAlreadyRunning = false;
    }
    
    private boolean checkRequests() {
        for (int i = 0;i < insideRequests.length; i++) {
            if (insideRequests[i] == 1 || outsideRequests[i] != Direction.IDLE) {
                return true;
            }
        }
        return false;
    }
    
    private boolean aboveRequests(int currentFloor) {
        for (int i = currentFloor+1;i < insideRequests.length; i++) {
            if (insideRequests[i] == 1 || outsideRequests[i] != Direction.IDLE) {
                return true;
            }
        }
        return false;
    }
    
    private boolean belowRequests(int currentFloor) {
        for (int i = currentFloor-1;i >= 0; i--) {
            if (insideRequests[i] == 1 || outsideRequests[i] != Direction.IDLE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void notify(Event e) {
        ControlEvent event = (ControlEvent) e;
        event.buttonNum--;
        if (event.type.equals(OUTSIDE)) {
            if (event.up) {
                outsideRequests[event.buttonNum] = Direction.UP;
            } else {
                outsideRequests[event.buttonNum] = Direction.DOWN;
            }
        } else {
            insideRequests[event.buttonNum] = 1;
        }
        if(!threadAlreadyRunning){
            (new Thread(this)).start();
        }
    }
}
