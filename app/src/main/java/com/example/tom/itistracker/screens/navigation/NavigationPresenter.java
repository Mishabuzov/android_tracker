package com.example.tom.itistracker.screens.navigation;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.screens.project_choosing.ProjectChoosingActivity;
import com.example.tom.itistracker.screens.sprints_and_stories.SprintsStoriesSwitchFragment;

import static com.example.tom.itistracker.tools.Constants.FIRST_SCREEN_ITEM;

class NavigationPresenter {

    @NonNull
    private MenuItem mPreviousItem;

    @NonNull
    private NavigationView mView;

    NavigationPresenter(@NonNull final NavigationView navigationView) {
        mView = navigationView;
        mPreviousItem = FIRST_SCREEN_ITEM;
    }

    void checkAndSwitchScreen(@NonNull final MenuItem menuItem) {
        if (menuItem != mPreviousItem) {
            switchScreen(menuItem);
            tryToChangeSelectedItem(menuItem);
        }
    }

    private void tryToChangeSelectedItem(@NonNull final MenuItem menuItem) {
        if (menuItem.isSelectable()) {
            mPreviousItem = menuItem;
        }
    }

    private void switchScreen(@NonNull final MenuItem menuItem) {
        switch (menuItem) {
            case LOG_OUT:
                mView.showLogoutDialog();
                break;
            case SPRINTS:
                mView.changeFragment(new SprintsStoriesSwitchFragment());
                break;
            case PROJECT_CHOOSING:
                mView.startActivity(ProjectChoosingActivity.class);
                break;
        }
    }

}
