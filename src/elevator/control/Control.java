package elevator.control;

import elevator.Elevator;
import elevator.Event;
import elevator.EventListener;
import static elevator.control.ControlEvent.ControlEventType.*;
import elevator.model.Model;
import elevator.model.ModelEvent;
import elevator.view.ViewEvent;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control extends Thread implements EventListener {
    private final Elevator elevator;
    private static final int NUM_FLOORS = 4;
    private static boolean [] insideRequests = new boolean[NUM_FLOORS];
    private static boolean [][] outsideRequests = new boolean[NUM_FLOORS][2];
    private static boolean wait;
    private static boolean threadAlreadyRunning;
    
    public Control(Elevator elevator) {
        this.elevator = elevator;
    }
    
    @Override
    public void run(){
        threadAlreadyRunning = true;
        Model model = elevator.getModel();
        while (checkRequests() || model.openedDoors) {
        
            // Open door if floor has any request
            if (!model.openedDoors
                && (insideRequests[model.currentFloor] || anyOutsideRequest(model.currentFloor, model.upDirection))) {
                removeRequest(model.currentFloor, model.upDirection);
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
            if (model.upDirection 
                    && !insideRequests[model.currentFloor] 
                    && !outsideRequests[model.currentFloor][1]
                    && aboveRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor++));
                continue;
            }

            // Go down
            if (!model.upDirection
                    && !insideRequests[model.currentFloor] 
                    && !outsideRequests[model.currentFloor][0]
                    && belowRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor--));
                continue;
            }

            // Change direction and go down
            if (model.upDirection
                    && !insideRequests[model.currentFloor]
                    && !(outsideRequests[model.currentFloor][0] || outsideRequests[model.currentFloor][1])
                    && belowRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor--, false));
                continue;
            }

            // Change direction and go up
            if (!model.upDirection
                    && !insideRequests[model.currentFloor]
                    && !(outsideRequests[model.currentFloor][0] || outsideRequests[model.currentFloor][1])
                    && aboveRequests(model.currentFloor)) {
                elevator.notify(new ModelEvent(model.currentFloor++, true));
            }
        }
        threadAlreadyRunning = false;
    }
    
    private boolean checkRequests() {
        for (int i = 0;i < insideRequests.length; i++) {
            if (insideRequests[i] || outsideRequests[i][0] || outsideRequests[i][1]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean aboveRequests(int currentFloor) {
        for (int i = currentFloor+1;i < insideRequests.length; i++) {
            if (insideRequests[i] || outsideRequests[i][0] || outsideRequests[i][1]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean belowRequests(int currentFloor) {
        for (int i = currentFloor-1;i >= 0; i--) {
            if (insideRequests[i] || outsideRequests[i][0] || outsideRequests[i][1]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean anyOutsideRequest(int currentFloor, boolean upDirection) {
        return outsideRequests[currentFloor][upDirection? 1 : 0]
                || (outsideRequests[currentFloor][0] && !aboveRequests(currentFloor)) || (outsideRequests[currentFloor][1] && !belowRequests(currentFloor));
    }
    
    private void removeRequest(int currentFloor, boolean upDirection) {
        if (outsideRequests[currentFloor][upDirection? 1 : 0]) {
            outsideRequests[currentFloor][upDirection? 1 : 0] = false;
            elevator.notify(new ViewEvent(currentFloor, upDirection));
        } else if (outsideRequests[currentFloor][0]) {
            outsideRequests[currentFloor][0] = false;
            elevator.notify(new ViewEvent(currentFloor, false));
        } else if (outsideRequests[currentFloor][1]) {
            outsideRequests[currentFloor][1] = false;
            elevator.notify(new ViewEvent(currentFloor, true));
        }
        
        if (insideRequests[currentFloor]) {
            insideRequests[currentFloor] = false;
            elevator.notify(new ViewEvent(currentFloor));
        }
    }

    @Override
    public void notify(Event e) {
        ControlEvent event = (ControlEvent) e;
        event.buttonNum--;
        if (event.type.equals(OUTSIDE)) {
            if (event.up) {
                outsideRequests[event.buttonNum][1] = true;
            } else {
                outsideRequests[event.buttonNum][0] = true;
            }
        } else {
            insideRequests[event.buttonNum] = true;
        }
        if(!threadAlreadyRunning){
            (new Thread(this)).start();
        }
    }
}
