package com.shineollet.dramancompany.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shineollet.dramancompany.di.scope.ActivityScoped;
import com.shineollet.dramancompany.ui.search.local.LocalUserFragment;
import com.shineollet.dramancompany.ui.search.remote.RemoteUserFragment;

import javax.inject.Inject;

@ActivityScoped
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    Context context;
    private int tabCount = 2;
//
//    @Inject
//    RemoteUserFragment remoteUserFragment;
//    private RemoteUserPresenter mRemoteUserPresenter;
//
//    @Inject
//    LocalUserFragment localUserFragment;
//    private LocalUserPresenter mLocalUserPresenter;

    @Inject
    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                return new RemoteUserFragment();
                return new RemoteUserFragment();
            case 1:
//                return new LocalUserFragment();
                return new LocalUserFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
