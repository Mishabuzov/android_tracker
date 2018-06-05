package com.example.tom.itistracker.repositories.task;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.TaskStatusChangeObject;
import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import java.util.List;

import io.reactivex.Single;

public class TaskRepositoryImplementation implements TaskRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    @NonNull
    private final PreferenceUtils mPrefUtils;

    public TaskRepositoryImplementation(@NonNull final ServiceApi serviceApi,
                                        @NonNull final PreferenceUtils prefUtils) {
        mServiceApi = serviceApi;
        mPrefUtils = prefUtils;
    }

    @NonNull
    @Override
    public Single<List<Task>> getSprintTasks(final long sprintId) {
        return mServiceApi.getSprintTasks(mPrefUtils.getChosenProjectId(), sprintId);
    }

    @NonNull
    @Override
    public Single<Task> changeTaskStatus(final long taskId, @NonNull final TaskStatusChangeObject taskStatusChangeObject) {
        return mServiceApi.changeTaskStatus(taskId, taskStatusChangeObject);
    }

}
