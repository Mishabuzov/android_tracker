package com.example.tom.itistracker.screens.base;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpPresenter;
import com.example.tom.itistracker.network.RetrofitException;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;
import com.example.tom.itistracker.tools.Function;

public abstract class BaseRequestPresenter<View extends BaseFragmentView> extends MvpPresenter<View> {

    /**
     * Handling errors with reload screen on Network Error.
     *
     * @param e              - exception, which broken request
     * @param reloadFunction - reload action
     */
    protected final void handleError(@NonNull final Throwable e,
                                     @NonNull final Function reloadFunction) {
        hideLoading();
        processingError((RetrofitException) e, true, reloadFunction);
    }

    /**
     * Simple error handling with just toast on Network Error.
     *
     * @param e - exception, which broken request.
     */
    protected final void handleError(@NonNull final Throwable e) {
        hideLoading();
        processingError((RetrofitException) e, false,
                () -> handleUnidentifiedError((RetrofitException) e));
    }

    private void processingError(@NonNull final RetrofitException exception,
                                 final boolean isReloadScreenShows,
                                 @NonNull final Function reloadFunction) {
        switch (exception.getType()) {
            case HTTP:
                handleHttpException(exception);
                break;
            case SERVER:
            case UNEXPECTED:
                handleUnidentifiedError(exception);
                break;
            case NETWORK:
                defineNetworkErrorProcessing(exception, isReloadScreenShows, reloadFunction);
                break;
        }
        logException(exception, exception.getOriginalExceptionMessage());
    }

    protected final void logException(@NonNull final Throwable exception,
                                      @NonNull final String message) {
//        Logger.w(setTagForLogging(), message);
        exception.printStackTrace();
    }

    protected abstract String setTagForLogging();

    private void defineNetworkErrorProcessing(@NonNull final RetrofitException exception,
                                              final boolean isReloadScreenShows,
                                              @NonNull final Function reloadFunction) {
        if (isReloadScreenShows) {
            handleNetworkError(reloadFunction);
        } else {
            handleNetworkError(exception);
        }
    }

    protected void doActionsWithSecDelay(Function function) {
        new Handler().postDelayed(() -> {
            function.action();
            hideLoading();
        }, 10000);
    }

    protected final void hideLoading() {
        getViewState().hideLoading();
    }

    protected final void showLoading() {
        getViewState().showLoading();
    }

    private void handleNetworkError(@NonNull final RetrofitException exception) {
        showToast(exception.getToastMessage());
    }

    private void handleNetworkError(@NonNull final Function reloadFunction) {
//        mView.showNetworkErrorScreen(reloadFunction);
        getViewState().showNetworkErrorMessage();
    }

    private void handleUnidentifiedError(@NonNull final RetrofitException exception) {
        showToast(exception.getToastMessage());
    }

    protected void handleHttpException(@NonNull final RetrofitException exception) {
        cleanDataAndExit();
        showToast(exception.getToastMessage());
    }

    protected final void showToast(@StringRes final int message) {
        getViewState().showToast(message);
    }

    protected final void showToast(@NonNull final String message) {
        getViewState().showToast(message);
    }

    protected void cleanDataAndExit() {
//        AndroidUtils.cleanAllSavedData();
        getViewState().exitToLoginScreen();
    }

}