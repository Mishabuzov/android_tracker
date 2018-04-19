package com.example.tom.itistracker.repositories;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.models.network.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthRepositoryImpl implements AuthRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public AuthRepositoryImpl(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public Single<User> login(@NonNull final Credentials credentials) {
        return mServiceApi.login(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
