package com.shineollet.dramancompany.ui.search;

import android.support.annotation.NonNull;

import com.shineollet.dramancompany.BasePresenter;
import com.shineollet.dramancompany.BaseView;

public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void setRefresh(boolean isRefreshing);

        void renderList();

        void showError(@NonNull String msg);

    }

    interface Presenter extends BasePresenter {

        void refresh(String query);

        void clear();

        void nextUserLoad(String query);

        int getUserCount();

        void SearchUser(String query);

        Object getUserItems();

        Object getUserItem(int position);

        void addFavoriteUser(Object userItem);

        void deleteFavoriteUser(Object userItem);

    }
}