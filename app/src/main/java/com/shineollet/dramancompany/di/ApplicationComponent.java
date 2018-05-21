package com.shineollet.dramancompany.di;

import android.app.Application;

import com.shineollet.dramancompany.GithubApplication;
import com.shineollet.dramancompany.data.source.UserRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 실질적으로 코드가 제네레이트 되는 부분
 * Dagger{interface_name} 형태로 제네레이트 된다.
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        UserRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class
})
public interface ApplicationComponent extends AndroidInjector<GithubApplication> {

    UserRepository getUserRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();

    }
}
