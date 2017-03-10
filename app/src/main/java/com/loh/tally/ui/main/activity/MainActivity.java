package com.loh.tally.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.loh.tally.R;
import com.loh.tally.ui.authentication.event.AuthenticationLogoutEvent;
import com.loh.tally.ui.base.activity.BaseActivity;
import com.loh.tally.ui.main.adapter.MainPagerAdapter;
import com.loh.tally.ui.main.presenter.MainContract;
import com.loh.tally.ui.views.NonSwipeableViewPager;
import com.loh.tally.util.IntentUtil;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * File: MainActivity.java
 * Date: 10/03/2017
 * Created By: Liam O'Hanlon
 * Description: Activity for the main screen of application.
 * Handles {@link android.support.v4.app.Fragment} related to the {@link BottomNavigationView}.
 */
public class MainActivity extends BaseActivity implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottomNavView) BottomNavigationView bottomNavView;
    @BindView(R.id.viewpager) NonSwipeableViewPager viewPager;

    @Inject MainContract.Presenter presenter;
    @Inject MainPagerAdapter pagerAdapter;

    public static Intent getStartingIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(IntentUtil.BUNDLE_KEY, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attach(this);

        setupToolbar();
        setupBottomNavigation();
        setupViewPager();

        if (savedInstanceState == null) {
            presenter.navigateToModules();
        }
    }

    private void setupViewPager() {
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    protected void inject() {
        getViewComponent().inject(this);
    }

    @Subscribe
    @Override
    public void onSignOutEvent(AuthenticationLogoutEvent event) {
        handleSignOut();
    }

    private void setupBottomNavigation() {
        bottomNavView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_modules:
                presenter.navigateToModules();
                return true;

            case R.id.action_chats:
                presenter.navigateToChats();
                return true;

            case R.id.action_logout:
                presenter.logout();
                return true;

            default:
                break;

        }

        return false;
    }

    @Override
    public void navigateToModules() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void navigateToChats() {
        viewPager.setCurrentItem(1);
    }
}
