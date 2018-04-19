package com.example.tom.itistracker.screens.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.tools.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

class MenuHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_header_layout) ViewGroup mMainLayout;

    @BindView(R.id.username_tv) TextView mUsernameTv;

    MenuHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind() {
        User user = PreferenceUtils.getUserProfile();
        mUsernameTv.setText(user.getUsername());
//        mStatusTv.setText(user.getStatus());
        mMainLayout.setVisibility(View.VISIBLE);
    }
}
