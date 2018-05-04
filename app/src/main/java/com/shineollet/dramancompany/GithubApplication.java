package com.shineollet.dramancompany;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.shineollet.dnc.BuildConfig;

import timber.log.Timber;

public class GithubApplication extends Application {

    private static GithubApplication INSTANCE = null;

    public static GithubApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Fresco.initialize(this);
    }
}
