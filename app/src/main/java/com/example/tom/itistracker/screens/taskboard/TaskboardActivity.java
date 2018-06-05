package com.example.tom.itistracker.screens.taskboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.tom.itistracker.screens.base.activities.SingleFragmentActivity;

public class TaskboardActivity extends SingleFragmentActivity {

    public static final String CLICKED_SPRINT_ID = "clicked_sprint_id";

    public static final String CLICKED_SPRINT_TITLE = "clicked_sprint_title";

    public static void startActivity(@NonNull final Context context,
                                     final long clickedSprintId,
                                     @NonNull final String clickedSprintTitle) {
        Intent intent = new Intent(context, TaskboardActivity.class);
        intent.putExtra(CLICKED_SPRINT_ID, clickedSprintId);
        intent.putExtra(CLICKED_SPRINT_TITLE, clickedSprintTitle);
        context.startActivity(intent);
    }

    @Override
    protected Fragment getFragment() {
        return new TaskboardFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }
}
