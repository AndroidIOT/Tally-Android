package com.loh.tally.ui.main.presenter;

import com.loh.tally.domain.authentication.AuthenticationManager;
import com.loh.tally.ui.base.dagger.scope.ViewScope;
import com.loh.tally.ui.base.presenter.BasePresenter;

/**
 * File: MainPresenter.java
 * Date: 10/03/2017
 * Created By: Liam O'Hanlon
 * Description: Communicates with business logic for the functionality of {@link com.loh.tally.ui.main.activity.MainActivity}.
 */
@ViewScope
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final AuthenticationManager authenticationManager;

    public MainPresenter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void navigateToModules() {
        getView().navigateToModules();
    }

    @Override
    public void navigateToChats() {
        getView().navigateToChats();
    }

    @Override
    public void logout() {
        authenticationManager.logout();
    }
}
