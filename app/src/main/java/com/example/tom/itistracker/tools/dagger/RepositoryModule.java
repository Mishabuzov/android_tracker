package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.repositories.AuthRepository;
import com.example.tom.itistracker.repositories.AuthRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public AuthRepository provideAuthRepository(ServiceApi serviceApi) {
        return new AuthRepositoryImpl(serviceApi);
    }

}
