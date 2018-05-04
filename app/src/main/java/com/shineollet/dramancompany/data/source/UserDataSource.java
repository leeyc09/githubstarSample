package com.shineollet.dramancompany.data.source;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

public interface UserDataSource {

    Single<Response<UserResponse>> getUsers(String query, int page);

    Maybe<FavoriteUser> getUserById(int id);

    Flowable<List<FavoriteUser>> getUsers();

    void saveUser(@NonNull Object userItem);

    void deleteUser(@NonNull Object userItem);
}
