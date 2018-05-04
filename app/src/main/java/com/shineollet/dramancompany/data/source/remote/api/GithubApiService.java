package com.shineollet.dramancompany.data.source.remote.api;

import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApiService {
    String ENDPOINT = "https://api.github.com";

    @GET("search/users")
    Single<Response<UserResponse>> queryUsers(@Query("q") String query, @Query("per_page") int limit, @Query("page") int page);

}
