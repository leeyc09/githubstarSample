package com.shineollet.dramancompany.data.source.local;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.UserDataSource;
import com.shineollet.dramancompany.data.source.local.dao.FavoriteUserDao;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserLocalDataSource implements UserDataSource {

    private static volatile UserLocalDataSource INSTANCE;


    private FavoriteUserDao mFavoriteUserDao;


    public UserLocalDataSource(@NonNull FavoriteUserDao favoriteUserDao) {
        mFavoriteUserDao = favoriteUserDao;
    }

    public static UserLocalDataSource getInstance(@NonNull FavoriteUserDao favoriteUserDao) {
        if (null == INSTANCE) {
            synchronized (UserLocalDataSource.class) {
                if (null == INSTANCE) {
                    INSTANCE = new UserLocalDataSource(favoriteUserDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<Response<UserResponse>> getUsers(String query, int page) {
        //not use, only use online
        return null;
    }

    @Override
    public Maybe<FavoriteUser> getUserById(int id) {
        //not use, only use online
        return mFavoriteUserDao.getUserById(id);
    }


    @Override
    public Flowable<List<FavoriteUser>> getUsers() {
        return mFavoriteUserDao.getAllFavoriteUser();
    }

    @Override
    public void saveUser(@NonNull Object o) {

//        public void addUser(final DatabaseCallback databaseCallback, final String firstName, final String lastName) {
        Completable.fromAction(() -> {
            if (o instanceof UserItem) {
                FavoriteUser favoriteUser = new FavoriteUser(
                        ((UserItem) o).getId(),
                        ((UserItem) o).getLogin(),
                        ((UserItem) o).getAvatarUrl(),
                        ((UserItem) o).getHtmlUrl());
                mFavoriteUserDao.insert(favoriteUser);
            } else if (o instanceof FavoriteUser) {
                mFavoriteUserDao.insert((FavoriteUser) o);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
//                    databaseCallback.onUserAdded();
            }

            @Override
            public void onError(Throwable e) {
//                    databaseCallback.onDataNotAvailable();
            }
        });
//        }

    }

    @Override
    public void deleteUser(@NonNull Object o) {

        Completable.fromAction(() -> {
            if (o instanceof UserItem) {
                FavoriteUser favoriteUser = new FavoriteUser(
                        ((UserItem) o).getId(),
                        ((UserItem) o).getLogin(),
                        ((UserItem) o).getAvatarUrl(),
                        ((UserItem) o).getHtmlUrl());
                mFavoriteUserDao.deleteUser(favoriteUser);
            } else if (o instanceof FavoriteUser) {
                mFavoriteUserDao.deleteUser((FavoriteUser) o);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }
        });

    }
}
