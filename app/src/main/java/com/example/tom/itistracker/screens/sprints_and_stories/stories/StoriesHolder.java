package com.example.tom.itistracker.screens.sprints_and_stories.stories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesAdapter.StoriesHolderListener;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoriesHolder extends RecyclerView.ViewHolder {

    private final StoriesHolderListener mSettingsListener;

    @BindString(R.string.user_story_points_inscription) String mUserStoryPointsInscription;

    @BindString(R.string.user_story_points_format) String mUserStoryPointsFormat;

    @BindView(R.id.executor_image) ImageView mExecutorAvatar;

    @BindView(R.id.story_title) TextView mStoryTitle;

    @BindView(R.id.story_number) TextView mStoryPoints;

    private UserStory mUserStory;

    StoriesHolder(@NonNull final View itemView,
                  @NonNull final StoriesHolderListener settingsListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mSettingsListener = settingsListener;
    }

    @OnClick(R.id.settings_button)
    public void onSettingsClicked(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(mSettingsListener.getStorySettingsMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> mSettingsListener.getStorySettingsClickFunction(
                getAdapterPosition(),
                mUserStory).action(menuItem));
        popupMenu.show();
    }

    public void bind(@NonNull final UserStory userStory) {
        mUserStory = userStory;
        mSettingsListener.setExecutorAvatar(userStory.getExecutor(), mExecutorAvatar);
        mStoryTitle.setText(userStory.getTitle());
        mStoryPoints.setText(String.format(mUserStoryPointsFormat,
                userStory.getTotalPoints(), mUserStoryPointsInscription));
    }

}
