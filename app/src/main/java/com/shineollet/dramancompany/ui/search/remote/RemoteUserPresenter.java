package com.shineollet.dramancompany.ui.search.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.shineollet.dramancompany.data.source.UserRepository;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;
import com.shineollet.dramancompany.data.source.remote.model.UserResponse;
import com.shineollet.dramancompany.ui.search.SearchContract;
import com.shineollet.dramancompany.util.ConvertGithubApiResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RemoteUserPresenter implements SearchContract.Presenter {

    private final UserRepository mUserRepository;

    private SearchContract.View mUserView;
    boolean isFirstSearch = true;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private List<UserItem> mUsers;
    private List<UserItem> mUsersIncludeHead;

    private int mNextPage = 0;

    @Inject
    public RemoteUserPresenter(UserRepository mUserRepository, RemoteUserFragment mUserView) {
        this.mUserRepository = mUserRepository;
        this.mUserView = mUserView;

        mCompositeDisposable = new CompositeDisposable();
        mUsers = new ArrayList<>();
        mUsersIncludeHead = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        mCompositeDisposable.add(mUserRepository.getUsers().
                subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteUsers -> {
                    Timber.d("디비 값이 변경됨");
                    CheckFavoriteUser();

                }, throwable -> {
                    //i.o exception
                    mUserView.showError(throwable.getMessage());
                }));
    }

    @Override
    public void unsubscribe() {
        mUserView.setRefresh(false);
        mCompositeDisposable.clear();
    }

    @Override
    public void refresh(String query) {
        mUserView.setRefresh(false);
        mUsers.clear();
        mUsersIncludeHead.clear();
        mNextPage = 0;
        SearchUser(query);

    }

    @Override
    public void clear() {
        mUsers = new ArrayList<>();
        mUsersIncludeHead = new ArrayList<>();
        mNextPage = 0;
    }

    @Override
    public void nextUserLoad(String query) {
        if (mUsers.size() > 0 && mNextPage > 0) {
            mUserView.setRefresh(true);
            mCompositeDisposable.add(mUserRepository.getUsers(query, mNextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userResponseResponse -> {
                        ConvertGithubApiResponse<UserResponse> githubApiResponse = new ConvertGithubApiResponse<>(userResponseResponse);
                        if (githubApiResponse.isSuccessful()) {
                            mNextPage = githubApiResponse.getNextPage() == null ? 0 : githubApiResponse.getNextPage();
                            mUsers.addAll(userResponseResponse.body().getItems());
                            CheckFavoriteUser();
//                            mUserView.renderList();
                            mUserView.setRefresh(false);
                        } else {
                            mUserView.showError(githubApiResponse.errorMessage);

                            mUserView.setRefresh(false);
                        }
                    }, throwable -> {
                        //i.o exception
                        mUserView.showError(throwable.getMessage());
                    }));
        }
    }

    @Override
    public int getUserCount() {
        return mUsersIncludeHead.size();
    }

    @Override
    public void SearchUser(String query) {
        int firstpage = 1;
        mCompositeDisposable.add(mUserRepository.getUsers(query, firstpage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponseResponse -> {
                    ConvertGithubApiResponse<UserResponse> githubApiResponse = new ConvertGithubApiResponse<>(userResponseResponse);
                    if (githubApiResponse.isSuccessful()) {
                        isFirstSearch = false;
                        mNextPage = githubApiResponse.getNextPage() == null ? 0 : githubApiResponse.getNextPage();
                        mUsers = userResponseResponse.body().getItems();
                        mUsersIncludeHead = getHeaderListLatter(mUsers);

                        CheckFavoriteUser();
//                    mUserView.renderList();
                        mUserView.setRefresh(false);
                    } else {
                        mUserView.showError(githubApiResponse.errorMessage);
                        mUserView.setRefresh(false);
                    }
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
    public UserItem getUserItem(int position) {
        return mUsersIncludeHead.get(position);
    }

    @Override
    public void addFavoriteUser(Object userItem) {
        mUserRepository.saveUser(userItem);
    }

    @Override
    public void deleteFavoriteUser(Object userItem) {
        mUserRepository.deleteUser(userItem);

    }

    void CheckFavoriteUser() {
        if (mUsers.size() > 0) {
            mCompositeDisposable.add(
                    Observable.fromIterable(mUsers)
                            .concatMap(userItem -> {
                                userItem.setFavorite(false);
                                return mUserRepository.getUserById(userItem.getId())
                                        .map(favoriteUser -> {
                                            userItem.setFavorite(true);
                                            return userItem;
                                        })
                                        .defaultIfEmpty(userItem)
                                        .toObservable();
                            })
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .toList()
                            .subscribe(userItems -> {
                                mUsers = userItems;
                                mUsersIncludeHead = getHeaderListLatter(mUsers);
                                mUserView.renderList();
                            }));
        } else {
            mUserView.renderList();
        }
    }

    private List<UserItem> getHeaderListLatter(List<UserItem> usersList) {

        String lastHeader = "";

        List<UserItem> userList_ = new ArrayList<>();

        int size = usersList.size();

        for (int i = 0; i < size; i++) {

            UserItem user = usersList.get(i);
            String header = String.valueOf(user.getLogin().charAt(0)).toUpperCase();

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;
                UserItem HeaduserItem = new UserItem();
                HeaduserItem.setLogin(header);
                HeaduserItem.setHeader(true);
                userList_.add(HeaduserItem);
            }

            userList_.add(user);
        }
        return userList_;
    }
}
