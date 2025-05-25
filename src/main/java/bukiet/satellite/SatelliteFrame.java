package bukiet.satellite;


import javax.swing.*;
import java.awt.*;

public class SatelliteFrame  extends JFrame {

    SatelliteService service;
    public SatelliteFrame() {
        setTitle("Satellite Map");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));
        JLabel imageLabel = new JLabel();
        add(imageLabel);
        String apiKey = new com.andrewoid.apikeys.ApiKey().get();
        SatelliteClient client = new SatelliteClient();
        SatelliteService service = client.createService();

        SatelliteController controller = new SatelliteController(service, imageLabel, apiKey);

        controller.display();
    }

    public static void main(String[] args) {
        new SatelliteFrame().setVisible(true);
    }

}