package com.example.tom.itistracker.screens.taskboard;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.models.local.TaskStatus;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.TaskStatusChangeObject;
import com.example.tom.itistracker.repositories.task.TaskRepository;
import com.example.tom.itistracker.screens.base.presenters.BaseRequestPresenter;
import com.example.tom.itistracker.tools.functions.ResultFunction;
import com.example.tom.itistracker.tools.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

@InjectViewState
public class TaskboardPresenter extends BaseRequestPresenter<TaskboardView> {

    @Inject TaskRepository mTaskRepository;

//    @Inject PreferenceUtils mPreferenceUtils;

    @Inject ConvertUtils mConvertUtils;

    private boolean mIsTasksLoaded;

    @NonNull
    private List<Task> mAllTasks;

    private long mClickedSprintId;

    TaskboardPresenter() {
        App.getComponent().inject(this);
        mAllTasks = new ArrayList<>();
    }

    final void loadTasks() {
        if (mIsTasksLoaded) {
            processTasks(mAllTasks);
        } else {
            sendingLoadTasksRequest();
        }
    }

    final void setClickedSprintId(final long clickedSprintId) {
        mClickedSprintId = clickedSprintId;
    }

    private void sendingLoadTasksRequest() {
        defaultRequestProcessing(mTaskRepository.getTasks(mClickedSprintId), this::processTasks);
    }

    private void processTasks(@NonNull final List<Task> tasks) {
        mIsTasksLoaded = true;
        mAllTasks = tasks;
        for (Task task : tasks) {
            distributeTask(task);
        }
    }

    private void distributeTask(@NonNull final Task task) {
        switch (task.getStatus()) {
            case NEW:
                getViewState().attachToNewTaskboard(task);
                break;
            case CLOSED:
                getViewState().attachToClosedTaskboard(task);
                break;
            case BLOCKED:
                getViewState().attachToBlockedTaskboard(task);
                break;
            case IN_PROGRESS:
                getViewState().attachToProgressTaskboard(task);
                break;
            case READY_FOR_TEST:
                getViewState().attachCheckReadyTaskboard(task);
                break;
        }
    }

    final void changeTaskStatus(@NonNull final Task taskWithOldStatus,
                                @IdRes final int dropTargetId,
                                @NonNull final ResultFunction<Task> onSuccessStatusChanged) {
        final TaskStatus newTaskStatus = Single.fromCallable(() -> TaskStatus.getStatusByBoardId(dropTargetId))
                .doOnError(this::handleError).blockingGet();
        defaultRequestProcessing(mTaskRepository.changeTaskStatus(taskWithOldStatus.getId(),
                new TaskStatusChangeObject(mConvertUtils.getNewTaskStatusId(newTaskStatus.getName()),
                        taskWithOldStatus.getVersion())),
                refreshedTask -> {
                    mAllTasks.remove(taskWithOldStatus);
                    mAllTasks.add(refreshedTask);
                    onSuccessStatusChanged.action(refreshedTask);
                });
    }

    @Override
    protected String setTagForLogging() {
        return "TaskboardPresenter: ";
    }

}
