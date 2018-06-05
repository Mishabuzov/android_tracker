package com.example.tom.itistracker.screens.base.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpPresenter;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;

import timber.log.Timber;

public abstract class BasePresenter<View extends BaseFragmentView> extends MvpPresenter<View> {

    protected final void logException(@NonNull final Throwable exception,
                                      @NonNull final String message) {
        Timber.w(setTagForLogging(), message);
        exception.printStackTrace();
    }

    protected abstract String setTagForLogging();

    protected final void hideLoading() {
        getViewState().hideLoading();
    }

    protected final void showLoading() {
        getViewState().hideKeyboard();
        getViewState().showLoading();
    }

    protected final void showToast(@StringRes final int message) {
        getViewState().showToast(message);
    }

    protected final void showToast(@NonNull final String message) {
        getViewState().showToast(message);
    }

}
