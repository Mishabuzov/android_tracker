package com.example.tom.itistracker.screens.project_choosing;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.Project;
import com.example.tom.itistracker.screens.base.fragments.BaseFragmentView;

import java.util.List;

public interface ProjectChoosingView extends BaseFragmentView {

    void showProjects(@NonNull final List<Project> projects);

}
