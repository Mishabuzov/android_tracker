package com.example.tom.itistracker.repositories.project;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.project.Project;
import com.example.tom.itistracker.models.network.project.ProjectDetails;

import java.util.List;

import io.reactivex.Single;

public interface ProjectRepository {

    @NonNull
    Single<List<Project>> getUserProjects(final long userId);

    @NonNull
    Single<ProjectDetails> getProjectDetails(final long projectId);

}
