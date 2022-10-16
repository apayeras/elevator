/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        super(EventType.Model);
        this.model = model;
        this.type = ViewEventType.STATE;
    }
    
    enum ViewEventType {
        STATE,
        REQUESTS
    }
}
