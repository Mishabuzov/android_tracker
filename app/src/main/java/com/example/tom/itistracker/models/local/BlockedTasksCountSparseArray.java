package com.example.tom.itistracker.models.local;

import android.support.annotation.NonNull;
import android.util.LongSparseArray;

import com.example.tom.itistracker.models.network.Task;

public class BlockedTasksCountSparseArray extends LongSparseArray<Integer> {

    public boolean incrementCounterIfBlocked(@NonNull final Task task) {
        boolean isTaskAdded = false;
        if (task.getStatus() == TaskStatus.BLOCKED || task.isBlocked()) {
            long sprintId = task.getSprintId();
            Integer blockedTasksCount = get(sprintId);
            if (blockedTasksCount == null) {
                blockedTasksCount = 1;
            } else {
                blockedTasksCount += 1;
            }
            append(sprintId, blockedTasksCount);
            isTaskAdded = true;
        }
        return isTaskAdded;
    }

}
