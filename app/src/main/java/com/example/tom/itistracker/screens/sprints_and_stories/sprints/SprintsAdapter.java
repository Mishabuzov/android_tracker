package com.example.tom.itistracker.screens.sprints_and_stories.sprints;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.screens.base.adapter.BaseAdapter;

import java.util.ArrayList;

public class SprintsAdapter extends BaseAdapter<Sprint> {

    private final SprintSettingsListener mSprintSettingsListener;

    SprintsAdapter(@NonNull final OnItemClickListener<Sprint> listener,
                   @NonNull final SprintSettingsListener sprintSettingsListener) {
        super(new ArrayList<>(), listener);
        mSprintSettingsListener = sprintSettingsListener;
    }

    @Override
    @NonNull
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new SprintsHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.sprint_item, parent, false),
                mSprintSettingsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SprintsHolder sprintsHolder = (SprintsHolder) holder;
        Sprint sprint = getItem(position);
        sprintsHolder.bind(sprint);
        super.onBindViewHolder(holder, position);
    }

    public interface SprintSettingsListener {

        void onAddNewStoryClicked(@NonNull final Sprint sprint);

        void onEditSprintClicked(@NonNull final Sprint sprint);

        void onDeleteSprintClicked(@NonNull final Sprint sprint);

    }

}
