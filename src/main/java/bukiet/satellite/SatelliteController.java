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

    public SatelliteController(SatelliteService service, JLabel imageLabel, String apiKey) {
        this.service = service;
        this.imageLabel = imageLabel;
        this.apiKey = apiKey;
    }

    public void display() {
        Disposable disposable = service.satelliteNow(40.7128, -74.0060, "2025-05-17", 0.2, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        responseBody -> {
                            try (InputStream inputStream = responseBody.byteStream()) {
                                BufferedImage image = ImageIO.read(inputStream);
                                if (image != null) {
                                    imageLabel.setIcon(new ImageIcon(image));
                                } else {
                                    System.err.println("Could not decode image.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        },
                        throwable -> {
                            System.err.println("Error fetching satellite image:");
                            throwable.printStackTrace();
                        }
                );
    }
}
