/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package elevator.control;

import elevator.Event;

/**
 *
 * @author usuario
 */
public class ControlEvent extends Event {
    public int buttonNum;
    public boolean up;
    public ControlEventType type;
    
    // OUTSIDE REQUEST
    public ControlEvent(int buttonNum, boolean up) {
        super(EventType.Control);
        this.buttonNum = buttonNum;
        this.up = up;
        this.type = ControlEventType.OUTSIDE;
    }
    
    // INSIDE REQUEST
    public ControlEvent(int buttonNum) {
        super(EventType.Control);
        this.buttonNum = buttonNum;
        this.type = ControlEventType.INSIDE;
    }
    
    enum ControlEventType {
        OUTSIDE,
        INSIDE
    }
}
