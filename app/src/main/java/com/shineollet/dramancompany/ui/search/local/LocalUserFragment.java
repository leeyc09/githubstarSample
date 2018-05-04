package com.shineollet.dramancompany.ui.search.local;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shineollet.dnc.R;
import com.shineollet.dramancompany.ui.search.SearchContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 로컬 사용자 프레그먼트
 */
public class LocalUserFragment extends Fragment implements SearchContract.View {
    public final static String LIST_STATE_KEY = "recycler_list_state";

    SearchContract.Presenter mPresenter;

    LinearLayoutManager linearLayoutManager;

    LocalUserAdapter mAdapter;
    @BindView(R.id.usersRecyclerView)
    RecyclerView userRecyclerView;
    private Unbinder unbinder;
    private CompositeDisposable mCompositeDisposable;


    public LocalUserFragment() {
        // Create the presenter
    }

    public static LocalUserFragment newInstance() {
        return new LocalUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable listState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, listState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }


    @Override
    public void setRefresh(boolean isRefreshing) {

    }

    @Override
    public void renderList() {
        userRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError(@NonNull String msg) {
        Snackbar.make(userRecyclerView, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(@NonNull SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favoriteuser, container, false);
        setRetainInstance(true);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.setDebug(true);
        unbinder = ButterKnife.bind(this, root);
        initAdapter();

        if (savedInstanceState != null) {
            Parcelable listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            if (listState != null) {
                linearLayoutManager.onRestoreInstanceState(listState);
            }
        }
        userRecyclerView.setLayoutManager(linearLayoutManager);
        userRecyclerView.setAdapter(mAdapter);

        return root;
    }

    private void initAdapter() {
        mAdapter = new LocalUserAdapter(mPresenter);
        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        unbinder.unbind();
    }


}