package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SatelliteView extends JComponent {
    private BufferedImage[] images = new BufferedImage[9];
    int height = getHeight();
    int width = getWidth();
    int offset = 300;
//x, y offset - draw based off that
    //as you drag change offset
    public void setImage(BufferedImage image, int ind) {
       images[ind] = image;
       repaint();
    }

    public BufferedImage getImage(int ind) {
        return images[ind];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0 ; i < images.length ; i++) {
            g.drawImage(images[i], i * offset, 0 , null);
        }
    }
}
