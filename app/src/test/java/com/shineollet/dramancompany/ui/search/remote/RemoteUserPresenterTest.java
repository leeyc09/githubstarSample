package com.shineollet.dramancompany.ui.search.remote;

import com.shineollet.dramancompany.data.source.UserRepository;
import com.shineollet.dramancompany.ui.search.SearchContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RemoteUserPresenterTest {

    @Mock
    private UserRepository mUserRepository;

    @Mock
    private SearchContract.View mView;

    private RemoteUserPresenter presenter;


    @Before
    public void setupPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        presenter = new RemoteUserPresenter(mUserRepository, mView);
    }


    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        presenter = new RemoteUserPresenter(mUserRepository, mView);

        // Then the presenter is set to the view
        verify(mView).setPresenter(presenter);
    }

    @Test
    public void errorLoadingTasks_ShowsError() {
        // Given that no tasks are available in the repository
        when(mUserRepository.getUsers()).thenReturn(Flowable.error(new Exception()));

        // When tasks are loaded
//        presenter.loadTasks(true);

        // Then an error message is shown
        verify(mView).showError("error");
    }
}