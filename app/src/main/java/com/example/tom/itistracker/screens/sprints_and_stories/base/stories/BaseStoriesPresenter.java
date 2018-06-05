package com.example.tom.itistracker.screens.sprints_and_stories.base.stories;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.base.BaseSprintAndStoryPresenter;
import com.example.tom.itistracker.tools.functions.BooleanResultFunction;

public abstract class BaseStoriesPresenter<View extends BaseStoriesView> extends BaseSprintAndStoryPresenter<View> {

    protected abstract BooleanResultFunction<MenuItem> getStorySettingsClickFunction(final int adapterPosition,
                                                                                     @NonNull final UserStory userStory);

}
