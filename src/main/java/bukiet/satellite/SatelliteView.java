package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SatelliteView extends JComponent {
    SatelliteController controller;
    private BufferedImage[] images = new BufferedImage[3];
    private double lat = 40.7128;
    private double lon = -74.0060;
    private final double dim = .025;


    public void setImage(BufferedImage image, int ind) {
       images[ind] = image;
       repaint();
    }

    public BufferedImage getImage (int ind){
        return images[ind];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (BufferedImage image : images){
        g.drawImage(image, 0, 0, null);}
    }
}
