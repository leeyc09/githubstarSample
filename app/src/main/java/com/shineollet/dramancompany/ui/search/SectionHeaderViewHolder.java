package com.shineollet.dramancompany.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shineollet.dnc.R;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.headerTitleTextview)
    TextView headerTitleTextview;


    public SectionHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static SectionHeaderViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_section_header, parent, false);
        return new SectionHeaderViewHolder(view);
    }

    public void bindTo(Object user) {
        if (user instanceof FavoriteUser) {
            headerTitleTextview.setText(MessageFormat.format("{0}", ((FavoriteUser) user).getUserName()));
        }

        if (user instanceof UserItem) {
            headerTitleTextview.setText(MessageFormat.format("{0}", ((UserItem) user).getLogin()));
        }

    }
}
