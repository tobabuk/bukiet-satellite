package bukiet.satellite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SatelliteFrame extends JFrame {

    private final JLabel imageLabel = new JLabel();
    private final JLabel latLabel = new JLabel();
    private final JLabel lonLabel = new JLabel();
    private final JTextField latField = new JTextField("40.7128");
    private final JTextField lonField = new JTextField("-74.0060");
    private final JButton goButton = new JButton("Start");
    private double lat = 40.7128;
    private double lon = -74.0060;

    private final String date = "2024-05-17";
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
        latLabel.setText(String.format("Current Latitude: %.4f", lat));
        lonLabel.setText(String.format("Current Longitude: %.4f", lon));
        JPanel coordPanel = new JPanel(new GridLayout(2, 1));
        coordPanel.add(latLabel);
        coordPanel.add(lonLabel);
        imagePanel.add(coordPanel, BorderLayout.SOUTH);
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Lat:"));
        inputPanel.add(latField);
        inputPanel.add(new JLabel("Lon:"));
        inputPanel.add(lonField);
        inputPanel.add(goButton);
        add(inputPanel, BorderLayout.NORTH);

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
                lat = Math.max(-85.0, Math.min(85.0, lat));
                lon = ((lon + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;

                latLabel.setText(String.format("Current Latitude: %.4f", lat));
                lonLabel.setText(String.format("Current Longitude: %.4f", lon));
                dragStart = e.getPoint();
                controller.display(lat, lon, date);
            }
        });

        goButton.addActionListener(e -> {
            try {
                lat = Double.parseDouble(latField.getText());
                lon = Double.parseDouble(lonField.getText());


                lat = Math.max(-85.0, Math.min(85.0, lat));
                lon = ((lon + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;

                latLabel.setText(String.format("Latitude: %.4f", lat));
                lonLabel.setText(String.format("Longitude: %.4f", lon));

                controller.display(lat, lon, date);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid latitude or longitude input.");
            }
        });

    }

    public static void main(String[] args) {
        new SatelliteFrame().setVisible(true);
    }
}
