package com.shineollet.dramancompany.data.source.remote.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

public class HttpClient {

    public static OkHttpClient createHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.addInterceptor(LoggingInterceptor());

        return builder.build();
    }

    private static HttpLoggingInterceptor LoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.d("API LOG %s", message));
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }
}
