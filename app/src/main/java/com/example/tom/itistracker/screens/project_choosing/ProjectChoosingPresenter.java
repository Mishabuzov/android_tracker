package com.example.tom.itistracker.screens.project_choosing;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.models.network.Project;
import com.example.tom.itistracker.repositories.project.ProjectRepository;
import com.example.tom.itistracker.screens.base.presenters.BaseRequestPresenter;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class ProjectChoosingPresenter extends BaseRequestPresenter<ProjectChoosingView> {

    @Inject ProjectRepository mProjectRepository;

    @Inject PreferenceUtils mPreferenceUtils;

    private List<Project> mProjects;

    private boolean mIsProjectLoaded;

    ProjectChoosingPresenter() {
        mProjects = new ArrayList<>();
        App.getComponent().inject(this);
    }

    void loadProjects() {
        if (!mIsProjectLoaded) {
            sendingLoadRequest();
        } else {
            showProjects(mProjects);
        }
    }

    private void sendingLoadRequest() {
        defaultRequestProcessing(mProjectRepository.getUserProjects(mPreferenceUtils.getUserProfile().getId()),
                this::onSuccessGettingProjects);
        showLoading();
    }

    private void onSuccessGettingProjects(@NonNull final List<Project> projects) {
        mIsProjectLoaded = true;
        mProjects = projects;
        showProjects(projects);
    }

    private void showProjects(@NonNull final List<Project> projects) {
        getViewState().showProjects(projects);
    }

    void saveChosenProjectId(final long projectId) {
        mPreferenceUtils.saveChosenProjectId(projectId);
    }

    @Override
    protected String setTagForLogging() {
        return "ProjectChoosingPresenter: ";
    }

}
