package com.example.tom.itistracker.screens.base.activities.base_fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.tom.itistracker.screens.base.activities.base.BaseActivityView;

public interface FragmentActivityView extends BaseActivityView {

    void changeFragment(@NonNull final Fragment fragment);

}
