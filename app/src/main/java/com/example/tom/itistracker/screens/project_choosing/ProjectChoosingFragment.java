package com.example.tom.itistracker.screens.project_choosing;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.project.Project;
import com.example.tom.itistracker.screens.base.fragments.BaseRecyclerFragment;
import com.example.tom.itistracker.screens.navigation.NavigationActivity;

import java.util.List;

public class ProjectChoosingFragment extends BaseRecyclerFragment<ProjectChoosingAdapter>
        implements ProjectChoosingView, ProjectChoosingAdapter.OnItemClickListener<Project> {

    @InjectPresenter ProjectChoosingPresenter mPresenter;

    private ProjectChoosingAdapter mAdapter;

    @Override
    protected void doActions() {
        setToolbarTitle(R.string.choosing_project_title);
        changeEmptyViewTitle(R.string.empty_view_project_title);
        mPresenter.loadProjects();
    }

    @Override
    protected ProjectChoosingAdapter installAdapter() {
        mAdapter = new ProjectChoosingAdapter(this);
        return mAdapter;
    }

    @Override
    public void showProjects(@NonNull final List<Project> projects) {
        mAdapter.changeDataSet(projects);
    }

    @Override
    public void openNavigationActivity() {
        NavigationActivity.openMainPage(getContext());
    }

    @Override
    public void onItemClick(@NonNull final Project project) {
        mPresenter.loadProjectDetails(project.getId());
    }


}
