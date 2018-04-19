package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.screens.auth.login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class,
        ApplicationModule.class,
        RepositoryModule.class,
        NetworkModule.class
})
public interface AppComponent {
    void inject(LoginPresenter loginPresenter);
}
