package com.shineollet.dramancompany.data.source.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.shineollet.dramancompany.data.source.local.localDataConst;


/**
 * local 즐겨찾기 한 사용자 테이블
 * Created by youngchanlee
 */
@Entity(tableName = localDataConst.FAVORITEUSER_TABLE)
public class FavoriteUser {

    boolean isHeader = false;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = localDataConst.FAVORITEUSER_ID)
    private int id;
    @ColumnInfo(name = localDataConst.FAVORITEUSER_LOGIN)
    private String userName;
    @ColumnInfo(name = localDataConst.FAVORITEUSER_AVATAR_URL)
    private String avatar_url;
    @ColumnInfo(name = localDataConst.FAVORITEUSER_HTML_URL)
    private String html_url;

    public FavoriteUser(@NonNull int id, String userName, String avatar_url, String html_url) {
        this.id = id;
        this.userName = userName;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
    }


    @NonNull
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }
}
