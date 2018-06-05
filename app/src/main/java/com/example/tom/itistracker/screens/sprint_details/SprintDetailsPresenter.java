package com.example.tom.itistracker.screens.sprint_details;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.repositories.story.StoryRepository;
import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesPresenter;
import com.example.tom.itistracker.tools.functions.BooleanResultFunction;
import com.example.tom.itistracker.tools.utils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class SprintDetailsPresenter extends BaseStoriesPresenter<SprintDetailsView> {

    @Inject StoryRepository mStoryRepository;

    @Inject ConvertUtils mConvertUtils;

    @NonNull
    private List<Sprint> mAnotherSprints;

    private Sprint mCurrentSprint;

    SprintDetailsPresenter() {
        mAnotherSprints = new ArrayList<>();
        App.getComponent().inject(this);
    }

    //Warning!
    //It's necessary to set sprints from view!
    final void setAnotherSprints(@NonNull final List<Sprint> anotherSprints) {
        mAnotherSprints = anotherSprints;
    }

    //Warning!
    //It's necessary to set current sprint from view!
    final void setCurrentSprintAndShowStories(@NonNull final Sprint currentSprint) {
        mCurrentSprint = currentSprint;
    }

    final void showStoriesOfSprint() {
        getViewState().showStories(mCurrentSprint.getUserStories());
    }

    private long getSprintIdByIndex(final int sprintIndex) {
        return mAnotherSprints.get(sprintIndex).getId();
    }

    protected final BooleanResultFunction<MenuItem> getStorySettingsClickFunction(final int adapterPosition,
                                                                                  @NonNull final UserStory userStory) {
        return item -> {
            switch (item.getItemId()) {
                case R.id.story_move_to_sprint:
                    getViewState().showAttachingDialog(mConvertUtils.getTitlesFromSprints(mAnotherSprints),
                            (sprintIndex) -> attachStoryToSprint(
                                    adapterPosition, userStory, getSprintIdByIndex(sprintIndex))
                    );
                    return true;
                case R.id.story_delete_from_sprint:
                    attachStoryToSprint(adapterPosition, userStory, null);
                    return true;
                default:
                    return false;
            }
        };
    }


    @Override
    protected StoryRepository getStoryRepository() {
        return mStoryRepository;
    }

    @Override
    protected final void refreshAfterAttaching(final int storyPosition) {
        mCurrentSprint.getUserStories().remove(storyPosition);
        getViewState().removeFromStoriesScreen(storyPosition);
        getViewState().setNeedToRefresh();
    }

    @Override
    protected final void disAttachStory(@NonNull final UserStory userStory) {
        getViewState().rememberDisAttachedStory(userStory);
    }

    @Override
    protected final String setTagForLogging() {
        return "SprintDetailsPresenter: ";
    }
}
