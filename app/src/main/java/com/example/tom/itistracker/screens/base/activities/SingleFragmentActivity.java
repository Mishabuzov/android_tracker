package com.example.tom.itistracker.screens.base.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.base.activities.base_fragment.BaseFragmentActivity;
import com.example.tom.itistracker.widgets.fonts.textviews.RobotoBoldTextView;

import butterknife.BindView;

public abstract class SingleFragmentActivity extends BaseFragmentActivity {

    @BindView(R.id.root_layout) LinearLayout mRootLayout;

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.toolbar_title) RobotoBoldTextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        initializeComponents();
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @NonNull
    public LinearLayout getRootLayout() {
        return mRootLayout;
    }

    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }
}
