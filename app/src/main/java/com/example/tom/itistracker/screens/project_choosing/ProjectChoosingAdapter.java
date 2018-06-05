package com.example.tom.itistracker.screens.project_choosing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Project;
import com.example.tom.itistracker.screens.base.adapter.BaseAdapter;

import java.util.ArrayList;

public class ProjectChoosingAdapter extends BaseAdapter<Project> {

    ProjectChoosingAdapter(@NonNull final OnItemClickListener<Project> listener) {
        super(new ArrayList<>(), listener);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        return new ProjectChoosingHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            ProjectChoosingHolder projectChoosingHolder = (ProjectChoosingHolder) holder;
            Project project = getItem(position);
            projectChoosingHolder.bind(project);
        }
        super.onBindViewHolder(holder, position);
    }


}
