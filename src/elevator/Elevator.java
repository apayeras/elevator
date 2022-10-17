package elevator;

import elevator.control.Control;
import elevator.model.Model;
import elevator.view.View;

public class Elevator implements EventListener {
    
    private Model model;
    private Control control;
    private View view;

    public static void main(String[] args) {
        (new Elevator()).init();
    }
    
    private void init(){
        this.model = new Model(this);
        this.control = new Control(this);
        //this.view = new View(this);
    }
    
    public Model getModel() {
        return this.model;
    } 
    
    @Override
    public void notify(Event e) {
        switch (e.getEventType()){
            case Model -> {
                model.notify(e);
            }
            case View -> {
                //view.notify(e);
            }
            case Control -> {
                control.notify(e);
            }
        }
    }
}
