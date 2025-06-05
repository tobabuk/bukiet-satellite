package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SatelliteView extends JComponent {
    private BufferedImage[] images = new BufferedImage[3];



    public void setImage(BufferedImage image, int ind) {
       images[ind] = image;
       repaint();
    }

    public BufferedImage getImage(int ind){
        return images[ind];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (BufferedImage image : images) {
        g.drawImage(image, 0, 0, null);
        }
    }
}
