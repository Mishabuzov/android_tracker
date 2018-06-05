package com.example.tom.itistracker.repositories.sprint;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Sprint;

import java.util.List;

import io.reactivex.Single;

public interface SprintRepository {

    @NonNull
    Single<List<Sprint>> getSprints(final long projectId);

    @NonNull
    Single<Void> deleteSprint(final long sprintId);

}
