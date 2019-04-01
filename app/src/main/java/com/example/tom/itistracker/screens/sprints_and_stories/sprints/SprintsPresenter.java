package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.example.tom.itistracker.App;
import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.repositories.sprint.SprintRepository;
import com.example.tom.itistracker.repositories.story.StoryRepository;
import com.example.tom.itistracker.repositories.task.TaskRepository;
import com.example.tom.itistracker.screens.sprints_and_stories.base.BaseSprintAndStoryPresenter;
import com.example.tom.itistracker.screens.sprints_and_stories.sprints.SprintsFragment.StoriesListener;
import com.example.tom.itistracker.tools.functions.ResultFunction;
import com.example.tom.itistracker.tools.utils.ConvertUtils;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class SprintsPresenter extends BaseSprintAndStoryPresenter<SprintsView> {

    @Inject StoryRepository mStoryRepository;

    @Inject ConvertUtils mConvertUtils;

    @Inject SprintRepository mSprintRepository;

    @Inject TaskRepository mTaskRepository;

    @Inject PreferenceUtils mPrefUtils;

    private StoriesListener mStoriesListener;

    private List<SprintLocalModel> mSprints;

    private boolean mIsSprintsLoaded;

    SprintsPresenter() {
        App.getComponent().inject(this);
        mSprints = new ArrayList<>();
    }

    public void loadSprints() {
        if (!mIsSprintsLoaded) {
            sendingLoadSprintsRequest();
        } else {
            showSprints();
        }
    }

    final List<SprintLocalModel> getSprints() {
        return mSprints;
    }

    public final void setSprints(@NonNull final List<SprintLocalModel> sprints) {
        mIsSprintsLoaded = true;
        mSprints = sprints;
    }

    private void sendingLoadSprintsRequest() {
        defaultRequestProcessing(mSprintRepository.getSprints(mPrefUtils.getChosenProjectId()),
                sprints -> defaultRequestProcessing(mTaskRepository.getTasks(null),
                        tasks -> processSprintsInfo(sprints, tasks)));
    }

    private void showSprints() {
        getViewState().showSprints(mSprints);
    }

    private void processSprintsInfo(@NonNull final List<Sprint> sprints,
                                    @NonNull final List<Task> tasks) {
        List<SprintLocalModel> localModels = mConvertUtils.convertSprintsToLocalFormat(sprints, tasks);
        setSprints(localModels);
        showSprints();
    }

  /*  final void detachStoryFromSprint(final long userStoryId,
                                     final int userStoryVersion) {
        defaultRequestProcessing(mStoryRepository
                        .attachUserStoryToSprint(userStoryId, new AttachStoryToSprintObject(userStoryVersion, null)),
                this::onSuccessfullyStoryDetached);
    }*/

    final void refreshData() {
        mIsSprintsLoaded = false;
        loadSprints();
    }

    final void checkToRefresh(final boolean isNeedToRefresh,
                              @Nullable final UserStory detachedStory) {
        if (isNeedToRefresh) {
            refreshData();
        }
        if (detachedStory != null) {
            mStoriesListener.addStoryAfterDetaching(detachedStory);
        }
    }

    final void setStoriesListener(StoriesListener storiesListener) {
        mStoriesListener = storiesListener;
    }

    @NonNull
    final List<String> getStoriesTitles() {
        return mConvertUtils.getTitlesFromStories(mStoriesListener.getUnassignedStories());
    }

    @NonNull
    private UserStory getStoryByIndex(final int storyIndex) {
        return mStoriesListener.getUnassignedStories().get(storyIndex);
    }

    final ResultFunction<Integer> getOnChoosingStoryFunction(final long sprintId) {
        return (storyIndex) -> attachStoryToSprint(
                storyIndex, getStoryByIndex(storyIndex), sprintId
        );
    }

    final void deleteSprint(@NonNull final SprintLocalModel sprint) {
        defaultRequestProcessing(mSprintRepository.deleteSprint(sprint.getId()), result -> onSuccessSprintDeleted(sprint));
    }

    private void onSuccessSprintDeleted(@NonNull final SprintLocalModel sprint) {
        mSprints.remove(sprint);
    }

    /*private void onSuccessfullyStoryDetached(@NonNull final UserStory userStory) {

    }*/

    @Override
    protected StoryRepository getStoryRepository() {
        return mStoryRepository;
    }

    @Override
    protected void refreshAfterAttaching(final int storyPosition) {
        mStoriesListener.removeStoryAfterAttaching(storyPosition);
        refreshData();
    }

    @Override
    protected String setTagForLogging() {
        return "SprintsPresenter: ";
    }

}