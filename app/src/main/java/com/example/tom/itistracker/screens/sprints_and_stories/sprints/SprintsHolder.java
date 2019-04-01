package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.SprintLocalModel;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.screens.sprints_and_stories.sprints.SprintsAdapter.SprintSettingsListener;
import com.example.tom.itistracker.widgets.CircularProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.tom.itistracker.tools.Constants.MILLIS_TO_DAYS_DIVISION_VALUE;

public class SprintsHolder extends RecyclerView.ViewHolder {

    private final SprintSettingsListener mSprintSettingsListener;
    @BindString(R.string.date_format) String mDateFormat;
    @BindString(R.string.deadline_overdue_format) String mDeadlineOverdueFormat;
    @BindString(R.string.sprint_points_format) String mSprintPointsFormat;
    @BindString(R.string.blocked_tasks_text_format) String mBlockedTasksFormat;
    @BindView(R.id.sprint_progress) CircularProgressBar mSprintProgress;
    @BindView(R.id.sprint_points) TextView mSprintPoints;
    @BindView(R.id.sprint_title) TextView mSprintTitle;

//    @BindView(R.id.show_stories_button) ImageButton mShowStoriesButton;
@BindView(R.id.sprint_deadline) TextView mSprintDeadline;
    @BindView(R.id.settings_button) ImageButton mSettingsButton;
    @BindView(R.id.blocked_tasks_textview) TextView mBlockedTasksTextView;
    private SprintLocalModel mSprint;

    SprintsHolder(@NonNull final View itemView,
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

    void bind(@NonNull final SprintLocalModel sprint) {
        mSprint = sprint;
        mSprintProgress.setProgress(mSprint.getClosedPoints() * 100 / mSprint.getTotalPoints());
        mSprintTitle.setText(mSprint.getName());
        mSprintPoints.setText(String.format(mSprintPointsFormat, mSprint.getClosedPoints(),
                mSprint.getTotalPoints()));
        setDeadline();
        showBlockedTasksInfo();
    }

    private void showBlockedTasksInfo() {
        if (mSprint.isContainsBlockedTasks()) {
            mBlockedTasksTextView.setText(String.format(mBlockedTasksFormat, mSprint.getBlockedTasksCount()));
            mBlockedTasksTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setDeadline() {
        if (isSprintOverdue()) {
            mSprintDeadline.setTypeface(null, Typeface.BOLD);
            mSprintDeadline.setTextColor(Color.RED);
            mSprintDeadline.setText(String.format(mDeadlineOverdueFormat, mSprint.getFinishDate()));
        } else if (isSprintRiskToBeOverdue()) {
            mSprintDeadline.setTypeface(null, Typeface.BOLD);
            mSprintDeadline.setTextColor(Color.MAGENTA);
        } else {
            mSprintDeadline.setText(mSprint.getFinishDate());
            //TODO: check for overdue risk
        }
    }

    private boolean isSprintRiskToBeOverdue() {
        return getNewStoriesPointsSum() > getDaysUntilSprintDeadline();
    }

    private boolean isSprintOverdue() {
        return getMillisUntilSprintDeadline() > 0 && !mSprint.isClosed();
    }

    private long getMillisUntilSprintDeadline() {
        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat, Locale.getDefault());
        long deadlineDateMillis = 0;
        try {
            deadlineDateMillis = sdf.parse(mSprint.getFinishDate()).getTime();
        } catch (ParseException e) {
            Timber.w(e.toString());
        }
        return System.currentTimeMillis() - deadlineDateMillis;
    }

    private int getDaysUntilSprintDeadline() {
        return (int) (getMillisUntilSprintDeadline() / MILLIS_TO_DAYS_DIVISION_VALUE);
    }

    private int getNewStoriesPointsSum() {
        List<UserStory> userStories = mSprint.getUserStories();
        int newStoriesPointsSum = 0;
        for (UserStory userStory : userStories) {
            switch (userStory.getSerStoryStatusInfo().getUserStoryStatus()) {
                case NEW:
                    newStoriesPointsSum += userStory.getTotalPoints();
            }
        }
        return newStoriesPointsSum;
    }


}
