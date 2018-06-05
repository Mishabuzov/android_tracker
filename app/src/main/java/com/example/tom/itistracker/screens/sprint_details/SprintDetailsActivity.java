package com.example.tom.itistracker.screens.sprint_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.screens.base.activities.SingleFragmentActivity;

import java.util.ArrayList;

public class SprintDetailsActivity extends SingleFragmentActivity {

    static final String SPRINT = "sprint";

    //Another sprints == list of sprints without chosen sprint;
    static final String ANOTHER_SPRINTS = "another_sprints";

    public static Intent getIntentForStarting(@NonNull final Context context,
                                              @NonNull final Sprint clickedSprint,
                                              @NonNull final ArrayList<Sprint> anotherSprints) {
        Intent intent = new Intent(context, SprintDetailsActivity.class);
        intent.putExtra(SPRINT, clickedSprint);
        intent.putParcelableArrayListExtra(ANOTHER_SPRINTS, anotherSprints);
        return intent;
    }

    @Override
    protected Fragment getFragment() {
        return new SprintDetailsFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }

}
