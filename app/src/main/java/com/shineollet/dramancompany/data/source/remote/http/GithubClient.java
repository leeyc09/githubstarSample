package com.shineollet.dramancompany.data.source.remote.http;

import com.shineollet.dramancompany.data.source.remote.api.GithubApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    public static GithubApiService githubApiBuilder() {
        return new Retrofit.Builder()
                .baseUrl(GithubApiService.ENDPOINT)
                .client(HttpClient.createHttpClientBuilder())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubApiService.class);
    }
}
