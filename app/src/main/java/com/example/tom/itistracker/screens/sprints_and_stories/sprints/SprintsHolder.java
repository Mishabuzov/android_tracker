package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.screens.sprints_and_stories.sprints.SprintsAdapter.SprintSettingsListener;
import com.example.tom.itistracker.widgets.CircularProgressBar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SprintsHolder extends RecyclerView.ViewHolder {

    @BindString(R.string.sprint_points_format) String mSprintPointsFormat;

    @BindView(R.id.sprint_progress) CircularProgressBar mSprintProgress;

    @BindView(R.id.sprint_points) TextView mSprintPoints;

    @BindView(R.id.sprint_title) TextView mSprintTitle;

    @BindView(R.id.sprint_deadline) TextView mSprintDeadline;

//    @BindView(R.id.show_stories_button) ImageButton mShowStoriesButton;

    @BindView(R.id.settings_button) ImageButton mSettingsButton;

    private final SprintSettingsListener mSprintSettingsListener;

    private Sprint mSprint;

    public SprintsHolder(@NonNull final View itemView,
                         @NonNull final SprintSettingsListener sprintSettingsListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mSprintSettingsListener = sprintSettingsListener;
    }

    @OnClick(R.id.settings_button)
    public void onSettingsClicked(View v) {
        PopupMenu popupMenu = new PopupMenu(mSettingsButton.getContext(), v);
        popupMenu.inflate(R.menu.sprint_settings_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.sprint_add_story:
                    mSprintSettingsListener.onAddNewStoryClicked(mSprint);
                    return true;
                case R.id.sprint_edit:
                    mSprintSettingsListener.onEditSprintClicked(mSprint);
                    return true;
                case R.id.sprint_delete:
                    mSprintSettingsListener.onDeleteSprintClicked(mSprint);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    void bind(@NonNull final Sprint sprint) {
        mSprint = sprint;
        mSprintProgress.setProgress(mSprint.getClosedPoints() * 100 / mSprint.getTotalPoints());
        mSprintTitle.setText(mSprint.getName());
        mSprintPoints.setText(String.format(mSprintPointsFormat, mSprint.getClosedPoints(),
                mSprint.getTotalPoints()));
        mSprintDeadline.setText(mSprint.getFinishDate());
    }

}
