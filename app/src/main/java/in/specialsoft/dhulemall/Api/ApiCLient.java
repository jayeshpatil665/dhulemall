package in.specialsoft.dhulemall.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCLient {
    private static Retrofit retrofit = null;
    private static String BASE_URL="https://dhulemall.ml/";



    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //  Log.i("BAseUrl", Config.BASE_URL);
        return retrofit;
    }

}
