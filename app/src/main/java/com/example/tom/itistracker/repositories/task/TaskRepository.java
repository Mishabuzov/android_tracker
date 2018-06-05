package com.example.tom.itistracker.repositories.task;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.TaskStatusChangeObject;

import java.util.List;

import io.reactivex.Single;

public interface TaskRepository {

    @NonNull
    Single<List<Task>> getSprintTasks(final long sprintId);

    @NonNull
    Single<Task> changeTaskStatus(final long taskId,
                                  @NonNull final TaskStatusChangeObject taskStatusChangeObject);

}
