package com.example.tom.itistracker.screens.base.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.auth.login.LoginActivity;
import com.example.tom.itistracker.screens.base.activities.base_fragment.BaseFragmentActivity;
import com.example.tom.itistracker.tools.utils.AndroidUtils;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;
import com.example.tom.itistracker.tools.utils.UiUtils;
import com.example.tom.itistracker.widgets.progressbar.LoadingDialog;
import com.example.tom.itistracker.widgets.progressbar.LoadingView;

import javax.inject.Inject;

public abstract class BaseFragment extends MvpAppCompatFragment implements BaseFragmentView {

    @Inject protected UiUtils mUiUtils;

    @Inject protected AndroidUtils mAndroidUtils;

    @Inject protected PreferenceUtils mPrefUtils;

    private LoadingView mLoadingView;

    protected void startActivity(@NonNull final Class<? extends AppCompatActivity> targetActivity) {
        startActivity(new Intent(getActivity(), targetActivity));
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        mLoadingView = LoadingDialog.view(getActivity().getSupportFragmentManager());
        getArgs();
    }

    /**
     * This is the special method for processing bundle in some fragments;
     */
    protected void getArgs() {
    }

    public void setToolbarTitle(@NonNull final String title) {
        if (title != null && !title.isEmpty()) {
            ((BaseFragmentActivity) getActivity()).setToolbarTitle(title);
        }
    }

    public void setToolbarTitle(@StringRes final int title) {
        if (title != 0) {
            ((BaseFragmentActivity) getActivity()).setToolbarTitle(title);
        }
    }

    @NonNull
    protected final String getTextFromEdit(@NonNull final EditText edit) {
        return String.valueOf(edit.getText()).trim();
    }

    @Override
    public void showNetworkErrorMessage() {
        showToast(R.string.network_error_message);
    }

    @Override
    public void showToast(@NonNull final String message) {
        mUiUtils.showToast(message, getContext());
    }

    @Override
    public void showToast(@StringRes final int message) {
        mUiUtils.showToast(message, getContext());
    }

    @Override
    public void clearDataAndExit() {
        mPrefUtils.clearPreference();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    protected void hideKeyboardAndCleanEdits(EditText... edits) {
        for (EditText edit : edits) {
            edit.setText("");
        }
        hideKeyboard();
    }

    @Override
    public void hideKeyboard() {
        mAndroidUtils.hideSoftKeyboard(getActivity());
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

}
