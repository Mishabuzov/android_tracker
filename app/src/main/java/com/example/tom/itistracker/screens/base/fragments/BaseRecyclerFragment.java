package com.example.tom.itistracker.screens.base.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.widgets.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerFragment extends BaseFragment {

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty) TextView mEmpty;

    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler, container, false);
        ButterKnife.bind(this, view);
        getArgs();
        initFragmentElements();
        initPresenter();
        doActions();
        return view;
    }

    protected abstract void getArgs();

    protected abstract void doActions();

    protected abstract void initPresenter();

    private void initFragmentElements() {
        installAdapter();
        setupRecyclerView();

    }

    protected abstract void installAdapter();

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mEmpty);
        mRecyclerView.setBackgroundResource(R.color.recycler_background_default);
    }

    public EmptyRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}