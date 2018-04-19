package com.example.tom.itistracker.screens.auth.base_auth;

import com.example.tom.itistracker.screens.base.BaseRequestPresenter;

public abstract class BaseAuthPresenter<View extends BaseAuthView>
        extends BaseRequestPresenter<View> {

    private boolean mIsPasswordHidden;

    public void defineInputType(int passwordLength) {
        if (mIsPasswordHidden && passwordLength != 0) {
            getViewState().showPassword();
            mIsPasswordHidden = false;
        } else {
            getViewState().hidePassword();
            mIsPasswordHidden = true;
        }
    }

}
