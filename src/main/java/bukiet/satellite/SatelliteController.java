package bukiet.satellite;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private BufferedImage[] images = new BufferedImage[9];
    Image scaledImage;

    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey, SatelliteView view) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;
        this.view = view;
    }

        public void display(double lat, double lon) {
            int number = 0;

            for (int latstart = 0; latstart < 3; latstart++) {
                for (int lonstart = 0; lonstart < 3; lonstart++) {
                    double newLat = lat + (latstart * dim);
                    double newLon = lon + (lonstart * dim);
                    final int count = number;
                    currentRequest = service.satelliteNow(newLat, newLon, dim, apiKey, false)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    body -> loadImage(body, count),
                                    error -> SwingUtilities.invokeLater(() -> {
                                        imageLabel.setText("Error loading image");
                                        System.err.println("Request failed: " + error.getMessage());
                                    })
                            );
                    number++;
                }}
        }
//resize to 256x256
    //
    private void loadImage(ResponseBody body, int count) {
        try (InputStream stream = body.byteStream()) {
            BufferedImage image = ImageIO.read(stream);
            if (image != null) {
                images[count] = image;
               Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);

            }
                SwingUtilities.invokeLater(() -> {
                    view.setImage((BufferedImage) scaledImage, count);
                });
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> imageLabel.setText("Failed to load"));
            e.printStackTrace();
        }
    }

}
