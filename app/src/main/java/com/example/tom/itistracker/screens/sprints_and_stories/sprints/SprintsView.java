package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;

import java.util.List;

public interface SprintsView extends BaseFragmentView {

    void removeSprintFromAdapter(@NonNull final SprintLocalModel sprint);

    void showSprints(@NonNull final List<SprintLocalModel> sprints);

}
