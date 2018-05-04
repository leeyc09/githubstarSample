package com.shineollet.dramancompany.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shineollet.dramancompany.data.source.local.dao.FavoriteUserDao;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;


/**
 * ROOM 즐겨찾기 사용자 DB
 */
@Database(entities = FavoriteUser.class, version = localDataConst.DB_VERSION, exportSchema = false)
public abstract class LocalDataBase extends RoomDatabase {

    private static LocalDataBase INSTANCE;

    public static LocalDataBase getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (LocalDataBase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDataBase.class, localDataConst.DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteUserDao favoriteUserDao();

}
