package com.shineollet.dramancompany.ui.search.remote;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.shineollet.dnc.R;
import com.shineollet.dramancompany.ui.search.SearchContract;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 리모트 사용자 프레그먼트
 */
public class RemoteUserFragment extends Fragment implements SearchContract.View {
    public final static String LIST_STATE_KEY = "recycler_list_state";

    SearchContract.Presenter mPresenter;

    LinearLayoutManager linearLayoutManager;

    remoteUserAdapter mAdapter;
    @BindView(R.id.usersSwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout2;
    @BindView(R.id.usersRecyclerView)
    RecyclerView userRecyclerView;
    @BindView(R.id.fragment_search_edittext)
    EditText searchEditText;
    @BindView(R.id.fragment_search_button)
    Button searchBtn;
    private Unbinder unbinder;
    private CompositeDisposable mCompositeDisposable;

    public RemoteUserFragment() {
        // Create the presenter
    }

    public static RemoteUserFragment newInstance() {
        return new RemoteUserFragment();
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
        swipeRefreshLayout2.setRefreshing(isRefreshing);
        userRecyclerView.setEnabled(!isRefreshing);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_searchuser, container, false);
        setRetainInstance(true);

        mCompositeDisposable = new CompositeDisposable();
        unbinder = ButterKnife.bind(this, root);
        initAdapter();

        if (savedInstanceState != null) {
            Parcelable listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            if (listState != null) {
                linearLayoutManager.onRestoreInstanceState(listState);
            }
        }
        userRecyclerView.setLayoutManager(linearLayoutManager);
        userRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    if (!swipeRefreshLayout2.isRefreshing()) {
                        int visibleItemCount = userRecyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = userRecyclerView.getLayoutManager().getItemCount();
                        int pastVisiblesItems = ((LinearLayoutManager)
                                userRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            mPresenter.nextUserLoad(searchEditText.getText().toString());
                        }
                    }
                }
            }
        });

        userRecyclerView.setAdapter(mAdapter);
        initSearch();

        swipeRefreshLayout2.setOnRefreshListener(() -> {
            if (searchEditText.getText().length() > 0) {
                mPresenter.refresh(searchEditText.getText().toString());
            } else {
                setRefresh(false);
                showError(getString(R.string.input_text));
            }
        });

        return root;
    }

    private void initAdapter() {
        mAdapter = new remoteUserAdapter(mPresenter);

        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

    }

    private void initSearch() {
        mCompositeDisposable.add(RxTextView.textChangeEvents(searchEditText)
                .skipInitialValue()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .map(text -> text.text().toString())
                .filter(s -> s.length() > 0)
                .subscribe(query -> mPresenter.SearchUser(query)));


        mCompositeDisposable.add(RxView.clicks(searchBtn)
                .debounce(250, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (!searchEditText.getText().toString().isEmpty()) {
                        mPresenter.SearchUser(searchEditText.getText().toString());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        unbinder.unbind();
    }


}
