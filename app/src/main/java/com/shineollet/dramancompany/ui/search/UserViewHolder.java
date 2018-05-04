package com.shineollet.dramancompany.ui.search;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shineollet.dnc.R;
import com.shineollet.dramancompany.data.source.local.entity.FavoriteUser;
import com.shineollet.dramancompany.data.source.remote.model.UserItem;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.UserName)
    TextView userName;

    @BindView(R.id.UserAvatar)
    SimpleDraweeView userAvatar;

    @BindView(R.id.favoriteImageView)
    ImageView favoriteImageView;

    public UserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static UserViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    public void bindTo(Object user) {
        if (user instanceof FavoriteUser) {
            if (((FavoriteUser) user).isHeader()) {

            } else {
                userName.setText(MessageFormat.format("{0}", ((FavoriteUser) user).getUserName()));
                Uri uri = Uri.parse(((FavoriteUser) user).getAvatar_url());
                userAvatar.setImageURI(uri);
                favoriteImageView.setVisibility(View.VISIBLE);
            }
        }

        if (user instanceof UserItem) {
            userName.setText(MessageFormat.format("{0}", ((UserItem) user).getLogin()));
            Uri uri = Uri.parse(((UserItem) user).getAvatarUrl());
            userAvatar.setImageURI(uri);

            if (((UserItem) user).isFavorite()) {
                favoriteImageView.setVisibility(View.VISIBLE);
            } else {
                favoriteImageView.setVisibility(View.INVISIBLE);
            }
        }

    }
}
