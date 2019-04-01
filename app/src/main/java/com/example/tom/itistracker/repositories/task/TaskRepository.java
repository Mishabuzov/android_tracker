package com.example.tom.itistracker.repositories.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.TaskStatusChangeObject;

import java.util.List;

import io.reactivex.Single;

public interface TaskRepository {

    @NonNull
    Single<List<Task>> getTasks(@Nullable final Long sprintId);

    @NonNull
    Single<Task> changeTaskStatus(final long taskStatusId,
                                  @NonNull final TaskStatusChangeObject taskStatusChangeObject);

}
