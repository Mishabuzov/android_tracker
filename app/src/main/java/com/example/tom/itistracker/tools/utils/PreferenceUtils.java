package com.example.tom.itistracker.tools.utils;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.models.network.project.TaskStatusInProject;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;


public final class PreferenceUtils {

    private static final String TOKEN_KEY = "token";

    private static final String TOKEN_PRESCRIPT = "Bearer";

    private static final String USER_PROFILE_KEY = "profile";

    private static final String NEXT_FROM = "next_from";

    private static final String PROJECT_ID = "project_id";

    private static final String PROJECT_TASK_STATUSES = "project_task_statuses";

    private static final List<Object> EMPTY_LIST = new ArrayList<>();

    private void saveToken(@NonNull final String token) {
        Hawk.put(TOKEN_KEY, String.format("%1$s %2$s", TOKEN_PRESCRIPT, token));
    }

    @NonNull
    public String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    @NonNull
    public User getUserProfile() {
        return Hawk.get(USER_PROFILE_KEY);
    }

    public void saveUserProfile(@NonNull final User user) {
        saveToken(user.getToken());
        Hawk.put(USER_PROFILE_KEY, user);
    }

    public void saveNextFromValue(@NonNull final String nextFrom) {
        Hawk.put(NEXT_FROM, nextFrom);
    }

    @NonNull
    public String getStartFromValue() {
        return Hawk.get(NEXT_FROM, "");
    }

    public void clearNextFromValue() {
        Hawk.remove(NEXT_FROM);
    }

    public boolean isSignedIn() {
        return !getToken().isEmpty();
    }

    public boolean isProjectChosen() {
        return getChosenProjectId() != 0;
    }

    public void saveChosenProjectId(final long projectId) {
        Hawk.put(PROJECT_ID, projectId);
    }

    public long getChosenProjectId() {
        return Hawk.get(PROJECT_ID, 0L);
    }

    public void saveAllProjectTaskStatuses(@NonNull final List<TaskStatusInProject> taskStatuses) {
        Hawk.put(PROJECT_TASK_STATUSES, taskStatuses);
    }

    public List<TaskStatusInProject> getAllProjectTaskStatuses() {
        return Hawk.get(PROJECT_TASK_STATUSES);
    }

    public void clearPreference() {
        Hawk.remove(TOKEN_KEY, USER_PROFILE_KEY, NEXT_FROM, PROJECT_ID, PROJECT_TASK_STATUSES);
    }

}
