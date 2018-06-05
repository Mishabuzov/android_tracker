package com.example.tom.itistracker.screens.sprint_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.screens.sprints_and_stories.stories.StoriesAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SprintDetailsAdapter extends StoriesAdapter {

    private static final int TYPE_TASKBOARD_BUTTON = 3;

    @NonNull
    private final TaskboardButtonListener mTaskboardButtonListener;

    SprintDetailsAdapter(@NonNull final StoriesHolderListener settingsListener,
                         @NonNull final TaskboardButtonListener taskboardButtonListener) {
        super(settingsListener);
        mTaskboardButtonListener = taskboardButtonListener;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Here be dangerous if enable footer pagination!
        //TODO: Warning! Here you should add extra item in end of list to work correctly
        if (position == getItemCount() - 1 && getItemCount() != 0) {
            return TYPE_TASKBOARD_BUTTON;
        }
        return super.getItemViewType(position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TASKBOARD_BUTTON) {
            return new TaskboardButtonHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_taskboard_item, parent, false),
                    mTaskboardButtonListener
            );
        }
        return super.onCreateDefaultViewHolder(parent, viewType);
    }

    interface TaskboardButtonListener {
        void showTaskboard();
    }

    static class TaskboardButtonHolder extends RecyclerView.ViewHolder {

        private final TaskboardButtonListener mListener;

        TaskboardButtonHolder(View itemView,
                              @NonNull final TaskboardButtonListener taskboardButtonListener) {
            super(itemView);
            mListener = taskboardButtonListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.taskboard_button)
        public void onTaskboardClicked() {
            mListener.showTaskboard();
        }

    }

}
