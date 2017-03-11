package com.loh.tally.ui.presentations.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.loh.tally.R;
import com.loh.tally.ui.authentication.event.AuthenticationLogoutEvent;
import com.loh.tally.ui.base.activity.BaseActivity;
import com.loh.tally.ui.presentations.main.adapter.PresentationPagerAdapter;
import com.loh.tally.ui.presentations.main.presenter.PresentationContract;
import com.loh.tally.ui.views.NonSwipeableViewPager;
import com.loh.tally.util.IntentUtil;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

public class PresentationActivity extends BaseActivity implements PresentationContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager) NonSwipeableViewPager viewPager;
    @BindView(R.id.bottomNavView) BottomNavigationView bottomNavView;

    @Inject PresentationContract.Presenter presenter;
    @Inject PresentationPagerAdapter listAdapter;

    public static Intent getStartingIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, PresentationActivity.class);
        intent.putExtra(IntentUtil.BUNDLE_KEY, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        presenter.attach(this);
        setupBackToolbar();
        setupViewpager();
        setupBottomNavigation();
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

    @Subscribe @Override
    public void onSignOutEvent(AuthenticationLogoutEvent event) {
        handleSignOut();
    }

    private void setupViewpager() {
        viewPager.setAdapter(listAdapter);
    }

    private void setupBottomNavigation() {
        bottomNavView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_presentations:
                presenter.navigateToPresentations();
                return true;

            case R.id.action_chat:
                presenter.navigateToChat();
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
    public void navigateToPresentations() {
        viewPager.setCurrentItem(PresentationPagerAdapter.FRAGMENT_PRESENTATIONS);
    }

    @Override
    public void navigateToChat() {
        viewPager.setCurrentItem(PresentationPagerAdapter.FRAGMENT_CHAT);
    }
}