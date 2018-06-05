package com.example.tom.itistracker.screens.auth.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.base.activities.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new LoginFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }

    @Override
    protected void doCreatingActions() {
        setToolbarTitle(R.string.login_title);
    }
}
