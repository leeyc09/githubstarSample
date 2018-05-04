package com.shineollet.dramancompany.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;

public class UserRepository implements UserDataSource {

    @Nullable
    private static UserRepository INSTANCE = null;

    @NonNull
    private final UserDataSource mUserRemoteDataSource;

    @NonNull
    private final UserDataSource mUserLocalDataSource;

    public UserRepository(@NonNull UserDataSource mUserRemoteDataSource, @NonNull UserDataSource mUserLocalDataSource) {
        this.mUserRemoteDataSource = mUserRemoteDataSource;
        this.mUserLocalDataSource = mUserLocalDataSource;
    }


    public static UserRepository getInstance(@NonNull UserDataSource mUserRemoteDataSource, @NonNull UserDataSource mUserLocalDataSource) {
        if (null == INSTANCE) {
            synchronized (UserRepository.class) {
                if (null == INSTANCE) {
                    INSTANCE = new UserRepository(mUserRemoteDataSource, mUserLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 새로운 인스턴스를 생성할 때 불려진다.
     */
    public static void destroyInstance() {
        INSTANCE = null;
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
