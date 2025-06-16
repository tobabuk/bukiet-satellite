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

    private Point dragStart;

    private final SatelliteService service;
    private final String apiKey;
    private final SatelliteController controller;
    private final SatelliteView view = new SatelliteView();

    public SatelliteFrame() {

        apiKey = new com.andrewoid.apikeys.ApiKey().get();
        SatelliteClient client = new SatelliteClient();
        service = client.createService();


        controller = new SatelliteController(service, imageLabel, apiKey, view);

        setTitle("Satellite Map");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Lat:"));
        inputPanel.add(latField);
        inputPanel.add(new JLabel("Lon:"));
        inputPanel.add(lonField);
        inputPanel.add(goButton);
        add(inputPanel, BorderLayout.NORTH);

        add(view, BorderLayout.CENTER);


        view.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }
        });

        view.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int offset = 300;
                int x = e.getX() - dragStart.x;
                int y = e.getY() - dragStart.y;

                double newLat = lat - y * offset;
                double newLon = lon + x * offset;

                dragStart = e.getPoint();
                controller.Move(x, y);
            }


        });

        goButton.addActionListener(e -> {
            try {
                lat = Double.parseDouble(latField.getText());
                lon = Double.parseDouble(lonField.getText());
                lat = Math.max(-85.0, Math.min(85.0, lat));
                lon = ((lon + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
                controller.display(lat, lon);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid latitude or longitude input." + ex.getStackTrace());
            }
        });

    }

    public static void main(String[] args) {
        new SatelliteFrame().setVisible(true);
    }
}
