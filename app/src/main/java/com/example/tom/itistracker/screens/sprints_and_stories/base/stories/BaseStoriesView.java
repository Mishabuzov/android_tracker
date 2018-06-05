package com.example.tom.itistracker.screens.sprints_and_stories.base.stories;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;
import com.example.tom.itistracker.tools.functions.ResultFunction;

import java.util.List;

public interface BaseStoriesView extends BaseFragmentView {

    void showStories(@NonNull final List<UserStory> stories);

    void removeFromStoriesScreen(final int position);

    void showAttachingDialog(@NonNull final List<String> sprintTitles,
                             @NonNull final ResultFunction<Integer> attachingFunction);


}
