package com.example.tom.itistracker.repositories.story;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.AttachStoryToSprintObject;
import com.example.tom.itistracker.models.network.UserStory;

import java.util.List;

import io.reactivex.Single;

public interface StoryRepository {

    @NonNull
    Single<List<UserStory>> getUserStories(final long projectId);

    @NonNull
    Single<UserStory> attachUserStoryToSprint(final long userStoryId,
                                              @NonNull final AttachStoryToSprintObject attachObject);

    @NonNull
    Single<Void> deleteUserStory(final long userStoryId);

}
