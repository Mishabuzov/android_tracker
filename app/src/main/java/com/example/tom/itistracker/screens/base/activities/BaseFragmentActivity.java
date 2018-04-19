package com.example.tom.itistracker.screens.base.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.widgets.fonts.textviews.RobotoBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends BaseActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    @BindView(R.id.root_layout) LinearLayout mRootLayout;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.toolbar_title) RobotoBoldTextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        ButterKnife.bind(this);
        initComponents();
        doCreatingActions();
    }

    protected void doCreatingActions() {
    }

    protected void initComponents() {
        processFragment();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void processFragment() {
        Fragment fragment = getFragment();
        fragment.setArguments(getFragmentArguments());
        setNewInstanceOfFragment(fragment);
    }

    protected abstract Fragment getFragment();

    protected abstract Bundle getFragmentArguments();

    @NonNull
    public LinearLayout getRootLayout() {
        return mRootLayout;
    }

    protected void setNewInstanceOfFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment_layout) != null) {
          /*  ft.replace(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();*/
        } else {
            ft.add(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        }
    }

    public void setToolbarTitle(@NonNull final String title) {
        if (!title.isEmpty()) {
            mToolbarTitle.setText(title);
        }
    }

    public void setToolbarTitle(@StringRes final int title) {
        if (title != 0) {
            mToolbarTitle.setText(title);
        }
    }

}
