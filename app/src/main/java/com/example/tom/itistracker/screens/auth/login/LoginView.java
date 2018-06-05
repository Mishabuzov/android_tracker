package com.example.tom.itistracker.screens.auth.login;

import com.example.tom.itistracker.screens.auth.base_auth.BaseAuthView;

public interface LoginView extends BaseAuthView {

    void showSnackbar();

    void showErrorInLoginField();

    void openProjectsScreen();

}
