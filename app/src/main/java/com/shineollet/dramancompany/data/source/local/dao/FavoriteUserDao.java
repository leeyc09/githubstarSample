package com.shineollet.dramancompany.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.local.localDataConst;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface FavoriteUserDao {
    /**
     * 즐겨찾기한 전체 사용자를 불러온다.
     *
     * @return 전체 사용자
     */
    @Query("select * from " + localDataConst.FAVORITEUSER_TABLE + " order by " + localDataConst.FAVORITEUSER_LOGIN)
    Flowable<List<FavoriteUser>> getAllFavoriteUser();

    /**
     * 즐겨찾기 등록
     *
     * @param favoriteUser : 사용자 정보
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteUser favoriteUser);

    /**
     * 즐겨 찾기 사용자 삭제
     *
     * @param favoriteUser
     */
    @Delete
    void deleteUser(FavoriteUser favoriteUser);

    @Query("SELECT * FROM " + localDataConst.FAVORITEUSER_TABLE + " WHERE " + localDataConst.FAVORITEUSER_ID + " = :userId")
    Maybe<FavoriteUser> getUserById(int userId);


}
