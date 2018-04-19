package com.example.tom.itistracker.screens.auth.base_auth;

import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;

public interface BaseAuthView extends BaseFragmentView {

    void showErrorInPasswordField();

    void showPassword();

    void hidePassword();

    void activateActionButton(boolean state);

}
