package com.example.tom.itistracker.models.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

public enum NotificationType {

    OVERDUE_SPRINT("overdue_sprint"),

    ADDING_MEMBER_WITH_MENTOR("adding_team_member"),

    ADDING_MEMBER_WITHOUT_MENTOR("adding_member_without_mentor"),

    DELETING_TEAM_MEMBER("deleting_team_member"),

    TEAM_MEMBER_HAS_LEFT_PROJECT("team_member_has_left_project"),

    REVIEW_NEWCOMER("review_newcomer"),

    HIGH_PRIORITY_ISSUE_WITHOUT_EXECUTOR("high_priority_issue_without_executor"),

    DEADLINE_IS_CLOSE("deadline_is_closed"),

    BLOCKED_TASKS("blocked_tasks"),

    USER_WITHOUT_TASKS("user_without_tasks"),

    SPRINT_FINISHED_BEFORE_DEADLINE("sprint_finished_before_deadline"),

    NEW_USER_STORY_CREATED("new_user_story_created"),

    UNEXPECTED_NOTIFICATION("unexpected_notification");

    private String mNotification;

    NotificationType(@NonNull final String notification) {
        mNotification = notification;
    }

    @NonNull
    public String getNotificationString() {
        return mNotification;
    }

    @NonNull
    public static NotificationType getTypeByValue(@Nullable final String value) {
        for (NotificationType notificationType : NotificationType.values()) {
            if (notificationType.getNotificationString().equals(value)) {
                return notificationType;
            }
        }
        Timber.w("Wrong notification from server: %1$s", value);
        return UNEXPECTED_NOTIFICATION;
    }

}
