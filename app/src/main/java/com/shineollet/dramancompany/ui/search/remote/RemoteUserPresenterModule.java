package com.shineollet.dramancompany.ui.search.remote;

import com.shineollet.dramancompany.di.scope.FragmentScoped;
import com.shineollet.dramancompany.ui.search.SearchContract;

import dagger.Binds;
import dagger.Module;

/**
 * 모듈은 의존성 관계를 설정하고 객체를 주입하기 위한 메서드를 정의한다.
 * 이 모듈을 사용해서 view에  프레젠터의 의존성 관계를 설정한다.
 */
@Module
public abstract class RemoteUserPresenterModule {

    /**
     * 이 메소드의 반환 유형에 대한 AndroidInjector를 생성합니다.
     * 인젝터는 dagger.Subcomponent로 구현되며 대거의 하위 요소가됩니다. 모듈 구성 요소.
     * 이 주석은 구체적인 Android 프레임 워크 유형 (예 : FooActivity, BarFragment, MyService 등)을 반환하는
     * dagger.Module의 추상 메소드에 적용되어야합니다. 메서드에는 매개 변수가 없어야합니다.
     * 자세한 내용은 문서를 참조하십시오.
     */
//    @ContributesAndroidInjector
//    abstract RemoteUserFragment remoteUserFragment();
    @FragmentScoped
    @Binds
    abstract SearchContract.View RemoteView(RemoteUserFragment remoteUserFragment);

    @FragmentScoped
    @Binds
    abstract SearchContract.Presenter RemotePresenter(RemoteUserPresenter remoteUserPresenter);

}
