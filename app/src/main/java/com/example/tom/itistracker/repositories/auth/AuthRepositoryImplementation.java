package com.example.tom.itistracker.repositories.auth;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.network.ServiceApi;

import io.reactivex.Single;

public class AuthRepositoryImplementation implements AuthRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public AuthRepositoryImplementation(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public Single<User> login(@NonNull final Credentials credentials) {
        return mServiceApi.login(credentials);
    }
}
