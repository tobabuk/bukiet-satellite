package bukiet.satellite;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class SatelliteController {
    private final JLabel imageLabel;
    private final SatelliteService service;
    private final String apiKey;

    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;

    }



    public void display(double lat, double lon, String date) {
        try {
            ResponseBody body = service.satelliteNow(
                    lat, lon, date, 0.2, apiKey
            ).blockingGet();

            if (body != null) {
                try (InputStream inputStream = body.byteStream()) {
                    BufferedImage image = ImageIO.read(inputStream);
                    if (image != null) {
                        ImageIcon thumbnail = new ImageIcon(image);
                        SwingUtilities.invokeLater(() -> imageLabel.setIcon(thumbnail));
                    } else {
                        System.err.println("Can't download image.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
