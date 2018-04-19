package com.example.tom.itistracker.screens.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.screens.auth.login.LoginActivity;
import com.example.tom.itistracker.screens.base.activities.BaseFragmentActivity;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;
import com.example.tom.itistracker.widgets.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends BaseFragmentActivity implements MenuAdapter.ClickCallback {

    @BindView(R.id.fragment_layout) FrameLayout mFragmentLayout;

    @BindView(R.id.menu_recycler) EmptyRecyclerView mMenuRecycler;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    private MenuAdapter mMenuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        initNavigationElements();
    }

    @Override
    protected Fragment getFragment() {
        return null;
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }

    private void initNavigationElements() {
        setupMenuAdapter();
        initComponents();
    }

    @Override
    public void onMenuItemChosen(MenuItem item) {
        switch (item) {
            case LOG_OUT:
                PreferenceUtils.clearPreference();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }
    }

    private void setupMenuAdapter() {
        mMenuAdapter = new MenuAdapter(MenuItem.getmMenuItems(), this);
        mMenuAdapter.attachToRecyclerView(mMenuRecycler);
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(mMenuRecycler.getContext()));
    }

    public void openNavigationMenu() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeNavigationMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
