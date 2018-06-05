package com.example.tom.itistracker.screens.sprint_details;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesView;

public interface SprintDetailsView extends BaseStoriesView {

    void setNeedToRefresh();

    void rememberDisAttachedStory(@NonNull final UserStory userStory);

}
