package com.example.tom.itistracker.screens.auth.login;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.models.network.LoginError;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.network.RetrofitException;
import com.example.tom.itistracker.repositories.auth.AuthRepository;
import com.example.tom.itistracker.screens.auth.base_auth.BaseAuthPresenter;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.tom.itistracker.tools.Constants.DEFAULT_LOGIN_TYPE;
import static com.example.tom.itistracker.tools.Constants.MIN_LOGIN_LENGTH;
import static com.example.tom.itistracker.tools.Constants.MIN_PASSWORD_LENGTH;

@InjectViewState
public class LoginPresenter extends BaseAuthPresenter<LoginView> {

    @Inject AuthRepository mAuthRepository;

    @Inject PreferenceUtils mPreferenceUtils;

    void tryEnableAndDisableLoginButton(@NonNull final String login,
                                        @NonNull final String password) {
        if (isAllFieldsAreValid(login, password)) {
            getViewState().activateActionButton(true);
        } else {
            getViewState().activateActionButton(false);
        }
    }

    public LoginPresenter() {
        App.getComponent().inject(this);
    }

    private boolean isAllFieldsAreValid(@NonNull final String login,
                                        @NonNull final String password) {
        return login.length() >= MIN_LOGIN_LENGTH && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private void login(@NonNull final String username, @NonNull final String password) {
        String id = FirebaseInstanceId.getInstance().getToken();
        Timber.d(id);
        defaultRequestProcessing(mAuthRepository.login(new Credentials(username, password, DEFAULT_LOGIN_TYPE)),
                this::onLoginSuccess);
    }

    private void onLoginSuccess(@NonNull final User user) {
        mPreferenceUtils.saveUserProfile(user);
        getViewState().openProjectsScreen();
        getViewState().finish();
    }

    void checkFieldsAndTryLogin(@NonNull final String login, @NonNull final String password) {
        if (isAllFieldsAreValid(login, password)) {
            login(login, password);
        } else {
            defineIncorrectField(login, password);
        }
    }

    private void defineIncorrectField(@NonNull final String login, @NonNull final String password) {
        if (login.length() < MIN_LOGIN_LENGTH) {
            getViewState().showErrorInLoginField();
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            getViewState().showErrorInPasswordField();
        }
    }

    @Override
    protected String setTagForLogging() {
        return "LoginPresenter";
    }

    @Override
    protected void handleHttpException(@NonNull final RetrofitException exception) {
        getViewState().showSnackbar();
        try {
            showToast(exception.getErrorBodyAs(LoginError.class).getErrorMessage());
        } catch (IOException e) {
            logException(e, e.toString());
        }
    }

}
