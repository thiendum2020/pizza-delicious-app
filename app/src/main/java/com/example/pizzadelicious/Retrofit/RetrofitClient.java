package com.example.pizzadelicious.Retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitScalars;
    private static Retrofit retrofitGson;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static Retrofit getRetrofitInstanceScalars(String BASE_URL){
        if(retrofitScalars == null){
            retrofitScalars = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

        }
        return retrofitScalars;
    }


    public static Retrofit getRetrofitInstanceGson(String BASE_URL){
        if(retrofitGson == null){
            retrofitGson = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitGson;
    }

}
