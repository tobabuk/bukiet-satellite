package bukiet.satellite;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.Arrays;

public class SatelliteController {

    private final JLabel imageLabel;
    private final SatelliteService service;
    private final String apiKey;
    private Disposable currentRequest;
    private final double dim = .025;
    private SatelliteView view;
    private BufferedImage[][] images = new BufferedImage[3][3];
    private int row;
    private int col;

    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey, SatelliteView view) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;
        this.view = view;
    }

        public void display(double lat, double lon) {
            int number = 0;

            for (row = 0; row < 3; row++) {

                for (col = 0; col < 3; col++) {
                    int offsetRow = row - 1;
                    int offsetCol = col - 1;
                    double newLat = lat + (offsetRow * dim);
                    double newLon = lon + (offsetCol * dim);
                    final int count = number;
                    final int c = col;
                    final int r = row;
                    currentRequest = service.satelliteNow(newLat, newLon, dim, apiKey, false)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    body -> loadImage(body, count, r, c),
                                    error -> SwingUtilities.invokeLater(() -> {
                                        imageLabel.setText("Error loading image");
                                        System.err.println("Request failed: " + error.getMessage());
                                    })
                            );
                    number++;
                }
            }
        }
    //resize to 256x256

    private void loadImage(ResponseBody body, int count, int row, int col) {
        try (InputStream stream = body.byteStream()) {
            BufferedImage image = ImageIO.read(stream);
            if (image != null) {
                Image scaledImage = null;
                scaledImage = image.getScaledInstance(300, -1, Image.SCALE_DEFAULT);
                BufferedImage bufferedScaled = new BufferedImage(
                        scaledImage.getWidth(null),
                        scaledImage.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = bufferedScaled.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();
                images[row][col] = bufferedScaled;

                SwingUtilities.invokeLater(() -> {
                    view.setImage(bufferedScaled, row, col);
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> imageLabel.setText("Failed to load"));
            e.printStackTrace();
        }
    }

}
