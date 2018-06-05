package com.example.tom.itistracker.screens.sprint_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.base.stories.BaseStoriesFragment;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesAdapter;
import com.example.tom.itistracker.screens.taskboard.TaskboardActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.tom.itistracker.screens.sprint_details.SprintDetailsActivity.ANOTHER_SPRINTS;
import static com.example.tom.itistracker.screens.sprint_details.SprintDetailsActivity.SPRINT;

public class SprintDetailsFragment extends BaseStoriesFragment<SprintDetailsView, SprintDetailsAdapter, SprintDetailsPresenter>
        implements SprintDetailsView, StoriesAdapter.StoriesHolderListener, SprintDetailsAdapter.TaskboardButtonListener {

    public static final String IS_NEED_TO_REFRESH = "is_need_to_refresh";

    public static final String DETACHED_STORY = "detached_story";

    @InjectPresenter SprintDetailsPresenter mSprintDetailsPresenter;

    private SprintDetailsAdapter mSprintDetailsAdapter;

    private Sprint mCurrentSprint;

    private ArrayList<Sprint> mAnotherSprints;

    private Intent mResultIntent;

    @Override
    protected void doActions() {
        setToolbarTitle(String.format(getString(R.string.sprint_details_title_format),
                mCurrentSprint.getName()));
        changeEmptyViewTitle(R.string.sprint_details_empty_title);
        mSprintDetailsPresenter.setAnotherSprints(mAnotherSprints);
        mSprintDetailsPresenter.setCurrentSprintAndShowStories(mCurrentSprint);
        mResultIntent = new Intent();
        mSprintDetailsPresenter.showStoriesOfSprint();
    }

    @Override
    protected SprintDetailsAdapter installAdapter() {
        mSprintDetailsAdapter = new SprintDetailsAdapter(this, this);
        return mSprintDetailsAdapter;
    }

    @Override
    protected void getArgs() {
        final Bundle args = getArguments();
        if (args != null) {
            mCurrentSprint = args.getParcelable(SPRINT);
            mAnotherSprints = args.getParcelableArrayList(ANOTHER_SPRINTS);
        }
    }

    @Override
    public void showTaskboard() {
        TaskboardActivity.startActivity(getContext(), mCurrentSprint.getId(), mCurrentSprint.getName());
    }

    @Override
    @MenuRes
    public int getStorySettingsMenu() {
        return R.menu.assigned_stories_settings;
    }

    @Override
    public void rememberDisAttachedStory(@NonNull final UserStory userStory) {
        mResultIntent.putExtra(DETACHED_STORY, userStory);
    }

    @Override
    public void setNeedToRefresh() {
        mResultIntent.putExtra(IS_NEED_TO_REFRESH, true);
        getActivity().setResult(RESULT_OK, mResultIntent);
    }

    @Override
    protected SprintDetailsPresenter getPresenter() {
        return mSprintDetailsPresenter;
    }

    @Override
    protected SprintDetailsAdapter getAdapter() {
        return mSprintDetailsAdapter;
    }
}
