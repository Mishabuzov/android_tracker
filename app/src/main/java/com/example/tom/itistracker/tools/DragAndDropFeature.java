package com.example.tom.itistracker.tools;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.tools.functions.ResultFunction;
import com.example.tom.itistracker.widgets.HorizontalDragScrollView;

public final class DragAndDropFeature {

    private static OnLongClickListener sClickedTaskViewListener;

    @NonNull
    private final Drawable mEnterShape;

    @NonNull
    private final Drawable mNormalShape;

    @NonNull
    private final DragAndDropCallback mCallback;

    public DragAndDropFeature(@NonNull final DragAndDropCallback dragAndDropCallback) {
        mCallback = dragAndDropCallback;
        mEnterShape = mCallback.getShape(R.drawable.shape_droptarget);
        mNormalShape = mCallback.getShape(R.drawable.shape);
    }

    public OnLongClickListener getNewLongClickListener(@NonNull final Task task) {
        return new OnLongClickListener(task);
    }

    public OnDragListener getNewDragListener() {
        return new OnDragListener();
    }

    public interface DragAndDropCallback {

        Drawable getShape(@DrawableRes final int shape);

        void onDropAction(@NonNull final ResultFunction<Task> afterSuccessDropAction,
                          @IdRes final int dropTargetId,
                          @NonNull final Task task);

        void setTaskInfo(@NonNull final Task refreshedTask, @NonNull final View taskLayout);

        int getScrollDistance();

        HorizontalDragScrollView getScrollView();

    }

    public final class OnLongClickListener implements View.OnLongClickListener {

        @NonNull
        private Task mTask;

        private OnLongClickListener(@NonNull final Task task) {
            mTask = task;
        }

        @NonNull
        public Task getTask() {
            return mTask;
        }

        public void setTask(@NonNull final Task task) {
            mTask = task;
        }

        @Override
        public boolean onLongClick(@NonNull final View view) {
            sClickedTaskViewListener = this;
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }

    }

    public final class OnDragListener implements View.OnDragListener {

        private boolean mIsViewExited;
        private View mTouchedView;

        @Override
        public boolean onDrag(@NonNull final View v,
                              @NonNull final DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(mEnterShape);
                    mIsViewExited = false;
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(mNormalShape);
                    mIsViewExited = true;
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    mTouchedView = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) mTouchedView.getParent();
                    if (!v.equals(owner)) {
                        mCallback.onDropAction(newTask -> {
                            owner.removeView(mTouchedView);
                            ViewGroup taskContainer = (ViewGroup) v;
                            taskContainer.addView(mTouchedView);
                            mCallback.setTaskInfo(newTask, mTouchedView);
                            sClickedTaskViewListener.setTask(newTask);
                            sClickedTaskViewListener = null;
                        }, v.getId(), sClickedTaskViewListener.getTask());
                    }
                    mTouchedView.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(mNormalShape);
                    if (mIsViewExited) {
                        mTouchedView = (View) event.getLocalState();
                        mTouchedView.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }

    }

    //TODO: fix auto drag listener
    public void setAutoScrollListener(View view) {
        view.setOnDragListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_LOCATION: {
                    int x = Math.round(v.getX()) + Math.round(event.getX());
                    int translatedX = x - mCallback.getScrollDistance();
                    int threshold = 50;
                    // make a scrolling up due the x has passed the threshold (200px in my case);
                    if (translatedX < 200) {
                        // make a scroll up by 30 px
                        mCallback.getScrollView().scrollBy(-23, 0);
                    }
                    // make a autoscrolling down due x has passed the 500 px border
                    if (translatedX + threshold > 500) {
                        // make a scroll down by 30 px
                        mCallback.getScrollView().scrollBy(23, 0);
                    }
                    break;
                }
                case DragEvent.ACTION_DRAG_ENDED:
                    mCallback.getScrollView().stopNestedScroll();
                    break;
            }
            return true;
        });
    }

}
