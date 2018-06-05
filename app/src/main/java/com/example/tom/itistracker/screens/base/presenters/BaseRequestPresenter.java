package com.example.tom.itistracker.screens.base.presenters;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.network.RetrofitException;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;
import com.example.tom.itistracker.tools.functions.Function;
import com.example.tom.itistracker.tools.functions.ResultFunction;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseRequestPresenter<View extends BaseFragmentView> extends BasePresenter<View> {

    private CompositeDisposable mDisposables;

    public BaseRequestPresenter() {
        mDisposables = new CompositeDisposable();
    }

    protected final <T> void defaultRequestProcessing(@NonNull final Single<T> requestSingle,
                                                      @NonNull final ResultFunction<T> onSuccessFunction) {
        showLoading();
        Disposable requestDisposable = requestSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> processResultWithSecDelay(() -> onSuccessFunction.action(result)), this::handleError);
        mDisposables.add(requestDisposable);
    }

    private void processResultWithSecDelay(@NonNull final Function function) {
       /* new Handler().postDelayed(() -> {
            hideLoading();
            function.action();
        }, 10000);*/
        hideLoading();
        function.action();
    }

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

    private void defineNetworkErrorProcessing(@NonNull final RetrofitException exception,
                                              final boolean isReloadScreenShows,
                                              @NonNull final Function reloadFunction) {
        if (isReloadScreenShows) {
            handleNetworkError(reloadFunction);
        } else {
            handleNetworkError(exception);
        }
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

    private void cleanDataAndExit() {
        getViewState().clearDataAndExit();
    }

    public void onDestroy() {
        mDisposables.dispose();
    }

}