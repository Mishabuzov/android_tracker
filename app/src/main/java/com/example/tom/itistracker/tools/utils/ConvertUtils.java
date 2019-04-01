package com.example.tom.itistracker.tools.utils;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.local.BlockedTasksCountSparseArray;
import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.models.network.project.TaskStatusInProject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ConvertUtils {

    private PreferenceUtils mPreferenceUtils;

    public ConvertUtils(@NonNull final PreferenceUtils preferenceUtils) {
        mPreferenceUtils = preferenceUtils;
    }

    public long getNewTaskStatusId(@NonNull final String newTaskStatusName) {
        List<TaskStatusInProject> allStatuses = mPreferenceUtils.getAllProjectTaskStatuses();
        for (TaskStatusInProject taskStatusInProject : allStatuses) {
            if (taskStatusInProject.getName().equals(newTaskStatusName)) {
                return taskStatusInProject.getId();
            }
        }
        return 0L;
    }

    @NonNull
    public List<SprintLocalModel> convertSprintsToLocalFormat(@NonNull final List<Sprint> sprints,
                                                              @NonNull final List<Task> allProjectTasks) {
        BlockedTasksCountSparseArray blockedTasksArray = new BlockedTasksCountSparseArray();

        for (Task task : allProjectTasks) {
            blockedTasksArray.incrementCounterIfBlocked(task);
        }

        ArrayList<SprintLocalModel> localModels = new ArrayList<>();

        for (Sprint sprint : sprints) {
            SprintLocalModel localModel = new SprintLocalModel();
            localModel.setId(sprint.getId());
            localModel.setClosed(sprint.isClosed());
            localModel.setName(sprint.getName());
            localModel.setStartDate(sprint.getStartDate());
            localModel.setFinishDate(sprint.getFinishDate());
            localModel.setClosedPoints(sprint.getClosedPoints());
            localModel.setTotalPoints(sprint.getTotalPoints());
            localModel.setUserStories(sprint.getUserStories());
            localModel.setBlockedTasksCount(blockedTasksArray.get(sprint.getId(), 0));
            localModels.add(localModel);
        }
        return localModels;
    }

    @NonNull
    public List<String> getTitlesFromSprints(@NonNull final List<SprintLocalModel> sprints) {
        List<String> titles = new ArrayList<>();
        for (SprintLocalModel sprint : sprints) {
            titles.add(sprint.getName());
        }
        return titles;
    }

    @NonNull
    public List<String> getTitlesFromStories(@NonNull final List<UserStory> userStories) {
        List<String> titles = new ArrayList<>();
        for (UserStory userStory : userStories) {
            titles.add(userStory.getTitle());
        }
        return titles;
    }

    @NonNull
    public String convertMillisToHourReadableFormat(final long millis) {
        long hoursLong = TimeUnit.MILLISECONDS.toHours(millis);
        long minutesLong = TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(hoursLong);
        long secondsLong = TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.HOURS.toSeconds(hoursLong) - TimeUnit.MINUTES.toSeconds(minutesLong);
        String hours = String.valueOf(hoursLong);
        String minutes = String.valueOf(minutesLong);
        String seconds = String.valueOf(secondsLong);
        if (hoursLong < 10) {
            hours = "0" + hours;
        }
        if (minutesLong < 10) {
            minutes = "0" + minutes;
        }
        if (secondsLong < 10) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s:%s",
                hours, minutes, seconds);
    }

}
