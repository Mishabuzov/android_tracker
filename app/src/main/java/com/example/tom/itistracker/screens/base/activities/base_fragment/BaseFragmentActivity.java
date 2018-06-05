package com.example.tom.itistracker.screens.base.activities.base_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.base.activities.base.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends BaseActivity implements FragmentActivityView {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    protected final void initializeComponents() {
        ButterKnife.bind(this);
        initializeActionBar();
        processFragment();
        doCreatingActions();
    }

    protected void doCreatingActions() {
    }

    protected void initializeActionBar() {
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected abstract Toolbar getToolbar();

    protected abstract TextView getToolbarTitle();

    protected final void processFragment() {
        Fragment fragment = getFragment();
        fragment.setArguments(getFragmentArguments());
        setNewInstanceOfFragment(fragment);
    }

    protected abstract Fragment getFragment();

    protected abstract Bundle getFragmentArguments();

    private void setNewInstanceOfFragment(@NonNull final Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment_layout) != null) {
            //Don't change fragment here! Otherwise Moxy won't set saved fragment instance
          /*  ft.replace(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();*/
        } else {
            ft.add(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        }
    }

    public void changeFragment(@NonNull final Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_layout, fragment, FRAGMENT_TAG);
        ft.commit();
    }

    public void setToolbarTitle(@NonNull final String title) {
        if (!title.isEmpty()) {
            getToolbarTitle().setText(title);
        }
    }

    public void setToolbarTitle(@StringRes final int title) {
        if (title != 0) {
            getToolbarTitle().setText(title);
        }
    }

}
