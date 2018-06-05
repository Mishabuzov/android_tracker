package com.example.tom.itistracker.screens.sprints_and_stories.base.stories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.base.fragments.BaseRecyclerFragment;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesAdapter;
import com.example.tom.itistracker.tools.functions.BooleanResultFunction;
import com.example.tom.itistracker.tools.functions.ResultFunction;

import java.util.List;

public abstract class BaseStoriesFragment<
        View extends BaseStoriesView, Adapter extends StoriesAdapter, Presenter extends BaseStoriesPresenter<View>
        >
        extends BaseRecyclerFragment<Adapter>
        implements BaseStoriesView, StoriesAdapter.StoriesHolderListener {

    protected abstract Presenter getPresenter();

    protected abstract Adapter getAdapter();

    @NonNull
    @Override
    public BooleanResultFunction<MenuItem> getStorySettingsClickFunction(final int adapterPosition,
                                                                         @NonNull final UserStory userStory) {
        return getPresenter().getStorySettingsClickFunction(adapterPosition, userStory);
    }

    @Override
    public void showStories(@NonNull final List<UserStory> stories) {
        getAdapter().changeDataSet(stories);
    }

    @Override
    public void setExecutorAvatar(@Nullable final User executor, @NonNull final ImageView userImageView) {
        mUiUtils.setExecutorAvatar(executor, userImageView);
    }

    @Override
    public void removeFromStoriesScreen(final int position) {
        getAdapter().removeValue(position);
    }

    @Override
    public void showAttachingDialog(@NonNull final List<String> sprintTitles,
                                    @NonNull final ResultFunction<Integer> attachingFunction) {
        mUiUtils.createListDialog(getContext(), R.string.select_sprint_dialog_title,
                sprintTitles,
                attachingFunction
        );
    }

}
