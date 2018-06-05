package com.example.tom.itistracker.screens.auth.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.auth.base_auth.AuthHelper;
import com.example.tom.itistracker.screens.base.activities.SingleFragmentActivity;
import com.example.tom.itistracker.screens.base.fragments.BaseFragment;
import com.example.tom.itistracker.screens.project_choosing.ProjectChoosingActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LoginFragment extends BaseFragment implements LoginView, AuthHelper.TextChangedListener {

    @BindString(R.string.login_unsuccess_default_error) String mUnSuccessLoginError;

    @BindString(R.string.login_error_edit_text) String mErrorLogin;

    @BindString(R.string.password_error_edit_text) String mErrorPassword;

    @BindString(R.string.content_login_btn_text_hide) String mHideButtonText;

    @BindString(R.string.content_login_btn_text_show) String mShowButtonText;

    @BindView(R.id.edittext_login) EditText mLoginEditText;

    @BindView(R.id.login_text_input_layout) TextInputLayout mLoginTextInputLayout;

    @BindView(R.id.edittext_password) EditText mPasswordEditText;

    @BindView(R.id.password_text_input_layout) TextInputLayout mPasswordTextInputLayout;

    @BindView(R.id.btn_login) Button mBtnLogin;

    @BindView(R.id.show_password_button) Button mShowPasswordButton;

    @InjectPresenter LoginPresenter mPresenter;

    private Snackbar mSnackbar;

    private AuthHelper mAuthHelper;

    @OnClick(R.id.btn_login)
    public void onClickLoginBtn() {
        mPresenter.checkFieldsAndTryLogin(
                getTextFromEdit(mLoginEditText),
                getTextFromEdit(mPasswordEditText));
        hideKeyboardAndCleanEdits(mPasswordEditText);
    }

    @OnLongClick(R.id.btn_login)
    public boolean onLongClickSignInButton() {
//        if (BuildConfig.DEBUG) {
        final String[] logins = {"mikhaijl7@gmail.com", "mikelio11@yandex.ru"};
        final String[] passwords = {"getiba19", "getiba19"};
        new MaterialDialog.Builder(getContext()).items((CharSequence[]) logins)
                .itemsCallback((dialog, view1, which, text) -> {
                    mLoginEditText.setText(logins[which]);
                    mPasswordEditText.setText(passwords[which]);
                })
                .show();
        return true;
        //        }
    }

    @OnClick(R.id.show_password_button)
    public void onClickShowPasswordButton() {
        mPresenter.defineInputType(getTextFromEdit(mPasswordEditText).length());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initFragmentElements();
        return view;
    }

    @Override
    public void activateActionButton(boolean state) {
        mBtnLogin.setEnabled(state);
    }

    private void initFragmentElements() {
        mBtnLogin.setEnabled(false);
        mAuthHelper = new AuthHelper(this);
        mAuthHelper.setTextWatcher(mLoginEditText, mLoginTextInputLayout);
        mAuthHelper.setTextWatcher(mPasswordEditText, mPasswordTextInputLayout);
        configSnackbar();
    }

    private void configSnackbar() {
        mSnackbar = Snackbar
                .make(((SingleFragmentActivity) getActivity()).getRootLayout(),
                        R.string.login_forget_password_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.login_forget_password_button,
//                        (v) -> startActivity(ForgetPasswordActivity.class))
                        (v) -> showToast("Вспомни!"))
                .setActionTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void showSnackbar() {
        mSnackbar.show();
    }

    @Override
    public void onTextChanged() {
        mPresenter.tryEnableAndDisableLoginButton(getTextFromEdit(mLoginEditText),
                getTextFromEdit(mPasswordEditText));
    }

    @Override
    public void openProjectsScreen() {
        startActivity(ProjectChoosingActivity.class);
    }

    @Override
    public void showErrorInLoginField() {
        mAuthHelper.showErrorInInputLayout(mLoginTextInputLayout, mErrorLogin);
    }

    @Override
    public void showErrorInPasswordField() {
        mAuthHelper.showErrorInInputLayout(mPasswordTextInputLayout, mErrorPassword);
    }

    @Override
    public void showPassword() {
        mAuthHelper.showPassword(mPasswordEditText, mShowPasswordButton);
    }

    @Override
    public void hidePassword() {
        mAuthHelper.hidePassword(mPasswordEditText, mShowPasswordButton);
    }
}
