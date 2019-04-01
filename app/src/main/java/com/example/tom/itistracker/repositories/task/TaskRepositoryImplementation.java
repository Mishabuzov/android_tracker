package com.example.tom.itistracker.repositories.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    /**
     * @param sprintId - id of sprint, which tasks assigned to. Pass null, if you want to get all
     *                 project tasks.
     * @return List<Tasks> - specified tasks of sprint or all project.
     */
    @NonNull
    @Override
    public Single<List<Task>> getTasks(@Nullable final Long sprintId) {
        return mServiceApi.getTasks(mPrefUtils.getChosenProjectId(), sprintId);
    }

    @NonNull
    @Override
    public Single<Task> changeTaskStatus(final long taskStatusId,
                                         @NonNull final TaskStatusChangeObject taskStatusChangeObject) {
        return mServiceApi.changeTaskStatus(taskStatusId, taskStatusChangeObject);
    }

}
