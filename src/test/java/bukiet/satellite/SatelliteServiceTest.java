package bukiet.satellite;

import com.andrewoid.apikeys.ApiKey;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SatelliteServiceTest {

    @Test
    public void satelliteNow() {

        SatelliteService service = new SatelliteClient().createService();
        ApiKey apikey = new ApiKey();
        String keyString = apikey.get();
        ResponseBody body = service
                .satelliteNow(40.7128, -74.0060, 0.2, keyString, false)
                .blockingGet();

        assertNotNull(body);
    }

}