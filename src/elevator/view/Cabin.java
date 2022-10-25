package elevator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Cabin extends JPanel{
    private BufferedImage bima;
    private Image closedDoor;
    private Image openedDoor;
    private Color cream = new Color(96, 70, 59);
    private final int floorHeight = 150;
    private int currentPosition;
    private int numFloor;
    private boolean doorStatus;

    public Cabin(){
        this.currentPosition = 0; 
        try {
            openedDoor = ImageIO.read(getClass().getResource("img\\elevator-opened.jpg")).getScaledInstance(109, 150, BufferedImage.SCALE_DEFAULT);
            closedDoor = ImageIO.read(getClass().getResource("img\\elevator-closed.jpg")).getScaledInstance(109, 150, BufferedImage.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(Cabin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    public void paint(Graphics gr) {   
        
        if (bima == null) {
            if (this.getWidth() > 0) {
                bima = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D  bima_graphics = bima.createGraphics();
                bima_graphics.setColor(cream);
                bima_graphics.fillRect(0, 0, bima.getWidth(), bima.getHeight());
                bima_graphics.drawImage(doorStatus ? openedDoor : closedDoor , 0, this.getHeight() - 150 - currentPosition, null);
            }
        }
        gr.drawImage(bima, 0, 0, this);

    }
    
    public void refreshCabin(boolean openedDoor, int numFloor){
        if(doorStatus != openedDoor){
            this.doorStatus = openedDoor;
            bima = null;
            this.repaint();  
        }
        
        if(this.numFloor < numFloor){
            for(; this.currentPosition < numFloor*floorHeight; this.currentPosition+=1){
                bima = null;
                this.repaint();  
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
            }
        } else {
            for(; this.currentPosition > numFloor*floorHeight; this.currentPosition-=1){
                bima = null;
                this.repaint(); 
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
            }
        }
        this.numFloor = numFloor;
    }
}
