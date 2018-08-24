package com.example.student.coinrx;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 30;
    private static OkHttpClient okHttpClient;
    private static String BASE_URL = "https://pro-api.coinmarketcap.com";
    public static Retrofit getClient() {
        if (okHttpClient == null)
            initOkHttp();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder().connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder().addHeader("Accept", "application/json").addHeader("Content-Type", "application/json");
                requestBuilder.addHeader("X-CMC_PRO_API_KEY", "ca4eed96-94bd-4cab-87a4-1ff82527ea6e");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();
    }
}
