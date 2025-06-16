package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SatelliteView extends JComponent {
    private BufferedImage[][] images = new BufferedImage[3][3];
    int widthHeight = 256;
    int xOffset =0;
    int yOffset = 0;

    //x, y offset - draw based off that
    //as you drag change offset

    public void setImage(BufferedImage image, int row, int col) {

        images[row][col] = image;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.translate(xOffset, yOffset);
        //offset for x and y
        //g.translate based on offset x and y

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = col * widthHeight;
                int y = row * widthHeight;
                g.drawImage(images[row][col], x, y, null);
            }
        }
    }
}
