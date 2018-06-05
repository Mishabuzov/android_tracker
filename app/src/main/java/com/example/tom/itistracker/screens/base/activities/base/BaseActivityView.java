package com.example.tom.itistracker.screens.base.activities.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public interface BaseActivityView {

    void startActivity(@NonNull final Class<? extends AppCompatActivity> targetActivity);

}
