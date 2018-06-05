package com.example.tom.itistracker.screens.taskboard;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;

interface TaskboardView extends BaseFragmentView {

    void attachToNewTaskboard(@NonNull final Task task);

    void attachToProgressTaskboard(@NonNull final Task task);

    void attachCheckReadyTaskboard(@NonNull final Task task);

    void attachToClosedTaskboard(@NonNull final Task task);

    void attachToBlockedTaskboard(@NonNull final Task task);

}
