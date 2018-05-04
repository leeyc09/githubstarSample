package com.shineollet.dramancompany.ui.search.local;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shineollet.dnc.R;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;
import com.shineollet.dramancompany.ui.search.SearchContract;
import com.shineollet.dramancompany.ui.search.SectionHeaderViewHolder;
import com.shineollet.dramancompany.ui.search.UserViewHolder;

public class LocalUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SECTION_VIEW = 0;
    public static final int CONTENT_VIEW = 1;

    private SearchContract.Presenter mPresenter;

    public LocalUserAdapter(SearchContract.Presenter presenter) {
        super();
        this.mPresenter = presenter;
    }

    public long getItemId(int position) {
        if (mPresenter.getUserItem(position) instanceof UserItem)
            return (long) ((UserItem) this.mPresenter.getUserItem(position)).getId();
        else
            return (long) ((FavoriteUser) this.mPresenter.getUserItem(position)).getId();
    }

    public int getItemCount() {
        return this.mPresenter.getUserCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mPresenter.getUserItem(position) instanceof UserItem) {
            if (((UserItem) mPresenter.getUserItem(position)).isHeader()) {
                return SECTION_VIEW;
            } else {
                return CONTENT_VIEW;
            }
        } else {
            if (((FavoriteUser) mPresenter.getUserItem(position)).isHeader()) {
                return SECTION_VIEW;
            } else {
                return CONTENT_VIEW;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == SECTION_VIEW) {
            View view = layoutInflater.inflate(R.layout.recycler_section_header, parent, false);
            return new SectionHeaderViewHolder(view);
        }

        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (SECTION_VIEW == getItemViewType(position)) {
            ((SectionHeaderViewHolder) holder).bindTo(mPresenter.getUserItem(position));
        } else {
            holder.itemView.setOnClickListener(view -> {
                mPresenter.deleteFavoriteUser(mPresenter.getUserItem(position));
            });
            ((UserViewHolder) holder).bindTo(mPresenter.getUserItem(position));
        }

    }

}
