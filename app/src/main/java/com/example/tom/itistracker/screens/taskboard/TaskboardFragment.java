package com.example.tom.itistracker.screens.taskboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.TaskStatus;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.screens.base.fragments.BaseFragment;
import com.example.tom.itistracker.tools.DragAndDropFeature;
import com.example.tom.itistracker.tools.functions.ResultFunction;
import com.example.tom.itistracker.widgets.HorizontalDragScrollView;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tom.itistracker.screens.taskboard.TaskboardActivity.CLICKED_SPRINT_ID;
import static com.example.tom.itistracker.screens.taskboard.TaskboardActivity.CLICKED_SPRINT_TITLE;

public class TaskboardFragment extends BaseFragment implements TaskboardView, DragAndDropFeature.DragAndDropCallback {

    @BindDimen(R.dimen.taskboard_width) int mTaskBoardWidth;

    @BindDimen(R.dimen.taskboard_margin_start_end) int mTaskboardMarginsStartEnd;

    @BindDimen(R.dimen.taskboard_margin_top_bottom) int mTaskboardMarginsTopBottom;

    @BindDimen(R.dimen.taskboard_corner_radius) int mTaskboardCornerRadius;

    @BindView(R.id.horizontal_drag_scroll) HorizontalDragScrollView mScrollView;

    @BindView(R.id.board_view) LinearLayout mMainBoard;

    @InjectPresenter TaskboardPresenter mTaskboardPresenter;

    private LayoutInflater mLayoutInflater;

    private LinearLayout mNewTasksBoard;

    private LinearLayout mInProgressTasksBoard;

    private LinearLayout mCheckTasksBoard;

    private LinearLayout mClosedTasksBoard;

    private LinearLayout mBlockedTasksBoard;

    private String mClickedSprintTitle;

    private DragAndDropFeature mDragAndDrop;

    private int mScrollDistance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taskboard, container, false);
        ButterKnife.bind(this, view);

        mDragAndDrop = new DragAndDropFeature(this);
        mScrollView.setOnScrollViewListener(() -> mScrollDistance = mScrollView.getScrollX());
        //TODO: fix auto drag listener
        mDragAndDrop.setAutoScrollListener(mMainBoard);

        setToolbarTitle(String.format(getString(R.string.taskboard_toolbar_title_format), mClickedSprintTitle));
        initTaskboards();
        mTaskboardPresenter.loadTasks();
        return view;
    }

    @Override
    protected void getArgs() {
        final Bundle args = getArguments();
        if (args != null) {
            mTaskboardPresenter.setClickedSprintId(args.getLong(CLICKED_SPRINT_ID));
            mClickedSprintTitle = args.getString(CLICKED_SPRINT_TITLE);
        }
    }

    private void initTaskboards() {
        mLayoutInflater = getActivity().getLayoutInflater();
        mNewTasksBoard = createAndAttachTaskboard(R.string.taskboard_new_board_title, TaskStatus.NEW.getBoardId());
        mInProgressTasksBoard = createAndAttachTaskboard(R.string.taskboard_in_progress_board_title, TaskStatus.IN_PROGRESS.getBoardId());
        mCheckTasksBoard = createAndAttachTaskboard(R.string.taskboard_check_board_title, TaskStatus.READY_FOR_TEST.getBoardId());
        mClosedTasksBoard = createAndAttachTaskboard(R.string.taskboard_close_board_title, TaskStatus.CLOSED.getBoardId());
        mBlockedTasksBoard = createAndAttachTaskboard(R.string.taskboard_blocked_board_title, TaskStatus.BLOCKED.getBoardId());
    }

    private LinearLayout createAndAttachTaskboard(@StringRes final int taskBoardTitle, @IdRes final int taskboardId) {
        final CardView taskBoard = (CardView) mLayoutInflater.inflate(R.layout.board_layout, null);
        //TODO: fix auto drag listener
        mDragAndDrop.setAutoScrollListener(taskBoard);
        final TextView taskBoardTitleTextView = taskBoard.findViewById(R.id.board_title_textview);
        taskBoardTitleTextView.setText(taskBoardTitle);
        CardView.LayoutParams cardParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        cardParams.setMargins(mTaskboardMarginsStartEnd, mTaskboardMarginsTopBottom, mTaskboardMarginsStartEnd, mTaskboardMarginsTopBottom);
        taskBoard.setLayoutParams(cardParams);
        mMainBoard.addView(taskBoard);
//        taskBoard.setOnDragListener(mDragAndDrop.getNewDragListener()); -- NOTE: Here is all board view markered. It's better for eye;
        LinearLayout tasksLayout = taskBoard.findViewById(R.id.task_item_layout);
        tasksLayout.setOnDragListener(mDragAndDrop.getNewDragListener());
        tasksLayout.setId(taskboardId);
        return tasksLayout;
    }

    @Override
    public void attachToNewTaskboard(@NonNull final Task task) {
        attachTaskToTaskboard(task, mNewTasksBoard);
    }

    @Override
    public void attachToProgressTaskboard(@NonNull final Task task) {
        attachTaskToTaskboard(task, mInProgressTasksBoard);
    }

    @Override
    public void attachCheckReadyTaskboard(@NonNull final Task task) {
        attachTaskToTaskboard(task, mCheckTasksBoard);
    }

    @Override
    public void attachToClosedTaskboard(@NonNull final Task task) {
        attachTaskToTaskboard(task, mClosedTasksBoard);
    }

    @Override
    public void attachToBlockedTaskboard(@NonNull final Task task) {
        attachTaskToTaskboard(task, mBlockedTasksBoard);
    }

    private void attachTaskToTaskboard(@NonNull final Task task, @NonNull final ViewGroup taskboard) {
        final View taskLayout = mLayoutInflater.inflate(R.layout.story_item, null);
        taskLayout.setLayoutParams(new ViewGroup.LayoutParams(mTaskBoardWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        //TODO: GOTO taskDetails.
        taskLayout.setOnClickListener(view -> mUiUtils.showToast("Goto taskDetails", getContext()));
        setTaskInfo(task, taskLayout);
        taskLayout.findViewById(R.id.settings_button).setVisibility(View.GONE);
        taskLayout.setOnLongClickListener(mDragAndDrop.getNewLongClickListener(task));
        taskboard.addView(taskLayout);
    }

    /**
     * This method also can be used for refreshing taskLayout after update;
     *
     * @param task       - new or refreshed task.
     * @param taskLayout - container for task.
     */
    @Override
    public void setTaskInfo(@NonNull final Task task, @NonNull final View taskLayout) {
        mUiUtils.setExecutorAvatar(task.getExecutor(), taskLayout.findViewById(R.id.executor_image));
        TextView taskTitle = taskLayout.findViewById(R.id.story_title);
        taskTitle.setText(task.getTitle());
        TextView deadlineView = taskLayout.findViewById(R.id.story_number);
        setDeadline(task.getDeadline(), deadlineView);
    }

    @Override
    public int getScrollDistance() {
        return mScrollDistance;
    }

    @Override
    public HorizontalDragScrollView getScrollView() {
        return mScrollView;
    }

    private void setDeadline(@Nullable final String deadline, @NonNull final TextView deadlineView) {
        if (deadline == null) {
            deadlineView.setText(String.format(getString(R.string.task_deadline_title_format), getString(R.string.task_deadline_doesnt_set)));
        } else {
            deadlineView.setText(String.format(getString(R.string.task_deadline_title_format), deadline));
        }
    }

    @Override
    public Drawable getShape(@DrawableRes final int shape) {
        return ContextCompat.getDrawable(getContext(), shape);
    }

    @Override
    public void onDropAction(@NonNull final ResultFunction<Task> afterSuccessDropAction,
                             @IdRes final int dropTargetId,
                             @NonNull final Task task) {
        mTaskboardPresenter.changeTaskStatus(task, dropTargetId, afterSuccessDropAction);
    }

}
