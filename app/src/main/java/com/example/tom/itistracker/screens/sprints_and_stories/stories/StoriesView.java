package com.example.tom.itistracker.screens.sprints_and_stories.stories;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesView;
import com.example.tom.itistracker.tools.functions.Function;

public interface StoriesView extends BaseStoriesView {

    void showDeleteDialog(@NonNull final Function onDeleteFunction,
                          @NonNull final String userStoryTitle);

}
