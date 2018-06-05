package com.example.tom.itistracker.tools.utils;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.UserStory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ConvertUtils {

    @NonNull
    public List<String> getTitlesFromSprints(@NonNull final List<Sprint> sprints) {
        List<String> titles = new ArrayList<>();
        for (Sprint sprint : sprints) {
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
