package com.loh.tally.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.loh.tally.R;
import com.loh.tally.TallyApp;
import com.loh.tally.ui.base.dagger.component.ViewComponent;
import com.loh.tally.ui.base.dagger.module.ViewModule;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File: BaseActivity.java
 * Date: 10/03/2017
 * Created By: Liam O'Hanlon
 * Description: Base activity that all {@link android.app.Activity} should inherit.
 * Offers common functionality that all {@link android.app.Activity} can use.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject Bus eventbus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventbus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventbus.unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected abstract void inject();

    protected ViewComponent getViewComponent() {
        return getTallyApp()
                .getApplicationComponent()
                .plusViewComponent(new ViewModule(this));
    }

    protected void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    protected void setupBackToolbar() {
        setupToolbar();

        if (getSupportActionBar() != null) {
            // back navigation should be handled by manifest.xml linking
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected TallyApp getTallyApp() {
        return (TallyApp) getApplication();
    }
}
