package com.example.tom.itistracker;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.tom.itistracker.tools.dagger.AppComponent;
import com.example.tom.itistracker.tools.dagger.ApplicationModule;
import com.example.tom.itistracker.tools.dagger.ContextModule;
import com.example.tom.itistracker.tools.dagger.DaggerAppComponent;
import com.example.tom.itistracker.tools.dagger.NetworkModule;
import com.example.tom.itistracker.tools.dagger.RepositoryModule;
import com.example.tom.itistracker.tools.dagger.UtilsModule;
import com.facebook.stetho.Stetho;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import timber.log.Timber;

public class App extends Application {

    public static Handler sHandler;

    public static AppComponent sComponent;

    public static AppComponent getComponent() {
        return sComponent;
    }

    private void setComponent(AppComponent component) {
        sComponent = component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setComponent(buildDaggerAppComponent());
        sHandler = new Handler(Looper.getMainLooper());
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();

//        if (BuildConfig.DEBUG) {
        Stetho.initializeWithDefaults(this);
        Timber.plant(new Timber.DebugTree());
//        }
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
    }

    public AppComponent buildDaggerAppComponent() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .utilsModule(new UtilsModule())
                .build();
    }

}
