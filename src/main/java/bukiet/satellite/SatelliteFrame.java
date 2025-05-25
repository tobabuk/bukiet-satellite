package bukiet.satellite;


import javax.swing.*;

public class SatelliteFrame  extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Satellite Map");
        JLabel imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        String apiKey = new com.andrewoid.apikeys.ApiKey().get();
        SatelliteService service = RetrofitClient.createService();
        SatelliteController controller = new SatelliteController(service, imageLabel, apiKey);

        controller.display();
    }
}
