package bukiet.satellite;

import com.andrewoid.apikeys.ApiKey;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SatelliteSeviceTest {

    @Test
    public void satelliteNow() {

        SatelliteService service = new SatelliteClient().createService();
        ApiKey apikey = new ApiKey();
        String keyString = apikey.get();
        ResponseBody body = service
                .satelliteNow(40.7128, -74.0060, "2025-05-17", 0.2, keyString)
                .blockingGet();

        assertNotNull(body);
    }

}