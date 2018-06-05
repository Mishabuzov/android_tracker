package com.example.tom.itistracker.screens.sprints_and_stories.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tom.itistracker.models.network.AttachStoryToSprintObject;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.repositories.story.StoryRepository;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;
import com.example.tom.itistracker.screens.base.presenters.BaseRequestPresenter;

public abstract class BaseSprintAndStoryPresenter<View extends BaseFragmentView> extends BaseRequestPresenter<View> {

    protected abstract StoryRepository getStoryRepository();

    protected abstract void refreshAfterAttaching(final int storyPosition);

    protected final void attachStoryToSprint(final int storyPosition,
                                             @NonNull final UserStory chosenStory,
                                             @Nullable final Long sprintId) {
        defaultRequestProcessing(getStoryRepository().attachUserStoryToSprint(chosenStory.getId(),
                new AttachStoryToSprintObject(chosenStory.getVersion(), sprintId)),
                newStory -> {
                    refreshAfterAttaching(storyPosition);
                    if (sprintId == null) {
                        disAttachStory(newStory);
                    }
                });
    }

    protected void disAttachStory(@NonNull final UserStory userStory) {
    }

}
