package bukiet.satellite;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;

public class SatelliteController {

    private final JLabel imageLabel;
    private final SatelliteService service;
    private final String apiKey;
    private Disposable currentRequest;
    private Disposable leftRequest;
    private Disposable rightRequest;
    private final double dim = .025;
        private SatelliteView view;
    ImageIcon[] icon = new ImageIcon[3];
    int number = 0;
    private BufferedImage[] images = new BufferedImage[3];


    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey, SatelliteView view) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;
        this.view = view;
    }

        public void display(double lat, double lon) {
            currentRequest = service.satelliteNow(lat, lon, dim, apiKey, false)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            body -> loadImage(body, 1),
                            error -> SwingUtilities.invokeLater(() -> {
                                imageLabel.setText("Error loading image");
                                System.err.println("Request failed: " + error.getMessage());
                            })
                    );
            leftRequest = service.satelliteNow(lat - (dim / 2), lon, dim, apiKey, false)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            body -> loadImage(body, 0),
                            error -> SwingUtilities.invokeLater(() -> {
                                imageLabel.setText("Error loading image");
                                System.err.println("Request failed: " + error.getMessage());
                            })
                    );

            rightRequest = service.satelliteNow(lat + (dim / 2), lon, dim, apiKey, false)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            body -> loadImage(body, 2),
                            error -> SwingUtilities.invokeLater(() -> {
                                imageLabel.setText("Error loading image");
                                System.err.println("Request failed: " + error.getMessage());
                            })
                    );
    }


    private void loadImage(ResponseBody body, int count) {
        try (InputStream stream = body.byteStream()) {
            BufferedImage image = ImageIO.read(stream);
            if (image != null) {
                images[count] = image;
                number++;
            }
            SwingUtilities.invokeLater(() -> {
                view.setImage(image, count);
            }); }



    catch (Exception e) {
            SwingUtilities.invokeLater(() -> imageLabel.setText("Failed to load"));
            e.printStackTrace();
        }
    }

}
