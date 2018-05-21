package com.shineollet.dramancompany.data.source.remote;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.UserDataSource;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.api.GithubApiService;
import com.shineollet.dramancompany.data.source.remote.http.GithubClient;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class UserRemoteDataSource implements UserDataSource {

    private GithubApiService mRetrofitService;

    @Inject
    public UserRemoteDataSource() {
    }

    private void initSource() {
        if (mRetrofitService == null) {
            mRetrofitService = GithubClient.githubApiBuilder();
        }
    }

    @Override
    public Single<Response<UserResponse>> getUsers(String query, int page) {
        initSource();
        return mRetrofitService.queryUsers(query, 30, page);
    }

    @Deprecated
    @Override
    public Maybe<FavoriteUser> getUserById(int id) {
        // localdata 전용으로 쓰지 않음
        //not use;
        return null;
    }

    @Deprecated
    @Override
    public Flowable<List<FavoriteUser>> getUsers() {
        // localdata 전용으로 쓰지 않음
        // not use;
        return null;
    }

    @Deprecated
    @Override
    public void saveUser(@NonNull Object userItem) {
        // localdata 전용으로 쓰지 않음
        //not use;
    }

    @Deprecated
    @Override
    public void deleteUser(@NonNull Object userItem) {
        // localdata 전용으로 쓰지 않음
        //not use;
    }

}
