package com.example.tom.itistracker.screens.base.activities;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.tom.itistracker.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Window mWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindow(R.color.up_toolbar_color);
    }

    protected void setupWindow(@ColorRes int statusBarColor) {
        setupWindow();
        setStatusBarColor(statusBarColor);
    }

    protected void setupWindow() {
        mWindow = getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    protected void setStatusBarColor(@ColorRes int statusBarColor) {
        mWindow.setStatusBarColor(ContextCompat.getColor(BaseActivity.this, statusBarColor));
    }

}
