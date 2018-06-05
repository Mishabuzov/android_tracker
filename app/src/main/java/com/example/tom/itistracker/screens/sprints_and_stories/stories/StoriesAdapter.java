package com.example.tom.itistracker.screens.sprints_and_stories.stories;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.base.adapter.BaseAdapter;
import com.example.tom.itistracker.tools.functions.BooleanResultFunction;

import java.util.ArrayList;

public class StoriesAdapter extends BaseAdapter<UserStory> {

    @NonNull
    private final StoriesHolderListener mSettingsListener;

    protected StoriesAdapter(@NonNull final StoriesHolderListener settingsListener) {
        super(new ArrayList<>());
        mSettingsListener = settingsListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new StoriesHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false),
                mSettingsListener
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            StoriesHolder storiesHolder = (StoriesHolder) holder;
            UserStory userStory = getItem(position);
            storiesHolder.bind(userStory);
        }
        super.onBindViewHolder(holder, position);
    }

    public interface StoriesHolderListener {

        @MenuRes
        int getStorySettingsMenu();

        @NonNull
        BooleanResultFunction<MenuItem> getStorySettingsClickFunction(final int adapterPosition,
                                                                      @NonNull final UserStory userStory);

        void setExecutorAvatar(@Nullable final User executor, @NonNull final ImageView userImageView);

    }

}
