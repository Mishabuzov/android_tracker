package com.example.tom.itistracker.screens.project_choosing;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.Project;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectChoosingHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.project_image) ImageView mProjectImage;

    @BindView(R.id.project_title) TextView mProjectTitle;

    ProjectChoosingHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(@NonNull final Project project) {
        mProjectTitle.setText(project.getTitle());
        setProjectAvatar(project.getLogoUrl());
    }

    private void setProjectAvatar(String avatarUri) {
        if (avatarUri == null || avatarUri.isEmpty()) {
            setDefaultProjectImage();
        } else {
            Picasso.get().load(avatarUri).into(mProjectImage, new Callback() {
                @Override
                public void onSuccess() {
                    mProjectImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    setDefaultProjectImage();
                }
            });
        }
    }

    private void setDefaultProjectImage() {
        mProjectImage.setImageDrawable(
                ContextCompat.getDrawable(mProjectImage.getContext(), R.drawable.ic_default_project_avatar)
        );
        mProjectImage.setVisibility(View.VISIBLE);
    }

}
