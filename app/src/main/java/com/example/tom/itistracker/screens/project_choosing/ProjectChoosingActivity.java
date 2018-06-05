package com.example.tom.itistracker.screens.project_choosing;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.tom.itistracker.screens.base.activities.SingleFragmentActivity;

public class ProjectChoosingActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new ProjectChoosingFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }

}
