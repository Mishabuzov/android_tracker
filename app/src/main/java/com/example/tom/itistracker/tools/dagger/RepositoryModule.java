package com.example.tom.itistracker.tools.dagger;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.repositories.auth.AuthRepository;
import com.example.tom.itistracker.repositories.auth.AuthRepositoryImplementation;
import com.example.tom.itistracker.repositories.project.ProjectRepository;
import com.example.tom.itistracker.repositories.project.ProjectRepositoryImplementation;
import com.example.tom.itistracker.repositories.sprint.SprintRepository;
import com.example.tom.itistracker.repositories.sprint.SprintRepositoryImplementation;
import com.example.tom.itistracker.repositories.story.StoryRepository;
import com.example.tom.itistracker.repositories.story.StoryRepositoryImplementation;
import com.example.tom.itistracker.repositories.task.TaskRepository;
import com.example.tom.itistracker.repositories.task.TaskRepositoryImplementation;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public AuthRepository provideAuthRepository(@NonNull final ServiceApi serviceApi) {
        return new AuthRepositoryImplementation(serviceApi);
    }

    @Singleton
    @Provides
    public ProjectRepository provideProjectRepository(@NonNull final ServiceApi serviceApi) {
        return new ProjectRepositoryImplementation(serviceApi);
    }

    @Singleton
    @Provides
    public StoryRepository provideStoryRepository(@NonNull final ServiceApi serviceApi) {
        return new StoryRepositoryImplementation(serviceApi);
    }

    @Singleton
    @Provides
    public SprintRepository provideSprintRepository(@NonNull final ServiceApi serviceApi) {
        return new SprintRepositoryImplementation(serviceApi);
    }

    @Singleton
    @Provides
    public TaskRepository provideTaskRepository(@NonNull final ServiceApi serviceApi,
                                                @NonNull final PreferenceUtils prefUtils) {
        return new TaskRepositoryImplementation(serviceApi, prefUtils);
    }

}
