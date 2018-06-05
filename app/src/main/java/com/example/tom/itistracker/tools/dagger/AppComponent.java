package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.screens.auth.login.LoginPresenter;
import com.example.tom.itistracker.screens.base.activities.StartActivity;
import com.example.tom.itistracker.screens.base.fragments.BaseFragment;
import com.example.tom.itistracker.screens.navigation.NavigationActivity;
import com.example.tom.itistracker.screens.project_choosing.ProjectChoosingPresenter;
import com.example.tom.itistracker.screens.sprint_details.SprintDetailsPresenter;
import com.example.tom.itistracker.screens.sprints_and_stories.sprints.SprintsPresenter;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesPresenter;
import com.example.tom.itistracker.screens.taskboard.TaskboardPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class,
        ApplicationModule.class,
        RepositoryModule.class,
        NetworkModule.class,
        UtilsModule.class
})
public interface AppComponent {

    //Activities
    void inject(StartActivity startActivity);

    void inject(NavigationActivity navigationActivity);

    //Fragments
    void inject(BaseFragment baseFragment);

    //Presenters
    void inject(LoginPresenter loginPresenter);

    void inject(ProjectChoosingPresenter projectChoosingPresenter);

    void inject(StoriesPresenter storiesPresenter);

    void inject(SprintsPresenter sprintsPresenter);

    void inject(SprintDetailsPresenter sprintDetailsPresenter);

    void inject(TaskboardPresenter taskboardPresenter);

}
