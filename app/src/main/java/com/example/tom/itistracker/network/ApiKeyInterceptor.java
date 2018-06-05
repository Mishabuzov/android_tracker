package com.example.tom.itistracker.network;

import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiKeyInterceptor implements Interceptor {

    private static final String AUTH_HEADER_NAME = "Authorization";

    private PreferenceUtils mPreferenceUtils;

    public ApiKeyInterceptor(PreferenceUtils preferenceUtils) {
        mPreferenceUtils = preferenceUtils;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        // Request customization: add request headers
        Request.Builder requestBuilder;
        if (mPreferenceUtils.isSignedIn()) {
            requestBuilder = original.newBuilder()
                    .addHeader(AUTH_HEADER_NAME, mPreferenceUtils.getToken()).url(originalHttpUrl);
        } else {
            requestBuilder = original.newBuilder().url(originalHttpUrl);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}
