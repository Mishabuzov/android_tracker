package com.example.tom.itistracker.screens.base.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface BaseFragmentView extends MvpView {

    void setToolbarTitle(String title);

    void finish();

    void exitToLoginScreen();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToast(@NonNull final String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToast(@StringRes final int message);

    void showNetworkErrorMessage();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideLoading();

}
