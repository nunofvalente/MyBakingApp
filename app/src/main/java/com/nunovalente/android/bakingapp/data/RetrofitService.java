package com.nunovalente.android.bakingapp.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private final static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private final static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging);

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(WebConfig.getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    public static WebService getInterface() {
        return retrofit.create(WebService.class);
    }
}
