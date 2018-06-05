package com.example.tom.itistracker.screens.sprints_and_stories.stories;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.repositories.story.StoryRepository;
import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesPresenter;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesFragment.SprintsListener;
import com.example.tom.itistracker.tools.functions.BooleanResultFunction;
import com.example.tom.itistracker.tools.utils.ConvertUtils;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class StoriesPresenter extends BaseStoriesPresenter<StoriesView> {

    @Inject StoryRepository mStoryRepository;

    @Inject PreferenceUtils mPrefUtils;

    @Inject ConvertUtils mConvertUtils;

    @NonNull
    private List<UserStory> mUserStories;

    private boolean mIsUserStoriesDownloaded;

    private SprintsListener mSprintsListener;

    StoriesPresenter() {
        App.getComponent().inject(this);
        mUserStories = new ArrayList<>();
    }

    void loadUserStories() {
        if (!mIsUserStoriesDownloaded) {
            sendingStoriesRequest();
        } else {
            showStories(mUserStories);
        }
    }

    void setSprintsListener(@NonNull final SprintsListener sprintsListener) {
        mSprintsListener = sprintsListener;
    }

    @NonNull
    private List<String> getTitlesFromSprints() {
        return mConvertUtils.getTitlesFromSprints(mSprintsListener.getSprints());
    }

    private long getSprintIdByIndex(final int sprintIndex) {
        return mSprintsListener.getSprints().get(sprintIndex).getId();
    }

    protected final BooleanResultFunction<MenuItem> getStorySettingsClickFunction(final int adapterPosition,
                                                                                  @NonNull final UserStory userStory) {
        return item -> {
            switch (item.getItemId()) {
                case R.id.story_move_to_sprint:
                    getViewState().showAttachingDialog(getTitlesFromSprints(),
                            (sprintIndex) -> attachStoryToSprint(
                                    adapterPosition, userStory, getSprintIdByIndex(sprintIndex))
                    );
                    return true;
                case R.id.story_delete:
                    getViewState().showDeleteDialog(() -> deleteUserStory(adapterPosition, userStory.getId()),
                            userStory.getTitle());
                    return true;
                default:
                    return false;
            }
        };
    }

    @NonNull
    final List<UserStory> getUnassignedUserStories() {
        return mUserStories;
    }

    private void deleteUserStory(final int position,
                                 final long storyId) {
        defaultRequestProcessing(mStoryRepository.deleteUserStory(storyId),
                result -> refreshAfterAttaching(position));
    }

    @Override
    protected StoryRepository getStoryRepository() {
        return mStoryRepository;
    }

    /**
     * Refreshing data in List, Stories and Sprint screens.
     *
     * @param storyPosition - UserStory position in adapter and List;
     */
    protected void refreshAfterAttaching(final int storyPosition) {
        removeStoryAfterAttaching(storyPosition);
        mSprintsListener.refreshSprintScreen();
    }

    protected final void removeStoryAfterAttaching(final int storyPosition) {
        mUserStories.remove(storyPosition);
        getViewState().removeFromStoriesScreen(storyPosition);
    }

    private void sendingStoriesRequest() {
        defaultRequestProcessing(mStoryRepository.getUserStories(mPrefUtils.getChosenProjectId()),
                this::onSuccessLoadedStories);
    }

    private void onSuccessLoadedStories(@NonNull final List<UserStory> userStories) {
        mIsUserStoriesDownloaded = true;
        mUserStories = userStories;
        showStories(userStories);
    }

    private void showStories(@NonNull final List<UserStory> userStories) {
        getViewState().showStories(userStories);
    }

    final void addDetachedUserStory(@NonNull final UserStory userStory) {
        mUserStories.add(userStory);
    }

    @Override
    protected String setTagForLogging() {
        return "StoriesPresenter: ";
    }
}