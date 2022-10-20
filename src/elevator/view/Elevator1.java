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

public class Elevator1 extends JPanel{
    private BufferedImage bima;
    private Image img;
    private Color cream = new Color(96, 70, 59);
    private final int floorHeight = 150;
    private int currentPosition;
    private int numFloor;

    public Elevator1(){
        this.currentPosition = 0; 
        try {
            img = ImageIO.read(getClass().getResource("img\\elevator-closed.jpg")).getScaledInstance(109, 150, BufferedImage.SCALE_DEFAULT);
        } catch (IOException ex) {
            Logger.getLogger(Elevator1.class.getName()).log(Level.SEVERE, null, ex);
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
                bima_graphics.drawImage(img, 0, currentPosition, null);
                
            }
        }
        gr.drawImage(bima, 0, 0, this);

    }
    
    public void move(int numFloor){
        
        if(this.numFloor < numFloor){
            for(; this.currentPosition < (numFloor-1)*floorHeight; this.currentPosition+=1){
                bima = null;
                this.repaint();  
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
            }
        } else {
            for(; this.currentPosition > (numFloor-1)*floorHeight; this.currentPosition-=1){
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
