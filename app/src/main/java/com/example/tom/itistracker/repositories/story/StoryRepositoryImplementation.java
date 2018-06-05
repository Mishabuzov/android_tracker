package com.example.tom.itistracker.repositories.story;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.AttachStoryToSprintObject;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.network.ServiceApi;

import java.util.List;

import io.reactivex.Single;

public class StoryRepositoryImplementation implements StoryRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public StoryRepositoryImplementation(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public Single<List<UserStory>> getUserStories(final long projectId) {
        return mServiceApi.getUserStories(projectId);
    }

    @NonNull
    @Override
    public Single<UserStory> attachUserStoryToSprint(final long userStoryId,
                                                     @NonNull final AttachStoryToSprintObject attachObject) {
        return mServiceApi.attachUserStoryToSprint(userStoryId, attachObject);
    }

    @NonNull
    @Override
    public Single<Void> deleteUserStory(final long userStoryId) {
        return mServiceApi.deleteUserStory(userStoryId);
    }
}
