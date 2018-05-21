package com.shineollet.dramancompany.ui.search.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.shineollet.dramancompany.data.source.UserRepository;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.ui.search.SearchContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalUserPresenter implements SearchContract.Presenter {

    @NonNull
    private final UserRepository mUserRepository;

    @NonNull
    private SearchContract.View mUserView;

    private List<FavoriteUser> mUsers;
    private List<FavoriteUser> mUsersIncludeHead;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public LocalUserPresenter(@NonNull UserRepository mUserRepository, LocalUserFragment mUserView) {
        this.mUserRepository = checkNotNull(mUserRepository, "UserRepository cannot be null");
        this.mUserView = checkNotNull(mUserView, "UserView cannot be null");

        mCompositeDisposable = new CompositeDisposable();
        mUsers = new ArrayList<>();
        mUsersIncludeHead = new ArrayList<>();
    }


    @Override
    public void subscribe() {
        SearchUser(null);

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Deprecated
    @Override
    public void refresh(String query) {
        //not use
        mCompositeDisposable.clear();
        SearchUser(null);
    }

    @Deprecated
    @Override
    public void clear() {
        //not use
    }

    @Deprecated
    @Override
    public void nextUserLoad(String query) {
        //not use
    }

    @Override
    public int getUserCount() {
        return mUsersIncludeHead.size();
    }

    @Override
    public void SearchUser(String query) {

        mCompositeDisposable.add(mUserRepository.getUsers().
                subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteUsers -> {
                    mUsers = favoriteUsers;
                    mUsersIncludeHead = getHeaderListLatter(mUsers);
                    mUserView.renderList();
                }, throwable -> {
                    //i.o exception
                    mUserView.showError(throwable.getMessage());
                }));
    }

    @Override
    public Object getUserItems() {
        return mUsersIncludeHead;
    }

    @Override
    public FavoriteUser getUserItem(int position) {
        return mUsersIncludeHead.get(position);
    }

    @Deprecated
    @Override
    public void addFavoriteUser(Object favoriteUser) {

    }

    @Override
    public void deleteFavoriteUser(Object favoriteUser) {
        mUserRepository.deleteUser(favoriteUser);
    }

    private List<FavoriteUser> getHeaderListLatter(List<FavoriteUser> usersList) {

        String lastHeader = "";

        List<FavoriteUser> userList_ = new ArrayList<>();

        int size = usersList.size();

        for (int i = 0; i < size; i++) {

            FavoriteUser user = usersList.get(i);
            String header = String.valueOf(user.getUserName().charAt(0)).toUpperCase();

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;
                FavoriteUser favoriteUser = new FavoriteUser(-1, header, null, null);
                favoriteUser.setHeader(true);
                userList_.add(favoriteUser);
            }

            userList_.add(user);
        }
        return userList_;
    }


}
