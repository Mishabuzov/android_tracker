package com.example.tom.itistracker.repositories.auth;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.models.network.User;

import io.reactivex.Single;

public interface AuthRepository {

    @NonNull
    Single<User> login(@NonNull final Credentials credentials);

}
