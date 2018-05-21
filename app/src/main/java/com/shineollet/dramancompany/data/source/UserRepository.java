package com.shineollet.dramancompany.data.source;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;
import com.shineollet.dramancompany.di.Qualifier.Local;
import com.shineollet.dramancompany.di.Qualifier.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

/**
 *
 */
@Singleton
public class UserRepository implements UserDataSource {

    private final UserDataSource mUserRemoteDataSource;

    private final UserDataSource mUserLocalDataSource;

    @Inject
    public UserRepository(@Remote UserDataSource mUserRemoteDataSource,
                          @Local UserDataSource mUserLocalDataSource) {
        this.mUserRemoteDataSource = mUserRemoteDataSource;
        this.mUserLocalDataSource = mUserLocalDataSource;
    }

    /**
     * remote 사용자 목록 api call
     *
     * @param query
     * @param page
     * @return
     */
    @Override
    public Single<Response<UserResponse>> getUsers(String query, int page) {
        return mUserRemoteDataSource.getUsers(query, page);
    }

    @Override
    public Maybe<FavoriteUser> getUserById(int id) {
        return mUserLocalDataSource.getUserById(id);
    }

    /**
     * local 사용자 목록
     *
     * @return
     */
    @Override
    public Flowable<List<FavoriteUser>> getUsers() {
        return mUserLocalDataSource.getUsers();
    }

    /**
     * 즐겨 찾기 사용자 저장
     *
     * @param userItem
     */
    @Override
    public void saveUser(@NonNull Object userItem) {
        //저장할 때 userItem -> favoriteUSer 로 변경
        mUserLocalDataSource.saveUser(userItem);
    }

    /**
     * 즐겨 찾기 사용자 삭제
     *
     * @param userItem
     */
    @Override
    public void deleteUser(@NonNull Object userItem) {
        mUserLocalDataSource.deleteUser(userItem);
    }
}
