package com.example.tom.itistracker.repositories.sprint;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.network.ServiceApi;

import java.util.List;

import io.reactivex.Single;

public class SprintRepositoryImplementation implements SprintRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public SprintRepositoryImplementation(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public Single<List<Sprint>> getSprints(final long projectId) {
        return mServiceApi.getSprints(projectId);
    }

    @NonNull
    @Override
    public Single<Void> deleteSprint(final long sprintId) {
        return mServiceApi.deleteSprint(sprintId);
    }

}
