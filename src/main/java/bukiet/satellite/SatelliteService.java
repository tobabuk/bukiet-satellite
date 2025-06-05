package bukiet.satellite;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Call;
import retrofit2.http.GET;
import okhttp3.ResponseBody;

import retrofit2.http.Query;

public interface SatelliteService {
    @GET("/planetary/earth/imagery")
    Single<ResponseBody> satelliteNow(
            @Query("lat") double lat,
            @Query("lon") double lon,
         //  @Query("date") String date,
            @Query("dim") double dim,
            @Query("api_key") String apikey,
            @Query("cloud_score") boolean cloudScore


    );
}
