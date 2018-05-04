package com.shineollet.dramancompany.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shineollet.dramancompany.Injection;
import com.shineollet.dramancompany.ui.search.local.LocalUserFragment;
import com.shineollet.dramancompany.ui.search.local.LocalUserPresenter;
import com.shineollet.dramancompany.ui.search.remote.RemoteUserFragment;
import com.shineollet.dramancompany.ui.search.remote.RemoteUserPresenter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    Context context;
    private int tabCount;
    @NonNull
    private RemoteUserPresenter mRemoteUserPresenter;

    @NonNull
    private LocalUserPresenter mLocalUserPresenter;

    public TabPagerAdapter(Context context, FragmentManager fm, @NonNull int tabCount) {
        super(fm);
        this.context = context;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RemoteUserFragment remoteUserFragment = RemoteUserFragment.newInstance();
                mRemoteUserPresenter = new RemoteUserPresenter(Injection.provideUserRepository(context), remoteUserFragment);
                remoteUserFragment.setPresenter(mRemoteUserPresenter);
                return remoteUserFragment;
            case 1:
                LocalUserFragment localUserFragment = LocalUserFragment.newInstance();
                mLocalUserPresenter = new LocalUserPresenter(Injection.provideUserRepository(context), localUserFragment);
                localUserFragment.setPresenter(mLocalUserPresenter);
                return localUserFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
