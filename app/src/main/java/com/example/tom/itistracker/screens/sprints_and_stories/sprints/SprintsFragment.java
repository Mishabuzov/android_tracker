package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.base.fragments.BaseRecyclerFragment;
import com.example.tom.itistracker.screens.sprint_details.SprintDetailsActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static com.example.tom.itistracker.screens.sprint_details.SprintDetailsFragment.DETACHED_STORY;
import static com.example.tom.itistracker.screens.sprint_details.SprintDetailsFragment.IS_NEED_TO_REFRESH;

public class SprintsFragment extends BaseRecyclerFragment<SprintsAdapter> implements SprintsView,
        SprintsAdapter.OnItemClickListener<SprintLocalModel>, SprintsAdapter.SprintSettingsListener {

    private static final int REQUEST_CODE_SPRINT_DETAILS = 1;

    @InjectPresenter SprintsPresenter mSprintsPresenter;

    private SprintsAdapter mAdapter;

    private StoriesListener mStoriesListener;

    @Override
    protected void doActions() {
        setToolbarTitle(R.string.sprints_title);
        mSprintsPresenter.setStoriesListener(mStoriesListener);
        mSprintsPresenter.loadSprints();
        String id = FirebaseInstanceId.getInstance().getToken();
        Timber.d(id);
    }

    public void refreshData() {
        mSprintsPresenter.refreshData();
    }

    @Override
    protected SprintsAdapter installAdapter() {
        mAdapter = new SprintsAdapter(this, this);
        return mAdapter;
    }

    @Override
    public void showSprints(@NonNull final List<SprintLocalModel> sprints) {
        mAdapter.changeDataSet(sprints);
    }

    @Override
    public void onItemClick(@NonNull final SprintLocalModel chosenSprint) {
        ArrayList<SprintLocalModel> anotherSprints = (ArrayList<SprintLocalModel>) mSprintsPresenter.getSprints();
        anotherSprints.remove(chosenSprint);
        startActivityForResult(SprintDetailsActivity.getIntentForStarting(getContext(), chosenSprint, anotherSprints),
                REQUEST_CODE_SPRINT_DETAILS);
    }

    public void setStoriesListener(@NonNull final StoriesListener storiesListener) {
        mStoriesListener = storiesListener;
    }

    @Override
    public void onAddNewStoryClicked(@NonNull final SprintLocalModel sprint) {
        mUiUtils.createListDialog(getContext(), R.string.select_story_dialog_title,
                mSprintsPresenter.getStoriesTitles(),
                mSprintsPresenter.getOnChoosingStoryFunction(sprint.getId())
        );
    }

    @Override
    public void onEditSprintClicked(@NonNull final SprintLocalModel sprint) {
        // TODO: GOTO EDIT SPRINT
        showToast("GOTO Edit Sprint page");
    }

    @Override
    public void onDeleteSprintClicked(@NonNull final SprintLocalModel sprint) {
        mUiUtils.createConfirmDialog(getContext(), () -> mSprintsPresenter.deleteSprint(sprint),
                String.format(getString(R.string.sprint_remove_dialog_title_format),
                        getString(R.string.sprint_remove_dialog_title), sprint.getName()));
    }

    @Override
    public void removeSprintFromAdapter(@NonNull final SprintLocalModel sprint) {
        mAdapter.removeValue(sprint);
    }

    public List<SprintLocalModel> getSprints() {
        return mSprintsPresenter.getSprints();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @NonNull final Intent data) {
        if (resultCode == RESULT_OK) {
            processSuccessfulResult(requestCode, data);
        }
    }

    private void processSuccessfulResult(final int requestCode, @NonNull final Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SPRINT_DETAILS:
                mSprintsPresenter.checkToRefresh(
                        data.getBooleanExtra(IS_NEED_TO_REFRESH, false),
                        data.getParcelableExtra(DETACHED_STORY));
                break;
        }
    }

    public interface StoriesListener {
        List<UserStory> getUnassignedStories();

        void removeStoryAfterAttaching(final int storyPosition);

        void addStoryAfterDetaching(@NonNull final UserStory userStory);
    }
}
