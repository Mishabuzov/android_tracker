package com.example.tom.itistracker.screens.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

class MenuHeaderHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_header_layout) ViewGroup mMainLayout;

    @BindView(R.id.username_tv) TextView mUsernameTv;

    private final User mUser;

    MenuHeaderHolder(@NonNull final View itemView,
                     @NonNull final User user) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mUser = user;
    }

    void bind() {
        mUsernameTv.setText(mUser.getUsername());
//        mStatusTv.setText(user.getStatus());
        mMainLayout.setVisibility(View.VISIBLE);
    }
}
