package bukiet.satellite;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class SatelliteClient {


            public static SatelliteService createService() {
                OkHttpClient client = new OkHttpClient.Builder().build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.nasa.gov/")
                        .client(client)
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build();

                return retrofit.create(SatelliteService.class);
            }
        }




