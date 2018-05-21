package com.shineollet.dramancompany.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shineollet.dramancompany.data.source.UserDataSource;
import com.shineollet.dramancompany.data.source.local.LocalDataBase;
import com.shineollet.dramancompany.data.source.local.UserLocalDataSource;
import com.shineollet.dramancompany.data.source.local.dao.FavoriteUserDao;
import com.shineollet.dramancompany.data.source.local.localDataConst;
import com.shineollet.dramancompany.data.source.remote.UserRemoteDataSource;
import com.shineollet.dramancompany.di.Qualifier.Local;
import com.shineollet.dramancompany.di.Qualifier.Remote;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 기존 mvp소스에서 injection 코드를 제거하는 부분
 * 레파지토리에서 필요하는 inject
 */
@Module
public abstract class UserRepositoryModule {

    @Singleton
    @Provides
    static LocalDataBase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), LocalDataBase.class, localDataConst.DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    static FavoriteUserDao provideFavoriteUserDao(LocalDataBase db) {
        return db.favoriteUserDao();
    }

    @Singleton
    @Binds
    @Local //같은 형태이기 때문에 쿼티파이로 구분해준다.
    abstract UserDataSource provideTasksLocalDataSource(UserLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract UserDataSource provideTasksRemoteDataSource(UserRemoteDataSource dataSource);
}
