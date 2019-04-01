package com.example.tom.itistracker.repositories.project;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.project.Project;
import com.example.tom.itistracker.models.network.project.ProjectDetails;
import com.example.tom.itistracker.network.ServiceApi;

import java.util.List;

import io.reactivex.Single;

public class ProjectRepositoryImplementation implements ProjectRepository {

    @NonNull
    private final ServiceApi mServiceApi;

    public ProjectRepositoryImplementation(@NonNull final ServiceApi serviceApi) {
        mServiceApi = serviceApi;
    }

    @NonNull
    @Override
    public Single<List<Project>> getUserProjects(final long userId) {
        return mServiceApi.getUserProjects(userId);
    }

    @NonNull
    @Override
    public Single<ProjectDetails> getProjectDetails(final long projectId) {
        return mServiceApi.getProjectDetails(projectId);
    }

}
