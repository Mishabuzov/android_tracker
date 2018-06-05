package com.example.tom.itistracker.screens.sprints_and_stories.stories;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesFragment;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesAdapter.StoriesHolderListener;
import com.example.tom.itistracker.tools.functions.Function;

import java.util.List;

public class StoriesFragment extends BaseStoriesFragment<StoriesView, StoriesAdapter, StoriesPresenter>
        implements StoriesView, StoriesHolderListener {

    @InjectPresenter StoriesPresenter mStoriesPresenter;

    private StoriesAdapter mAdapter;

    private SprintsListener mSprintsListener;

    @Override
    protected void doActions() {
        setToolbarTitle(R.string.user_stories_title);
        mStoriesPresenter.setSprintsListener(mSprintsListener);
        mStoriesPresenter.loadUserStories();
    }

    public void setSprintsListener(@NonNull final SprintsListener sprintsListener) {
        mSprintsListener = sprintsListener;
    }

    @Override
    protected StoriesAdapter installAdapter() {
        mAdapter = new StoriesAdapter(this);
        return mAdapter;
    }

    public void removeStoryAfterAttaching(final int storyPosition) {
        mStoriesPresenter.removeStoryAfterAttaching(storyPosition);
    }

    public void addStoryAfterDetaching(@NonNull final UserStory userStory) {
        mStoriesPresenter.addDetachedUserStory(userStory);
        mAdapter.add(userStory);
    }

    @Override
    @MenuRes
    public int getStorySettingsMenu() {
        return R.menu.unassigned_stories_settings_menu;
    }

    @Override
    protected StoriesPresenter getPresenter() {
        return mStoriesPresenter;
    }

    @Override
    protected StoriesAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void showDeleteDialog(@NonNull final Function onDeleteFunction,
                                 @NonNull final String userStoryTitle) {
        mUiUtils.createConfirmDialog(getContext(), onDeleteFunction,
                String.format(getString(R.string.user_story_remove_dialog_title_format),
                        getString(R.string.user_story_remove_dialog_title), userStoryTitle));
    }

    public List<UserStory> getUnassignedStories() {
        return mStoriesPresenter.getUnassignedUserStories();
    }

    public interface SprintsListener {
        List<Sprint> getSprints();

        void refreshSprintScreen();
    }

}
