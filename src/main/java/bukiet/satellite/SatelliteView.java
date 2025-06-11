package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SatelliteView extends JComponent {
    private BufferedImage[][] images = new BufferedImage[3][3];
    int offset = 300;
    //x, y offset - draw based off that
    //as you drag change offset

    public void setImage(BufferedImage image, int row, int col) {

        images[row][col] = image;
        repaint();
    }

    public BufferedImage getImage(int row, int col) {

        return images[row][col];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3 ; col++) {
                int x = col * offset;
                int y = row * offset;
                g.drawImage(images[row][col], x, y, null);
            }
        }
    }
}
