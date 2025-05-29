package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SatelliteFrame extends JFrame {

    private final JLabel imageLabel = new JLabel();
    private double lat = 40.7128;
    private double lon = -74.0060;
    private final String date = "2025-05-17";
    private Point dragStart;

    private final SatelliteService service;
    private final String apiKey;
    private final SatelliteController controller;

    public SatelliteFrame() {

        apiKey = new com.andrewoid.apikeys.ApiKey().get();
        SatelliteClient client = new SatelliteClient();
        service = client.createService();


        controller = new SatelliteController(service, imageLabel, apiKey);


        setTitle("Satellite Map");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.CENTER);


        imagePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }
        });

        imagePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() - dragStart.x;
                int y = e.getY() - dragStart.y;

                lat -= y * 0.01;
                lon += x * 0.01;

                dragStart = e.getPoint();
                controller.display(lat, lon, date);
            }
        });
    }

    public static void main(String[] args) {
        new SatelliteFrame().setVisible(true);
    }
}
