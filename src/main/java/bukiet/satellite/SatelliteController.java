package bukiet.satellite;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SatelliteController {

    private final JLabel imageLabel;
    private final SatelliteService service;
    private final String apiKey;
    private Disposable currentRequest;

    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;

    }


    public void display(double lat, double lon, String date) {

        currentRequest = service.satelliteNow(lat, lon, date, 0.1, apiKey, false)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        body -> loadImage(body, apiKey),
                        error -> SwingUtilities.invokeLater(() -> {
                            imageLabel.setText("Error loading image");
                            System.err.println("Request failed: " + error.getMessage());
                        })
                );
    }

    private void loadImage(ResponseBody body, String apiKey) {
        try (InputStream stream = body.byteStream()) {
            BufferedImage image = ImageIO.read(stream);
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                SwingUtilities.invokeLater(() -> {
                    imageLabel.setIcon(icon);
                    imageLabel.setText("");
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> imageLabel.setText("Failed to load"));
            e.printStackTrace();
        }
    }

}
