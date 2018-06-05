package com.example.tom.itistracker.screens.base.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.tom.itistracker.App;
import com.example.tom.itistracker.screens.auth.login.LoginActivity;
import com.example.tom.itistracker.screens.navigation.NavigationActivity;
import com.example.tom.itistracker.screens.project_choosing.ProjectChoosingActivity;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import javax.inject.Inject;

public class StartActivity extends AppCompatActivity {

    @Inject PreferenceUtils mPreferenceUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        if (mPreferenceUtils.isSignedIn()) {
            openNavigationOrProjectScreen();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

    private void openNavigationOrProjectScreen() {
        if (mPreferenceUtils.isProjectChosen()) {
            startActivity(new Intent(this, NavigationActivity.class));
        } else {
            startActivity(new Intent(this, ProjectChoosingActivity.class));
        }
    }

}
