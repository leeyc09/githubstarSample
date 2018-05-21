package com.shineollet.dramancompany.data.source.local;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.UserDataSource;
import com.shineollet.dramancompany.data.source.local.dao.FavoriteUserDao;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * 싱글턴 어노테이션을 추가하면서 싱글톤을 위하 인스턴스 생성 부분을 제거함.
 */
@Singleton
public class UserLocalDataSource implements UserDataSource {

    private FavoriteUserDao mFavoriteUserDao;

    @Inject
    public UserLocalDataSource(@NonNull FavoriteUserDao favoriteUserDao) {
        mFavoriteUserDao = favoriteUserDao;
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
