package com.shineollet.dramancompany;

import android.content.Context;
import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.UserRepository;
import com.shineollet.dramancompany.data.source.local.LocalDataBase;
import com.shineollet.dramancompany.data.source.local.UserLocalDataSource;
import com.shineollet.dramancompany.data.source.remote.UserRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static UserRepository provideUserRepository(@NonNull Context context) {
        checkNotNull(context);

        LocalDataBase database = LocalDataBase.getInstance(context);

        return UserRepository.getInstance(UserRemoteDataSource.getInstance(),
                UserLocalDataSource.getInstance(database.favoriteUserDao()));
    }
}
