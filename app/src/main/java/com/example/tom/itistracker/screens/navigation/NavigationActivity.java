package com.example.tom.itistracker.screens.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tom.itistracker.App;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.screens.auth.login.LoginActivity;
import com.example.tom.itistracker.screens.base.activities.base_fragment.BaseFragmentActivity;
import com.example.tom.itistracker.screens.sprints_and_stories.SprintsStoriesSwitchFragment;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;
import com.example.tom.itistracker.tools.utils.UiUtils;
import com.example.tom.itistracker.widgets.EmptyRecyclerView;
import com.example.tom.itistracker.widgets.fonts.textviews.RobotoBoldTextView;

import javax.inject.Inject;

import butterknife.BindView;

public class NavigationActivity extends BaseFragmentActivity implements MenuAdapter.ClickCallback,
        NavigationView {

    @BindView(R.id.fragment_layout) FrameLayout mFragmentLayout;

    @BindView(R.id.menu_recycler) EmptyRecyclerView mMenuRecycler;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.toolbar_title) RobotoBoldTextView mToolbarTitle;

    @Inject PreferenceUtils mPreferenceUtils;

    @Inject UiUtils mUiUtils;

    private MenuAdapter mMenuAdapter;

    private NavigationPresenter mPresenter;

    public static void openMainPage(@NonNull final Context context) {
        startActivityAsFirstInStack(context, NavigationActivity.class);
    }

    /**
     * The method open NavigationActivity as first Activity in Activities stack.
     * It's cleared all previous opened activities;
     *
     * @param context - context for opening new Activity;
     */
    private static void startActivityAsFirstInStack(@NonNull final Context context,
                                                    @NonNull final Class<? extends AppCompatActivity> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        App.getComponent().inject(this);
        initializeComponents();
        setupMenuAdapter();
    }

    @Override
    protected void doCreatingActions() {
        mPresenter = new NavigationPresenter(this);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    @Override
    protected Fragment getFragment() {
        return new SprintsStoriesSwitchFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }

    @Override
    public void onMenuItemChosen(MenuItem item) {
        mPresenter.checkAndSwitchScreen(item);
    }

    @Override
    public void showLogoutDialog() {
        mUiUtils.createConfirmDialog(this, this::onLogoutConfirmed, getString(R.string.exit_message));
    }

    private void onLogoutConfirmed() {
        mPreferenceUtils.clearPreference();
        startActivity(LoginActivity.class);
        finish();
    }

    private void setupMenuAdapter() {
        mMenuAdapter = new MenuAdapter(MenuItem.getMenuItems(), this, mPreferenceUtils.getUserProfile());
        mMenuAdapter.attachToRecyclerView(mMenuRecycler);
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(mMenuRecycler.getContext()));
    }

    public void openNavigationMenu() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeNavigationMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        mUiUtils.createConfirmDialog(this, super::onBackPressed, getString(R.string.exit_message));
    }
}
