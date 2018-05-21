package com.shineollet.dramancompany.di;

import com.shineollet.dramancompany.di.scope.ActivityScoped;
import com.shineollet.dramancompany.ui.MainActivity;
import com.shineollet.dramancompany.ui.MainActivityModule;
import com.shineollet.dramancompany.ui.search.searchFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger.Android가 ActivityBindingModule 모듈이있는 부모 구성 요소가있는 하위 구성 요소를 만들고,
 * 우리의 경우 AppComponent가 될 것입니다. 이 설정에 대한 아름다운 부분은 AppComponent에 이러한 모든 하위 구성 요소가 있음을 알릴 필요가 없다는 것입니다
 * 이 하위 구성 요소에 AppComponent가 있음을 알릴 필요도 없습니다.
 * Dagger.Android에게 SubComponent가 지정된 모듈을 포함하고 scope 어노테이션 @ActivityScoped를 인식해야한다고 말하고 있습니다.
 * Dagger.Android 주석 프로세서를 실행하면 4 개의 하위 구성 요소가 생성됩니다.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainActivityModule.class, searchFragmentBindingModule.class})
    abstract MainActivity provideMainActivity();

}
