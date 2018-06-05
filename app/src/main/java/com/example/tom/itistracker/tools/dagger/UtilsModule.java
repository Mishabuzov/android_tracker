package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.tools.utils.AndroidUtils;
import com.example.tom.itistracker.tools.utils.ConvertUtils;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;
import com.example.tom.itistracker.tools.utils.UiUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Singleton
    @Provides
    PreferenceUtils providePreferences() {
        return new PreferenceUtils();
    }

    @Singleton
    @Provides
    UiUtils provideUiUtils(AndroidUtils androidUtils) {
        return new UiUtils(androidUtils);
    }

    @Singleton
    @Provides
    AndroidUtils provideAndroidUtils() {
        return new AndroidUtils();
    }

    @Singleton
    @Provides
    ConvertUtils provideConvertUtils() {
        return new ConvertUtils();
    }

}
